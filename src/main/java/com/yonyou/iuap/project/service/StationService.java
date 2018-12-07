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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
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

        String inputStr=String.valueOf(searchMap.get("searchParam"));
        if(!inputStr.isEmpty()){
            try {
                String con= URLDecoder.decode(inputStr,"UTF-8");
                searchMap.put("searchParam",con);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        //查询缓存数据
        Page<Station> pageResult;

        boolean updateOperation=Boolean.parseBoolean(searchMap.get("updateOperation").toString());
        if(updateOperation){
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
        }else{
            //查询缓存数据
            pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.STASION_COMPARE_DATA);

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
                                if(similarity>0.9){
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

    public void stationOnlyJob(){
        dao.selectOnlyValidateData();
        setSyncTime(RedisCacheKey.STASION_ONLY_TIME);
    }

    public void stationRequiredJob(){
        List<String> requiredColumn=new ArrayList<>();

        //默认给必填条件加值
        requiredColumn.add("name");
        requiredColumn.add("station_shortname");
        requiredColumn.add("station_group_shortname");
        requiredColumn.add("station_address");
        requiredColumn.add("station_gdprovince");
        requiredColumn.add("station_yueyun");
        requiredColumn.add("station_companyid");
        requiredColumn.add("station_companyname");
        requiredColumn.add("station_company");
        requiredColumn.add("station_level");
        requiredColumn.add("station_servicetype");
        requiredColumn.add("station_area");
        requiredColumn.add("station_parea");
        requiredColumn.add("station_leasearea");
        requiredColumn.add("station_pnum");
        requiredColumn.add("station_type");
        requiredColumn.add("station_businessnature");
        requiredColumn.add("station_own");
        requiredColumn.add("station_businessmode");

        dao.selectRequiredData(requiredColumn);
        setSyncTime(RedisCacheKey.STASION_REQUIRED_TIME);
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
     * @param searchParams
     * @return
     */
    public Page<Station> selectOnlyValidateByPage(PageRequest pageRequest,SearchParams searchParams){

        boolean updateOperation=Boolean.parseBoolean(searchParams.getSearchMap().get("updateOperation").toString());

        //查询缓存数据
        Page<Station> pageResult;

        if(updateOperation){
            //从数据库查询全部数据
            //查询数据库数据量
            int result=dao.selectOnlyValidateData();
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if(result>0){
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.STASION_ONLY_TIME);
                pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.STASION_ONLY_DATA);
            }else {
                setSyncTime(RedisCacheKey.STASION_ONLY_TIME);
                pageResult=new PageImpl<>(new ArrayList<Station>(),pageRequest,0);
            }
        }else{
            //查询缓存数据
            pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.STASION_ONLY_DATA);
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
                    setSyncTime(RedisCacheKey.STASION_ONLY_TIME);
                    return pageResult;
                }
            }
        }
        return pageResult;
    }

    /**
     * 查询必填项校验
     * @param pageRequest
     * @param requiredColumn
     * @return
     */
    public Page<Station> selectRequiredData(PageRequest pageRequest,List<String> requiredColumn,SearchParams searchParams){

        //默认给必填条件加值
        requiredColumn.add("name");
        requiredColumn.add("station_shortname");
        requiredColumn.add("station_group_shortname");
        requiredColumn.add("station_address");
        requiredColumn.add("station_gdprovince");
        requiredColumn.add("station_yueyun");
        requiredColumn.add("station_companyid");
        requiredColumn.add("station_companyname");
        requiredColumn.add("station_company");
        requiredColumn.add("station_level");
        requiredColumn.add("station_servicetype");
        requiredColumn.add("station_area");
        requiredColumn.add("station_parea");
        requiredColumn.add("station_leasearea");
        requiredColumn.add("station_pnum");
        requiredColumn.add("station_type");
        requiredColumn.add("station_businessnature");
        requiredColumn.add("station_own");
        requiredColumn.add("station_businessmode");

        boolean updateOperation=Boolean.parseBoolean(searchParams.getSearchMap().get("updateOperation").toString());
        Page<Station> pageResult;
        if(updateOperation){
            //从数据库查询全部数据
            //查询数据库数据量
            int result=dao.selectRequiredData(requiredColumn);
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if(result>0){
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.STASION_REQUIRED_TIME);
                pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.STASION_REQUIRED_DATA);
            }else{
                setSyncTime(RedisCacheKey.STASION_REQUIRED_TIME);
                pageResult=new PageImpl<>(new ArrayList<Station>(),pageRequest,0);
            }
        }else{
            //查询缓存数据
            pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.STASION_REQUIRED_DATA);
            //判断缓存是否有值
            if((!pageResult.getContent().isEmpty())&&pageResult.getContent().size()>0){
                return pageResult;
            }else{
                //从数据库查询全部数据
                //查询数据库数据量
                int result=dao.selectRequiredData(requiredColumn);
                //如果没有数据直接返回空值,如果有数据,从redis里分页取值
                if(result>0){
                    //有数据设置同步时间
                    setSyncTime(RedisCacheKey.STASION_REQUIRED_TIME);
                    pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.STASION_REQUIRED_DATA);
                }else{
                    setSyncTime(RedisCacheKey.STASION_REQUIRED_TIME);
                    return pageResult;
                }
            }
        }
        return pageResult;
    }

    /**
     * 从缓存中取所有数据导出
     * @return
     */
    public Map<String,List<String>> selectAllCacheForExcel(){
        Map<String,List<String>> resultMap=new HashMap<>();
        //取相似度匹配结果
        int compareLength=redisTemplate.llen(RedisCacheKey.STASION_COMPARE_DATA).intValue();

        if(compareLength>0){
            List<String> compareList=redisTemplate.lrange(RedisCacheKey.STASION_COMPARE_DATA,0,compareLength);
            resultMap.put("compareData",compareList);
        }else{
            resultMap.put("compareData",new ArrayList<String>());
        }

        //取唯一性校验的数据
        int onlyLength=redisTemplate.llen(RedisCacheKey.STASION_ONLY_DATA).intValue();
        if(onlyLength>0){
            List<String> onlyData=redisTemplate.lrange(RedisCacheKey.STASION_ONLY_DATA,0,onlyLength);
            resultMap.put("onlyData",onlyData);
        }else{
            resultMap.put("onlyData",new ArrayList<String>());
        }

        //取必填校验的数据
        int requiredLength=redisTemplate.llen(RedisCacheKey.STASION_REQUIRED_DATA).intValue();
        if(requiredLength>0){
            List<String> requiredData=redisTemplate.lrange(RedisCacheKey.STASION_REQUIRED_DATA,0,requiredLength);
            resultMap.put("requiredData",requiredData);
        }else{
            resultMap.put("requiredData",new ArrayList<String>());
        }

        return resultMap;
    }
}
