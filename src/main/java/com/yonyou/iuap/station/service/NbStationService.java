package com.yonyou.iuap.station.service;

import com.google.gson.Gson;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.station.cache.RedisCacheKey;
import com.yonyou.iuap.station.cache.RedisTemplate;
import com.yonyou.iuap.station.entity.NbStation;
import com.yonyou.iuap.station.repository.NbStationDao;
import com.yonyou.iuap.station.repository.NbStationRepository;
import com.yonyou.iuap.station.util.SimilarityMatch;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>Title: CardTableMetaService</p>
 * <p>Description: </p>
 */
@Service
public class NbStationService {
	
    @Autowired
    private NbStationDao dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private NbStationRepository nbStationRepository;

    private Gson gson =new Gson();
    
    /**
     * Description:通过非主键字段查询
     * List<CardTable>
     * @param code
     */
    public List<NbStation> findByCode(String code) {
        return dao.findByCode(code);
    }
    public List<NbStation> findByName(String code) {
        return dao.findByName(code);
    }

    /**
     * 查询所有数据
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<NbStation> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {

        Map<String,Object> searchMap=searchParams.getSearchMap();

        //查询缓存数据
        Page<NbStation> pageResult=dao.selectAllByCache(pageRequest);

        //判断缓存是否有值
        if(pageResult.getContent()!=null||pageResult.getContent().size()>0){
            return pageResult;
        }else{
            //从数据库查询全部数据
            //查询数据库数据量
            int size=nbStationRepository.countAll();
            //定义新的分页数据,用来查询全部
            PageRequest pageRequestTemp=new PageRequest(0,size);
            //查询全部结果
            pageResult = dao.selectAllByPage(pageRequestTemp, searchMap);
            //相似度比较
            similarityMatch(pageResult,searchMap);
            //比较完之后再从缓存取值
            pageResult=dao.selectAllByCache(pageRequest);
        }
        return pageResult;
    }
    
    public void save(List<NbStation> recordList) {
        List<NbStation> addList = new ArrayList<>(recordList.size());
        List<NbStation> updateList = new ArrayList<>(recordList.size());
        for (NbStation meta : recordList) {
        	if (meta.getId() == null) {
            	meta.setId(UUID.randomUUID().toString());
            	meta.setDr(0);
                addList.add(meta);
            } else {
                updateList.add(meta);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
        	dao.batchInsert(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
        	dao.batchUpdate(updateList);
        }
    }

    public void batchDeleteByPrimaryKey(List<NbStation> list) {
    	dao.batchDelete(list);
    }

    /**
     * 将double转化为百分数返回
     * @param number
     * @return
     */
    private String dobleFormat(double number){
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(2);
        return nt.format(number);
    }

    /**
     * 内部站场相似度比较，并将数据存储在redis
     * @param pageResult
     * @param searchMap
     */
    public void similarityMatch(Page<NbStation> pageResult, Map<String,Object> searchMap){
        //匹配之前，先删除redis数据
        redisTemplate.del(RedisCacheKey.STASION_COED);
        //处理数据，相似度检查
        //根据查询的参数，看是哪个字段需要检查相似度
        //循环的list
        List<NbStation> nbStationList=pageResult.getContent();
        //匹配的list
        CopyOnWriteArrayList<NbStation> nbStationMatch=new CopyOnWriteArrayList<>(pageResult.getContent());
        //结果的list
        List<NbStation> nbStationResult=new ArrayList<>();

        //测试代码
        searchMap.put("SimilarityMatch","Name");
        Object object=searchMap.get("SimilarityMatch");

        //判断匹配条件,默认使用Name匹配
        if (object!=null){
            //匹配标志
            int tag=1;

            //取匹配的字段
            String matchField="get"+object.toString();

            //循环判断
            for (NbStation nbStation:nbStationList) {

                //放值的标志
                boolean tagStation=true;

                try {

                    //判断结果集是否已经包含
                    if(!contain(nbStationResult,nbStation)){
                        //反射取字段值
                        Method method=nbStation.getClass().getMethod(matchField,null);
                        String field1= (String) method.invoke(nbStation);

                        //循环去匹配
                        for (NbStation station:nbStationMatch ) {
                            //如果不是同一条数据则进行匹配
                            if(!nbStation.getMdm_code().equals(station.getMdm_code())){
                                String field2= (String) station.getClass().getMethod(matchField,null).invoke(station);
                                //匹配结果
                                double similarity= SimilarityMatch.getSimilarity(field1,field2);
                                //大于80%
                                if(similarity>0.8){
                                    //如果第一次匹配成功,将源数据也放入结果集
                                    if(tagStation){
                                        nbStation.setTag(String.valueOf(tag));
                                        nbStationResult.add(station);
                                        //同时将数据写入redis
                                        redisTemplate.rpush(RedisCacheKey.STASION_COED,gson.toJson(nbStation));
                                        tagStation=false;
                                    }

                                    //将相似度设置
                                    station.setSimilarity(dobleFormat(similarity));
                                    station.setTag(String.valueOf(tag));
                                    nbStationResult.add(station);
                                    //同时将数据写入redis
                                    redisTemplate.rpush(RedisCacheKey.STASION_COED,gson.toJson(station));
                                    nbStationMatch.remove(station);
                                }
                            }
                        }
                        tag++;
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断结果集是否包含比较的数据
     * 包含返回 true
     * @param list
     * @param nbStation
     * @return
     */
    private boolean contain(List<NbStation> list,NbStation nbStation){
        boolean result=false;
        if(list!=null&&list.size()>0&&nbStation!=null){
            for (NbStation station:list){
                //比较id
                if(station.getId().equals(nbStation.getId())){
                    result=true;
                    break;
                }
            }
        }
        return result;
    }
}
