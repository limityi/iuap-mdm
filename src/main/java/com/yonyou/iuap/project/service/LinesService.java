package com.yonyou.iuap.project.service;

import com.google.gson.Gson;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.cache.RedisUtil;
import com.yonyou.iuap.project.entity.Lines;
import com.yonyou.iuap.project.entity.Station;
import com.yonyou.iuap.project.repository.LinesDao;
import com.yonyou.iuap.project.repository.LinesRepository;
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
public class LinesService {

    @Autowired
    private LinesDao dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LinesRepository linesRepository;

    private Gson gson = new Gson();

    /**
     * 查询所有数据
     *
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<Lines> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        //Page<Lines> pageResult = dao.selectAllByPage(pageRequest, searchParams.getSearchMap()) ;
        //return pageResult;
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

        //查询缓存数据
        Page<Lines> pageResult;
        
        String requestId=UUID.randomUUID().toString();

        boolean updateOperation = Boolean.parseBoolean(searchMap.get("updateOperation").toString());
        if (updateOperation) {
            this.syncCacheData(requestId);
            //比较完之后再从缓存取值
            pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LINE_COMPARE_DATA);
        } else {
        	if(searchMap.get("searchParam").equals("null")){
            //查询缓存数据
            pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LINE_COMPARE_DATA);

            //判断缓存是否有值
            if ((!pageResult.getContent().isEmpty()) && pageResult.getContent().size() > 0) {
                return pageResult;
            } else {
                this.syncCacheData(requestId);
                //比较完之后再从缓存取值
                pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LINE_COMPARE_DATA);
            }
        	}else{
        		pageResult=dao.selectCacheByCondition(pageRequest,RedisCacheKey.LINE_COMPARE_DATA,searchMap);
        	}
        }
        return pageResult;
    }

    public void save(List<Lines> recordList) {
        List<Lines> addList = new ArrayList<>(recordList.size());
        List<Lines> updateList = new ArrayList<>(recordList.size());
        for (Lines meta : recordList) {
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

    public void batchDeleteByPrimaryKey(List<Lines> list) {
        dao.batchDelete(list);
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
     * 客运线路相似度比较，并将数据存储在redis
     *
     * @param pageResult
     * @param searchMap
     */
    private void similarityMatch(Page<Lines> pageResult) {
    	Map<String, Object> searchMap =new HashMap<>();
    	//匹配之前，先删除redis数据
        //redisTemplate.del(RedisCacheKey.LINE_COMPARE_DATA);
        //处理数据，相似度检查
        //根据查询的参数，看是哪个字段需要检查相似度
        //循环的list
        List<Lines> linesList = pageResult.getContent();
        //匹配的list
        CopyOnWriteArrayList<Lines> linesMatch = new CopyOnWriteArrayList<>(pageResult.getContent());
        //结果的list
        List<Lines> linesResult = new ArrayList<>();

        //测试代码,只匹配Name
        searchMap.put("SimilarityMatch", "Name");
        Object object = searchMap.get("SimilarityMatch");

        //判断匹配条件,默认使用Name匹配
        if (object != null) {
            //匹配标志
            int tag = 1;

            //取匹配的字段
            String matchField = "get" + object.toString();

            //循环判断
            for (Lines lines : linesList) {

                //放值的标志
                boolean tagLines = true;

                try {

                    //判断结果集是否已经包含
                    if (!contain(linesResult, lines)) {
                        //反射取字段值
                        Method method = lines.getClass().getMethod(matchField, null);
                        String field1 = (String) method.invoke(lines);

                        //循环去匹配
                        for (Lines lin : linesMatch) {
                            //如果不是同一条数据则进行匹配
                            if (!lines.getMdm_code().equals(lin.getMdm_code())) {
                                String field2 = (String) lin.getClass().getMethod(matchField, null).invoke(lin);
                                //匹配结果
                                double similarity = SimilarityMatch.getSimilarity(field1, field2);
                                //大于80%
                                if (similarity > 0.9) {
                                    //如果第一次匹配成功,将源数据也放入结果集
                                    if (tagLines) {
                                        lines.setTag(String.valueOf(tag));
                                        linesResult.add(lines);
                                        //同时将数据写入redis
                                        redisTemplate.rpush(RedisCacheKey.LINE_COMPARE_DATA, gson.toJson(lines));
                                        tagLines = false;
                                    }

                                    //将相似度设置
                                    lin.setSimilarity(dobleFormat(similarity));
                                    lin.setTag(String.valueOf(tag));
                                    linesResult.add(lin);
                                    //同时将数据写入redis
                                    redisTemplate.rpush(RedisCacheKey.LINE_COMPARE_DATA, gson.toJson(lin));
                                    linesMatch.remove(lin);
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
     *
     * @param list
     * @param lines
     * @return
     */
    private boolean contain(List<Lines> list, Lines lines) {
        boolean result = false;
        if (list != null && list.size() > 0 && lines != null) {
            for (Lines lin : list) {
                //比较id
                if (lin.getId().equals(lines.getId())) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 定时任务调用方法
     */
    public void linesJob() {
    	String requestId=UUID.randomUUID().toString();
        this.syncCacheData(requestId);
    }

    public void linesOnlyJob() {
        dao.selectOnlyValidateData();
        setSyncTime(RedisCacheKey.LINE_ONLY_TIME);
    }

    public void linesRequiredJob() {
        List<String> requiredColumn = new ArrayList<>();

        //默认给必填条件加值
        requiredColumn.add("code");
        requiredColumn.add("name");
        requiredColumn.add("line_shortname");
        requiredColumn.add("line_startdistrict");
        requiredColumn.add("line_start");
        requiredColumn.add("line_endistrict");
        requiredColumn.add("line_end");
        requiredColumn.add("line_businesstype");
        requiredColumn.add("line_businessnature");
        requiredColumn.add("line_amount");
        requiredColumn.add("line_avgday_bus");
        requiredColumn.add("line_avgday_income");
        requiredColumn.add("line_avgday_cust");
        requiredColumn.add("line_begtime");
        requiredColumn.add("line_endtime");

        Map<String, Object> searchMap = new HashMap<>();

        dao.selectRequiredData(requiredColumn, searchMap);
        setSyncTime(RedisCacheKey.LINE_REQUIRED_TIME);
    }

    /**
     * 获取客运线路同步时间
     *
     * @param fieldName
     * @return String
     */
    public String getSyncTime(String fieldName) {
        return RedisUtil.getSyncTime(redisTemplate, RedisCacheKey.LINE_COMPARE_TIME, fieldName);
    }

    /**
     * 设置客运线路同步时间
     *
     * @param fieldName
     */
    private void setSyncTime(String fieldName) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RedisUtil.setSyncTime(redisTemplate, RedisCacheKey.LINE_COMPARE_TIME, fieldName, format.format(new Date()));
    }

    /**
     * 查询全部唯一性校验失败的数据
     *
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<Lines> selectOnlyValidateByPage(PageRequest pageRequest, SearchParams searchParams) {

        boolean updateOperation = Boolean.parseBoolean(searchParams.getSearchMap().get("updateOperation").toString());

        //查询缓存数据
        Page<Lines> pageResult;

        if (updateOperation) {
            //从数据库查询全部数据
            //查询数据库数据量
            int result = dao.selectOnlyValidateData();
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if (result > 0) {
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.LINE_ONLY_TIME);
                pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LINE_ONLY_DATA);
            } else {
                setSyncTime(RedisCacheKey.LINE_ONLY_TIME);
                pageResult = new PageImpl<>(new ArrayList<Lines>(), pageRequest, 0);
            }
        } else {
            //查询缓存数据
            pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LINE_ONLY_DATA);
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
                    setSyncTime(RedisCacheKey.LINE_ONLY_TIME);
                    pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LINE_ONLY_DATA);
                } else {
                    setSyncTime(RedisCacheKey.LINE_ONLY_TIME);
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
    public Page<Lines> selectRequiredData(PageRequest pageRequest, List<String> requiredColumn, SearchParams searchParams) {

        //默认给必填条件加值
        requiredColumn.add("code");
        requiredColumn.add("name");
        requiredColumn.add("line_shortname");
        requiredColumn.add("line_startdistrict");
        requiredColumn.add("line_start");
        requiredColumn.add("line_endistrict");
        requiredColumn.add("line_end");
        requiredColumn.add("line_businesstype");
        requiredColumn.add("line_businessnature");
        requiredColumn.add("line_amount");
        requiredColumn.add("line_avgday_bus");
        requiredColumn.add("line_avgday_income");
        requiredColumn.add("line_avgday_cust");
        requiredColumn.add("line_begtime");
        requiredColumn.add("line_endtime");

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
        Page<Lines> pageResult = null;
        if (updateOperation) {
        	boolean lock=RedisUtil.tryGetDistributedLock(redisTemplate,RedisCacheKey.LINE_REQUIRED_DATA,requestId,RedisUtil.getLock_timeout());
            if(lock){
        	//从数据库查询全部数据
            //查询数据库数据量
            int result = dao.selectRequiredData(requiredColumn, searchMap);
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if (result > 0) {
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.LINE_REQUIRED_TIME);
                RedisUtil.releaseDistributedLock(redisTemplate,RedisCacheKey.LINE_REQUIRED_DATA,requestId);
                pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LINE_REQUIRED_DATA);
            } else {
                setSyncTime(RedisCacheKey.LINE_REQUIRED_TIME);
                RedisUtil.releaseDistributedLock(redisTemplate,RedisCacheKey.LINE_REQUIRED_DATA,requestId);
                pageResult = new PageImpl<>(new ArrayList<Lines>(), pageRequest, 0);
            }
            }
        } else {
        	if(searchMap.get("searchParam").equals("null")){
            //查询缓存数据
            pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LINE_REQUIRED_DATA);
            //判断缓存是否有值
            if ((!pageResult.getContent().isEmpty()) && pageResult.getContent().size() > 0) {
                return pageResult;
            } else {   
            	boolean lock=RedisUtil.tryGetDistributedLock(redisTemplate,RedisCacheKey.LINE_REQUIRED_DATA,requestId,RedisUtil.getLock_timeout());
                if(lock){
                	redisTemplate.del(RedisCacheKey.LINE_REQUIRED_DATA);
            	//从数据库查询全部数据
                //查询数据库数据量
                int result = dao.selectRequiredData(requiredColumn, searchMap);
                //如果没有数据直接返回空值,如果有数据,从redis里分页取值
                if (result > 0) {
                    //有数据设置同步时间
                    setSyncTime(RedisCacheKey.LINE_REQUIRED_TIME);
                    RedisUtil.releaseDistributedLock(redisTemplate,RedisCacheKey.LINE_REQUIRED_DATA,requestId);
                    pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LINE_REQUIRED_DATA);
                } else {
                    setSyncTime(RedisCacheKey.LINE_REQUIRED_TIME);
                    RedisUtil.releaseDistributedLock(redisTemplate,RedisCacheKey.LINE_REQUIRED_DATA,requestId);
                    pageResult=new PageImpl<>(new ArrayList<Lines>(),pageRequest,0);
                }
            }
            }
            }else{
            	pageResult=dao.selectCacheByConditionRequired(pageRequest,RedisCacheKey.LINE_REQUIRED_DATA,searchMap);
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
        int compareLength = redisTemplate.llen(RedisCacheKey.LINE_COMPARE_DATA).intValue();

        if (compareLength > 0) {
            List<String> compareList = redisTemplate.lrange(RedisCacheKey.LINE_COMPARE_DATA, 0, compareLength);
            resultMap.put("compareData", compareList);
        } else {
            resultMap.put("compareData", new ArrayList<String>());
        }

        //取唯一性校验的数据
        int onlyLength = redisTemplate.llen(RedisCacheKey.LINE_ONLY_DATA).intValue();
        if (onlyLength > 0) {
            List<String> onlyData = redisTemplate.lrange(RedisCacheKey.LINE_ONLY_DATA, 0, onlyLength);
            resultMap.put("onlyData", onlyData);
        } else {
            resultMap.put("onlyData", new ArrayList<String>());
        }

        //取必填校验的数据
        int requiredLength = redisTemplate.llen(RedisCacheKey.LINE_REQUIRED_DATA).intValue();
        if (requiredLength > 0) {
            List<String> requiredData = redisTemplate.lrange(RedisCacheKey.LINE_REQUIRED_DATA, 0, requiredLength);
            resultMap.put("requiredData", requiredData);
        } else {
            resultMap.put("requiredData", new ArrayList<String>());
        }

        return resultMap;
    }

    /**
     * 去除相似度比较，参数为站场编码
     *
     * @param code
     */
    public void removeData(String code) {
        int result = linesRepository.removeData(code);

        if (result > 0) {
        	Map<String,Object> searchMap=new HashMap<>();

            String requestId=UUID.randomUUID().toString();
            this.syncCacheData(requestId);
        }
    }
    
    /**
     * 缓存处理(相似度)
     * @param requestId
     */
    private void syncCacheData(String requestId){

        boolean lock=RedisUtil.tryGetDistributedLock(redisTemplate,RedisCacheKey.LINE_COMPARE_DATA,requestId,RedisUtil.getLock_timeout());

        if(lock){
            //匹配之前，先删除redis数据
            redisTemplate.del(RedisCacheKey.LINE_COMPARE_DATA);
            //从数据库查询全部数据
            //查询数据库数据量
            int size=linesRepository.countAll();
            //定义新的分页数据,用来查询全部
            PageRequest pageRequestTemp=new PageRequest(0,size);
            //查询全部结果
            Page<Lines> pageResult = dao.selectAllByPage(pageRequestTemp);
            //相似度比较
            similarityMatch(pageResult);
            //比较完之后更新对比同步时间
            setSyncTime(RedisCacheKey.LINE_COMPARE_TIME);

            //释放分布式锁
            RedisUtil.releaseDistributedLock(redisTemplate,RedisCacheKey.LINE_COMPARE_DATA,requestId);
        }

    }
}
