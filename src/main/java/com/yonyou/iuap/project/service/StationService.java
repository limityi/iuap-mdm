package com.yonyou.iuap.project.service;

import com.google.gson.Gson;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.cache.RedisUtil;
import com.yonyou.iuap.project.entity.Station;
import com.yonyou.iuap.project.repository.StationDao;
import com.yonyou.iuap.project.repository.StationRepository;
import com.yonyou.iuap.project.util.SimilarityMatch;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>Title: CardTableMetaService</p>
 * <p>Description: </p>
 */
@Service
public class StationService {
	
    @Autowired
    private StationDao dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StationRepository stationRepository;

    private Gson gson =new Gson();


    /**
     * Description:通过非主键字段查询
     * List<CardTable>
     * @param
     */
    public Page<Station> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        Map<String,Object> searchMap=searchParams.getSearchMap();

        //查询缓存数据
        Page<Station> pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.STASION_COMPARE_DATA);

        //判断缓存是否有值
        if((!pageResult.getContent().isEmpty())&&pageResult.getContent().size()>0){
            return pageResult;
        }else{
            //从数据库查询全部数据
            //查询数据库数据量
            int size=stationRepository.countAll();
            //定义新的分页数据,用来查询全部
            PageRequest pageRequestTemp=new PageRequest(0,size);
            //查询全部结果
            pageResult = dao.selectAllByPage(pageRequestTemp, searchMap);
            //相似度比较
            similarityMatch(pageResult,searchMap);
            //比较完之后更新对比同步时间
            setSyncTime(RedisCacheKey.STASION_COMPARE_TIME);
            //比较完之后再从缓存取值
            pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.STASION_COMPARE_DATA);
        }
        return pageResult;
    }
    
    public void save(List<Station> recordList) {
        List<Station> addList = new ArrayList<>(recordList.size());
        List<Station> updateList = new ArrayList<>(recordList.size());
        for (Station meta : recordList) {
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
    
    public void batchDeleteByPrimaryKey(List<Station> list) {
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
    private void similarityMatch(Page<Station> pageResult, Map<String, Object> searchMap){
        //匹配之前，先删除redis数据
        redisTemplate.del(RedisCacheKey.STASION_COMPARE_DATA);
        //处理数据，相似度检查
        //根据查询的参数，看是哪个字段需要检查相似度
        //循环的list
        List<Station> stationList=pageResult.getContent();
        //匹配的list
        CopyOnWriteArrayList<Station> stationMatch=new CopyOnWriteArrayList<>(pageResult.getContent());
        //结果的list
        List<Station> stationResult=new ArrayList<>();

        //测试代码,只匹配Name
        searchMap.put("SimilarityMatch","Name");
        Object object=searchMap.get("SimilarityMatch");

        //判断匹配条件,默认使用Name匹配
        if (object!=null){
            //匹配标志
            int tag=1;

            //取匹配的字段
            String matchField="get"+object.toString();

            //循环判断
            for (Station station : stationList) {

                //放值的标志
                boolean tagStation=true;

                try {

                    //判断结果集是否已经包含
                    if(!contain(stationResult,station)){
                        //反射取字段值
                        Method method=station.getClass().getMethod(matchField,null);
                        String field1= (String) method.invoke(station);

                        //循环去匹配
                        for (Station sta:stationMatch ) {
                            //如果不是同一条数据则进行匹配
                            if(!station.getMdm_code().equals(sta.getMdm_code())){
                                String field2= (String) sta.getClass().getMethod(matchField,null).invoke(sta);
                                //匹配结果
                                double similarity= SimilarityMatch.getSimilarity(field1,field2);
                                //大于80%
                                if(similarity>0.8){
                                    //如果第一次匹配成功,将源数据也放入结果集
                                    if(tagStation){
                                        station.setTag(String.valueOf(tag));
                                        stationResult.add(station);
                                        //同时将数据写入redis
                                        redisTemplate.rpush(RedisCacheKey.STASION_COMPARE_DATA,gson.toJson(station));
                                        tagStation=false;
                                    }

                                    //将相似度设置
                                    sta.setSimilarity(dobleFormat(similarity));
                                    sta.setTag(String.valueOf(tag));
                                    stationResult.add(sta);
                                    //同时将数据写入redis
                                    redisTemplate.rpush(RedisCacheKey.STASION_COMPARE_DATA,gson.toJson(sta));
                                    stationMatch.remove(sta);
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
     * @param station
     * @return
     */
    private boolean contain(List<Station> list, Station station){
        boolean result=false;
        if(list!=null&&list.size()>0&&station!=null){
            for (Station sta:list){
                //比较id
                if(sta.getId().equals(station.getId())){
                    result=true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 定时任务调用方法
     */
    public void stationJob(){
        Map<String,Object> searchMap=new HashMap<>();
        //从数据库查询全部数据
        //查询数据库数据量
        int size=stationRepository.countAll();
        //定义新的分页数据,用来查询全部
        PageRequest pageRequestTemp=new PageRequest(0,size);
        //查询全部结果
        Page pageResult = dao.selectAllByPage(pageRequestTemp, searchMap);
        //相似度比较
        similarityMatch(pageResult,searchMap);
        //比较完之后更新站场同步时间
        setSyncTime(RedisCacheKey.STASION_COMPARE_TIME);
    }

    /**
     * 获取站场同步时间
     * @param fieldName
     * @return String
     */
    public String getSyncTime(String fieldName){
        return RedisUtil.getSyncTime(redisTemplate,RedisCacheKey.STASION_COMPARE_TIME,fieldName);
    }

    /**
     * 设置站场同步时间
     * @param fieldName
     */
    private void setSyncTime(String fieldName){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RedisUtil.setSyncTime(redisTemplate,RedisCacheKey.STASION_COMPARE_TIME,fieldName, format.format(new Date()));
    }

    /**
     * 查询全部唯一性校验失败的数据
     * @param pageRequest
     * @param condition
     * @return
     */
    public Page<Station> selectOnlyValidateByPage(PageRequest pageRequest,Map<String,Object> condition){
        //查询缓存数据
        Page<Station> pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.STASION_ONLY_DATA);
        //判断缓存是否有值
        if((!pageResult.getContent().isEmpty())&&pageResult.getContent().size()>0){
            return pageResult;
        }else{
            //从数据库查询全部数据
            //查询数据库数据量
            int result=dao.selectOnlyValidateData();
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if(result>0){
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.STASION_ONLY_TIME);
                pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.STASION_ONLY_DATA);
            }else{
                return pageResult;
            }
        }
        return pageResult;
    }

    /**
     * 查询必填项校验
     * @param pageRequest
     * @param condition
     * @return
     */
    public Page<Station> selectRequiredData(PageRequest pageRequest,List<String> condition){
        //查询缓存数据
        Page<Station> pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.STASION_REQUIRED_DATA);


        return pageResult;
    }
}
