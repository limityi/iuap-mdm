package com.yonyou.iuap.project.service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.cache.RedisUtil;
import com.yonyou.iuap.project.entity.Bus;
import com.yonyou.iuap.project.repository.BusDao;
import com.yonyou.iuap.project.repository.BusRepository;
import com.yonyou.iuap.project.util.SimilarityMatch;

/**
 * <p>Title: CardTableMetaService</p>
 * <p>Description: </p>
 */
@Service
public class BusService {
	
	@Autowired
    private BusDao dao;
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Autowired
    private BusRepository busRepository;
    
    private Gson gson =new Gson();
    
    /**
     * Description:通过非主键字段查询
     * List<CardTable>
     * @param str
     */
       
    public Page<Bus> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        //Page<Lines> pageResult = dao.selectAllByPage(pageRequest, searchParams.getSearchMap()) ;
		//return pageResult;
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
    	Page<Bus> pageResult;
    	
    	boolean updateOperation=Boolean.parseBoolean(searchMap.get("updateOperation").toString());
    	if(updateOperation){
    		//从数据库查询全部数据
            //查询数据库数据量
            int size=busRepository.countAll();
            //定义新的分页数据，用来查询全部
            PageRequest pageRequestTemp=new PageRequest(0, size);
            //查询全部结果
            pageResult = dao.selectAllByPage(pageRequestTemp, searchMap);
            //相似度比较
            similarityMatch(pageResult,searchMap);
            //比较完之后更新对比同步时间
            setSyncTime(RedisCacheKey.BUS_COMPARE_TIME);
            //比较完之后再从缓存取值
            pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.BUS_COMPARE_DATA);
    	}else{
    		//查询缓存数据
            pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.BUS_COMPARE_DATA);

            //判断缓存是否有值
            if((!pageResult.getContent().isEmpty())&&pageResult.getContent().size()>0){
                return pageResult;
            }else{
                //从数据库查询全部数据
                //查询数据库数据量
                int size=busRepository.countAll();
                //定义新的分页数据,用来查询全部
                PageRequest pageRequestTemp=new PageRequest(0,size);
                //查询全部结果
                pageResult = dao.selectAllByPage(pageRequestTemp, searchMap);
                //相似度比较
                similarityMatch(pageResult,searchMap);
                //比较完之后更新对比同步时间
                setSyncTime(RedisCacheKey.BUS_COMPARE_TIME);
                //比较完之后再从缓存取值
                pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.BUS_COMPARE_DATA);
            }
    	}
    	return pageResult;
    }
    
    public void save(List<Bus> recordList) {
        List<Bus> addList = new ArrayList<>(recordList.size());
        List<Bus> updateList = new ArrayList<>(recordList.size());
        for (Bus meta : recordList) {
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
    
    public void batchDeleteByPrimaryKey(List<Bus> list) {
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
     * 车辆相似度比较，并将数据存储在redis
     * @param pageResult
     * @param searchMap
     */
    private void similarityMatch(Page<Bus> pageResult, Map<String, Object> searchMap){
        //匹配之前，先删除redis数据
        redisTemplate.del(RedisCacheKey.BUS_COMPARE_DATA);
        //处理数据，相似度检查
        //根据查询的参数，看是哪个字段需要检查相似度
        //循环的list
        List<Bus> busList=pageResult.getContent();
        //匹配的list
        CopyOnWriteArrayList<Bus> busMatch=new CopyOnWriteArrayList<>(pageResult.getContent());
        //结果的list
        List<Bus> busResult=new ArrayList<>();

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
            for (Bus bus : busList) {

                //放值的标志
                boolean tagBus=true;

                try {

                    //判断结果集是否已经包含
                    if(!contain(busResult,bus)){
                        //反射取字段值
                        Method method=bus.getClass().getMethod(matchField,null);
                        String field1= (String) method.invoke(bus);

                        //循环去匹配
                        for (Bus b:busMatch ) {
                            //如果不是同一条数据则进行匹配
                            if(!bus.getMdm_code().equals(b.getMdm_code())){
                                String field2= (String) b.getClass().getMethod(matchField,null).invoke(b);
                                //匹配结果
                                double similarity= SimilarityMatch.getSimilarity(field1,field2);
                                //大于80%
                                if(similarity>0.9){
                                    //如果第一次匹配成功,将源数据也放入结果集
                                    if(tagBus){
                                        bus.setTag(String.valueOf(tag));
                                        busResult.add(bus);
                                        //同时将数据写入redis
                                        redisTemplate.rpush(RedisCacheKey.BUS_COMPARE_DATA,gson.toJson(bus));
                                        tagBus=false;
                                    }

                                    //将相似度设置
                                    b.setSimilarity(dobleFormat(similarity));
                                    b.setTag(String.valueOf(tag));
                                    busResult.add(b);
                                    //同时将数据写入redis
                                    redisTemplate.rpush(RedisCacheKey.BUS_COMPARE_DATA,gson.toJson(b));
                                    busMatch.remove(b);
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
     * @param lines
     * @return
     */
    private boolean contain(List<Bus> list, Bus bus){
        boolean result=false;
        if(list!=null&&list.size()>0&&bus!=null){
            for (Bus b:list){
                //比较id
                if(b.getId().equals(bus.getId())){
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
    public void BusJob(){
        Map<String,Object> searchMap=new HashMap<>();
        //从数据库查询全部数据
        //查询数据库数据量
        int size=busRepository.countAll();
        //定义新的分页数据,用来查询全部
        PageRequest pageRequestTemp=new PageRequest(0,size);
        //查询全部结果
        Page pageResult = dao.selectAllByPage(pageRequestTemp, searchMap);
        //相似度比较
        similarityMatch(pageResult,searchMap);
        //比较完之后更新站场同步时间
        setSyncTime(RedisCacheKey.BUS_COMPARE_TIME);
    }
    
    public void BusOnlyJob(){
        dao.selectOnlyValidateData();
        setSyncTime(RedisCacheKey.BUS_ONLY_TIME);
    }
    
    public void BusRequiredJob(){
        List<String> requiredColumn=new ArrayList<>();

        //默认给必填条件加值
        requiredColumn.add("name");
        requiredColumn.add("code");

        dao.selectRequiredData(requiredColumn);
        setSyncTime(RedisCacheKey.BUS_REQUIRED_TIME);
    }
    
    /**
     * 获取车辆同步时间
     * @param fieldName
     * @return String
     */
    public String getSyncTime(String fieldName){
        return RedisUtil.getSyncTime(redisTemplate,RedisCacheKey.BUS_COMPARE_TIME,fieldName);
    }
    
    /**
     * 设置车辆同步时间
     * @param fieldName
     */
    private void setSyncTime(String fieldName){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RedisUtil.setSyncTime(redisTemplate,RedisCacheKey.BUS_COMPARE_TIME,fieldName, format.format(new Date()));
    }
    
    /**
     * 查询全部唯一性校验失败的数据
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<Bus> selectOnlyValidateByPage(PageRequest pageRequest,SearchParams searchParams){

        boolean updateOperation=Boolean.parseBoolean(searchParams.getSearchMap().get("updateOperation").toString());

        //查询缓存数据
        Page<Bus> pageResult;

        if(updateOperation){
            //从数据库查询全部数据
            //查询数据库数据量
            int result=dao.selectOnlyValidateData();
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if(result>0){
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.BUS_ONLY_TIME);
                pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.BUS_ONLY_DATA);
            }else {
                setSyncTime(RedisCacheKey.BUS_ONLY_TIME);
                pageResult=new PageImpl<>(new ArrayList<Bus>(),pageRequest,0);
            }
        }else{
            //查询缓存数据
            pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.BUS_ONLY_DATA);
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
                    setSyncTime(RedisCacheKey.BUS_ONLY_TIME);
                    pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.BUS_ONLY_DATA);
                }else{
                    setSyncTime(RedisCacheKey.BUS_ONLY_TIME);
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
    public Page<Bus> selectRequiredData(PageRequest pageRequest,List<String> requiredColumn,SearchParams searchParams){

        //默认给必填条件加值
    	requiredColumn.add("name");
        requiredColumn.add("code");       

        boolean updateOperation=Boolean.parseBoolean(searchParams.getSearchMap().get("updateOperation").toString());
        Page<Bus> pageResult;
        if(updateOperation){
            //从数据库查询全部数据
            //查询数据库数据量
            int result=dao.selectRequiredData(requiredColumn);
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if(result>0){
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.BUS_REQUIRED_TIME);
                pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.BUS_REQUIRED_DATA);
            }else{
                setSyncTime(RedisCacheKey.BUS_REQUIRED_TIME);
                pageResult=new PageImpl<>(new ArrayList<Bus>(),pageRequest,0);
            }
        }else{
            //查询缓存数据
            pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.BUS_REQUIRED_DATA);
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
                    setSyncTime(RedisCacheKey.BUS_REQUIRED_TIME);
                    pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.BUS_REQUIRED_DATA);
                }else{
                    setSyncTime(RedisCacheKey.BUS_REQUIRED_TIME);
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
        int compareLength=redisTemplate.llen(RedisCacheKey.BUS_COMPARE_DATA).intValue();

        if(compareLength>0){
            List<String> compareList=redisTemplate.lrange(RedisCacheKey.BUS_COMPARE_DATA,0,compareLength);
            resultMap.put("compareData",compareList);
        }else{
            resultMap.put("compareData",new ArrayList<String>());
        }

        //取唯一性校验的数据
        int onlyLength=redisTemplate.llen(RedisCacheKey.BUS_ONLY_DATA).intValue();
        if(onlyLength>0){
            List<String> onlyData=redisTemplate.lrange(RedisCacheKey.BUS_ONLY_DATA,0,onlyLength);
            resultMap.put("onlyData",onlyData);
        }else{
            resultMap.put("onlyData",new ArrayList<String>());
        }

        //取必填校验的数据
        int requiredLength=redisTemplate.llen(RedisCacheKey.BUS_REQUIRED_DATA).intValue();
        if(requiredLength>0){
            List<String> requiredData=redisTemplate.lrange(RedisCacheKey.BUS_REQUIRED_DATA,0,requiredLength);
            resultMap.put("requiredData",requiredData);
        }else{
            resultMap.put("requiredData",new ArrayList<String>());
        }

        return resultMap;
    }

}
