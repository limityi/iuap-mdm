package com.yonyou.iuap.project.service;

import com.google.gson.Gson;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.cache.RedisUtil;
import com.yonyou.iuap.project.entity.Fleet;
import com.yonyou.iuap.project.entity.Merchants;
import com.yonyou.iuap.project.repository.FleetDao;
import com.yonyou.iuap.project.repository.FleetRepository;
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
 * <p>
 * Title: CardTableMetaService
 * </p>
 * <p>
 * Description:
 * </p>
 */
@Service
public class FleetService {

    @Autowired
    private FleetDao dao;

    @Autowired
    private FleetRepository fleetRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    private Gson gson = new Gson();

    /**
     * Description:通过非主键字段查询 List<CardTable>
     *
     * @param str
     */

    public Page<Fleet> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {

        Map<String, Object> searchMap = searchParams.getSearchMap();

        String inputStr = String.valueOf(searchMap.get("searchParam"));
        if (!inputStr.isEmpty()) {
            try {
                String con = URLDecoder.decode(inputStr, "UTF-8");
                searchMap.put("searchParam", con);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        // 查询缓存数据
        Page<Fleet> pageResult;
        
        String requestId=UUID.randomUUID().toString();

        boolean updateOperation = Boolean.parseBoolean(searchMap.get("updateOperation").toString());
        if (updateOperation) {
        	this.syncCacheData(requestId);           
            // 比较完之后再从缓存取值
            pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.FLEET_COMPARE_DATA);
        } else {
        	if(searchMap.get("searchParam").equals("null")){
            // 查询缓存数据
            pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.FLEET_COMPARE_DATA);

            // 判断缓存是否有值
            if ((!pageResult.getContent().isEmpty()) && pageResult.getContent().size() > 0) {
                return pageResult;
            } else {
            	this.syncCacheData(requestId);
                // 比较完之后再从缓存取值
                pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.FLEET_COMPARE_DATA);
            }
        	}else{
        		pageResult=dao.selectCacheByCondition(pageRequest,RedisCacheKey.FLEET_COMPARE_DATA,searchMap);
        	}
        }
        return pageResult;
    }

    public void save(List<Fleet> recordList) {
        List<Fleet> addList = new ArrayList<>(recordList.size());
        List<Fleet> updateList = new ArrayList<>(recordList.size());
        for (Fleet meta : recordList) {
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

    public void batchDeleteByPrimaryKey(List<Fleet> list) {
        dao.batchDelete(list);
    }

    /**
     * 获取公交线路同步时间
     *
     * @param fieldName
     * @return String
     */
    public String getSyncTime(String fieldName) {
        return RedisUtil.getSyncTime(redisTemplate, RedisCacheKey.FLEET_COMPARE_TIME, fieldName);
    }

    /**
     * 设置公交线路同步时间
     *
     * @param fieldName
     */
    private void setSyncTime(String fieldName) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RedisUtil.setSyncTime(redisTemplate, RedisCacheKey.FLEET_COMPARE_TIME, fieldName, format.format(new Date()));
    }

    /**
     * 公交线路相似度比较，并将数据存储在redis
     *
     * @param pageResult
     * @param searchMap
     */
    private void similarityMatch(Page<Fleet> pageResult) {
    	Map<String, Object> searchMap = new HashMap<>();
    	// 匹配之前，先删除redis数据
        //redisTemplate.del(RedisCacheKey.FLEET_COMPARE_DATA);
        // 处理数据，相似度检查
        // 根据查询的参数，看是哪个字段需要检查相似度
        // 循环的list
        List<Fleet> fleetList = pageResult.getContent();
        // 匹配的list
        CopyOnWriteArrayList<Fleet> fleetMatch = new CopyOnWriteArrayList<>(pageResult.getContent());
        // 结果的list
        List<Fleet> fleetResult = new ArrayList<>();

        // 测试代码,只匹配Name
        searchMap.put("SimilarityMatch", "Name");
        Object object = searchMap.get("SimilarityMatch");

        // 判断匹配条件,默认使用Name匹配
        if (object != null) {
            // 匹配标志
            int tag = 1;

            // 取匹配的字段
            String matchField = "get" + object.toString();

            // 循环判断
            for (Fleet fleet : fleetList) {

                // 放值的标志
                boolean tagFleet = true;

                try {

                    // 判断结果集是否已经包含
                    if (!contain(fleetResult, fleet)) {
                        // 反射取字段值
                        Method method = fleet.getClass().getMethod(matchField, null);
                        String field1 = (String) method.invoke(fleet);

                        // 循环去匹配
                        for (Fleet fl : fleetMatch) {
                            // 如果不是同一条数据则进行匹配
                            if (!fleet.getMdm_code().equals(fl.getMdm_code())) {
                                String field2 = (String) fl.getClass().getMethod(matchField, null).invoke(fl);
                                // 匹配结果
                                double similarity = SimilarityMatch.getSimilarity(field1, field2);
                                // 大于80%
                                if (similarity > 0.9) {
                                    // 如果第一次匹配成功,将源数据也放入结果集
                                    if (tagFleet) {
                                        fleet.setTag(String.valueOf(tag));
                                        fleetResult.add(fleet);
                                        // 同时将数据写入redis
                                        redisTemplate.rpush(RedisCacheKey.FLEET_COMPARE_DATA, gson.toJson(fleet));
                                        tagFleet = false;
                                    }

                                    // 将相似度设置
                                    fl.setSimilarity(dobleFormat(similarity));
                                    fl.setTag(String.valueOf(tag));
                                    fleetResult.add(fl);
                                    // 同时将数据写入redis
                                    redisTemplate.rpush(RedisCacheKey.FLEET_COMPARE_DATA, gson.toJson(fl));
                                    fleetMatch.remove(fl);
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
     * 判断结果集是否包含比较的数据 包含返回 true
     *
     * @param list
     * @param FLEET
     * @return
     */
    private boolean contain(List<Fleet> list, Fleet fleet) {
        boolean result = false;
        if (list != null && list.size() > 0 && fleet != null) {
            for (Fleet fl : list) {
                // 比较id
                if (fl.getId().equals(fleet.getId())) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 将double转化为百分数返回
     *
     * @param number
     * @return
     */
    private String dobleFormat(double number) {
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(2);
        return nt.format(number);
    }

    /**
     * 定时任务调用方法
     */
    public void fleetJob() {
    	String requestId=UUID.randomUUID().toString();
        this.syncCacheData(requestId);
    }

    public void fleetOnlyJob() {
        dao.selectOnlyValidateData();
        setSyncTime(RedisCacheKey.FLEET_ONLY_TIME);
    }

    public void fleetRequiredJob() {
        List<String> requiredColumn = new ArrayList<>();

        //默认给必填条件加值
        requiredColumn.add("name");
        requiredColumn.add("code");
        requiredColumn.add("business_org");

        Map<String, Object> searchMap = new HashMap<>();

        dao.selectRequiredData(requiredColumn, searchMap);
        setSyncTime(RedisCacheKey.FLEET_REQUIRED_TIME);

    }

    /**
     * 查询全部唯一性校验失败的数据
     *
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<Fleet> selectOnlyValidateByPage(PageRequest pageRequest, SearchParams searchParams) {

        boolean updateOperation = Boolean.parseBoolean(searchParams.getSearchMap().get("updateOperation").toString());

        //查询缓存数据
        Page<Fleet> pageResult;

        if (updateOperation) {
            //从数据库查询全部数据
            //查询数据库数据量
            int result = dao.selectOnlyValidateData();
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if (result > 0) {
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.FLEET_ONLY_TIME);                
                pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.FLEET_ONLY_DATA);
            } else {
                setSyncTime(RedisCacheKey.FLEET_ONLY_TIME);
                pageResult = new PageImpl<>(new ArrayList<Fleet>(), pageRequest, 0);
            }
        } else {
            //查询缓存数据
            pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.FLEET_ONLY_DATA);
            //判断缓存是否有值
            if ((!pageResult.getContent().isEmpty()) && pageResult.getContent().size() > 0) {
                return pageResult;
            } else {
                //从数据库查询全部数据
                //查询数据库数据量
                int result = dao.selectOnlyValidateData();
                //如果没有数据直接返回空值,如果有数据,从redis里分页取值
                if (result > 0) {
                    //有数据设置同步时间
                    setSyncTime(RedisCacheKey.FLEET_ONLY_TIME);
                    pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.FLEET_ONLY_DATA);
                } else {
                    setSyncTime(RedisCacheKey.FLEET_ONLY_TIME);
                    return pageResult;
                }
            }
        }
        return pageResult;
    }

    /**
     * 查询必填项校验
     *
     * @param pageRequest
     * @param requiredColumn
     * @return
     */
    public Page<Fleet> selectRequiredData(PageRequest pageRequest, List<String> requiredColumn, SearchParams searchParams) {
        //默认给必填条件加值
        requiredColumn.add("name");
        requiredColumn.add("code");
        requiredColumn.add("business_org");

        Map<String, Object> searchMap = searchParams.getSearchMap();

        String inputStr = String.valueOf(searchMap.get("searchParam"));
        if (!inputStr.isEmpty()) {
            try {
                String con = URLDecoder.decode(inputStr, "UTF-8");
                searchMap.put("searchParam", con);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String requestId=UUID.randomUUID().toString();

        boolean updateOperation = Boolean.parseBoolean(searchParams.getSearchMap().get("updateOperation").toString());
        Page<Fleet> pageResult = null;
        if (updateOperation) {
        	boolean lock=RedisUtil.tryGetDistributedLock(redisTemplate,RedisCacheKey.FLEET_REQUIRED_DATA,requestId,RedisUtil.getLock_timeout());
            if(lock){
            	redisTemplate.del(RedisCacheKey.FLEET_REQUIRED_DATA);
            //从数据库查询全部数据
            //查询数据库数据量
            int result = dao.selectRequiredData(requiredColumn, searchMap);
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if (result > 0) {
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.FLEET_REQUIRED_TIME);
                RedisUtil.releaseDistributedLock(redisTemplate,RedisCacheKey.FLEET_REQUIRED_DATA,requestId);
                pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.FLEET_REQUIRED_DATA);
            } else {
                setSyncTime(RedisCacheKey.FLEET_REQUIRED_TIME);
                RedisUtil.releaseDistributedLock(redisTemplate,RedisCacheKey.FLEET_REQUIRED_DATA,requestId);
                pageResult = new PageImpl<>(new ArrayList<Fleet>(), pageRequest, 0);
            }
            }
        } else {
        	if(searchMap.get("searchParam").equals("null")){
            //查询缓存数据
            pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.FLEET_REQUIRED_DATA);
            //判断缓存是否有值
            if ((!pageResult.getContent().isEmpty()) && pageResult.getContent().size() > 0) {
                return pageResult;
            } else {
            	boolean lock=RedisUtil.tryGetDistributedLock(redisTemplate,RedisCacheKey.FLEET_REQUIRED_DATA,requestId,RedisUtil.getLock_timeout());
                if(lock){
                	redisTemplate.del(RedisCacheKey.FLEET_REQUIRED_DATA);
                //从数据库查询全部数据
                //查询数据库数据量
                int result = dao.selectRequiredData(requiredColumn, searchMap);
                //如果没有数据直接返回空值,如果有数据,从redis里分页取值
                if (result > 0) {
                    //有数据设置同步时间
                    setSyncTime(RedisCacheKey.FLEET_REQUIRED_TIME);
                    RedisUtil.releaseDistributedLock(redisTemplate,RedisCacheKey.FLEET_REQUIRED_DATA,requestId);
                    pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.FLEET_REQUIRED_DATA);
                } else {
                    setSyncTime(RedisCacheKey.FLEET_REQUIRED_TIME);
                    RedisUtil.releaseDistributedLock(redisTemplate,RedisCacheKey.FLEET_REQUIRED_DATA,requestId);
                    pageResult=new PageImpl<>(new ArrayList<Fleet>(),pageRequest,0);
                }
            }
            }
        	}else{
        		pageResult=dao.selectCacheByConditionRequired(pageRequest,RedisCacheKey.FLEET_COMPARE_DATA,searchMap);           
        	}
        }
        return pageResult;
    }

    /**
     * 从缓存中取所有数据导出
     *
     * @return
     */
    public Map<String, List<String>> selectAllCacheForExcel() {
        Map<String, List<String>> resultMap = new HashMap<>();
        //取相似度匹配结果
        int compareLength = redisTemplate.llen(RedisCacheKey.FLEET_COMPARE_DATA).intValue();

        if (compareLength > 0) {
            List<String> compareList = redisTemplate.lrange(RedisCacheKey.FLEET_COMPARE_DATA, 0, compareLength);
            resultMap.put("compareData", compareList);
        } else {
            resultMap.put("compareData", new ArrayList<String>());
        }

        //取唯一性校验的数据
        int onlyLength = redisTemplate.llen(RedisCacheKey.FLEET_ONLY_DATA).intValue();
        if (onlyLength > 0) {
            List<String> onlyData = redisTemplate.lrange(RedisCacheKey.FLEET_ONLY_DATA, 0, onlyLength);
            resultMap.put("onlyData", onlyData);
        } else {
            resultMap.put("onlyData", new ArrayList<String>());
        }

        //取必填校验的数据
        int requiredLength = redisTemplate.llen(RedisCacheKey.FLEET_REQUIRED_DATA).intValue();
        if (requiredLength > 0) {
            List<String> requiredData = redisTemplate.lrange(RedisCacheKey.FLEET_REQUIRED_DATA, 0, requiredLength);
            resultMap.put("requiredData", requiredData);
        } else {
            resultMap.put("requiredData", new ArrayList<String>());
        }

        return resultMap;
    }
    
    /**
     * 缓存处理(相似度)
     * @param requestId
     */
    private void syncCacheData(String requestId){

        boolean lock=RedisUtil.tryGetDistributedLock(redisTemplate,RedisCacheKey.FLEET_COMPARE_DATA,requestId,RedisUtil.getLock_timeout());

        if(lock){
            //匹配之前，先删除redis数据
            redisTemplate.del(RedisCacheKey.FLEET_COMPARE_DATA);
            //从数据库查询全部数据
            //查询数据库数据量
            int size=fleetRepository.countAll();
            //定义新的分页数据,用来查询全部
            PageRequest pageRequestTemp=new PageRequest(0,size);
            //查询全部结果
            Page<Fleet> pageResult = dao.selectAllByPage(pageRequestTemp);
            //相似度比较
            similarityMatch(pageResult);
            //比较完之后更新对比同步时间
            setSyncTime(RedisCacheKey.FLEET_COMPARE_TIME);

            //释放分布式锁
            RedisUtil.releaseDistributedLock(redisTemplate,RedisCacheKey.FLEET_COMPARE_DATA,requestId);
        }

    }

}
