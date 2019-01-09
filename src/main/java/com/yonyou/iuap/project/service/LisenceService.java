package com.yonyou.iuap.project.service;

import com.google.gson.Gson;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.cache.RedisUtil;
import com.yonyou.iuap.project.entity.Lisence;
import com.yonyou.iuap.project.repository.LisenceDao;
import com.yonyou.iuap.project.repository.LisenceRepository;
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
public class LisenceService {

    @Autowired
    private LisenceDao dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LisenceRepository lisenceRepository;

    private Gson gson = new Gson();


    /**
     * Description:通过非主键字段查询
     * List<CardTable>
     *
     * @param str
     */
    public Page<Lisence> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
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
        Page<Lisence> pageResult;

        boolean updateOperation = Boolean.parseBoolean(searchMap.get("updateOperation").toString());
        if (updateOperation) {
            //从数据库查询全部数据
            //查询数据库数据量
            int size = lisenceRepository.countAll();
            //定义新的分页数据，用来查询全部
            PageRequest pageRequestTemp = new PageRequest(0, size);
            //查询全部结果
            pageResult = dao.selectAllByPage(pageRequestTemp, searchMap);
            //相似度比较
            similarityMatch(pageResult, searchMap);
            //比较完之后更新对比同步时间
            setSyncTime(RedisCacheKey.LISENCE_COMPARE_TIME);
            //比较完之后再从缓存取值
            pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LISENCE_COMPARE_DATA);
        } else {
            //查询缓存数据
            pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LISENCE_COMPARE_DATA);

            //判断缓存是否有值
            if ((!pageResult.getContent().isEmpty()) && pageResult.getContent().size() > 0) {
                return pageResult;
            } else {
                //从数据库查询全部数据
                //查询数据库数据量
                int size = lisenceRepository.countAll();
                //定义新的分页数据,用来查询全部
                PageRequest pageRequestTemp = new PageRequest(0, size);
                //查询全部结果
                pageResult = dao.selectAllByPage(pageRequestTemp, searchMap);
                //相似度比较
                similarityMatch(pageResult, searchMap);
                //比较完之后更新对比同步时间
                setSyncTime(RedisCacheKey.LISENCE_COMPARE_TIME);
                //比较完之后再从缓存取值
                pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LISENCE_COMPARE_DATA);
            }
        }
        return pageResult;
    }

    public void save(List<Lisence> recordList) {
        List<Lisence> addList = new ArrayList<>(recordList.size());
        List<Lisence> updateList = new ArrayList<>(recordList.size());
        for (Lisence meta : recordList) {
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

    public void batchDeleteByPrimaryKey(List<Lisence> list) {
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
     * 线路牌相似度比较，并将数据存储在redis
     *
     * @param pageResult
     * @param searchMap
     */
    private void similarityMatch(Page<Lisence> pageResult, Map<String, Object> searchMap) {
        //匹配之前，先删除redis数据
        redisTemplate.del(RedisCacheKey.LISENCE_COMPARE_DATA);
        //处理数据，相似度检查
        //根据查询的参数，看是哪个字段需要检查相似度
        //循环的list
        List<Lisence> lisenceList = pageResult.getContent();
        //匹配的list
        CopyOnWriteArrayList<Lisence> lisenceMatch = new CopyOnWriteArrayList<>(pageResult.getContent());
        //结果的list
        List<Lisence> lisenceResult = new ArrayList<>();

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
            for (Lisence lisence : lisenceList) {

                //放值的标志
                boolean tagLisence = true;

                try {

                    //判断结果集是否已经包含
                    if (!contain(lisenceResult, lisence)) {
                        //反射取字段值
                        Method method = lisence.getClass().getMethod(matchField, null);
                        String field1 = (String) method.invoke(lisence);

                        //循环去匹配
                        for (Lisence lis : lisenceMatch) {
                            //如果不是同一条数据则进行匹配
                            if (!lisence.getMdm_code().equals(lis.getMdm_code())) {
                                String field2 = (String) lis.getClass().getMethod(matchField, null).invoke(lis);
                                //匹配结果
                                double similarity = SimilarityMatch.getSimilarity(field1, field2);
                                //大于80%
                                if (similarity > 0.8) {
                                    //如果第一次匹配成功,将源数据也放入结果集
                                    if (tagLisence) {
                                        lisence.setTag(String.valueOf(tag));
                                        lisenceResult.add(lisence);
                                        //同时将数据写入redis
                                        redisTemplate.rpush(RedisCacheKey.LISENCE_COMPARE_DATA, gson.toJson(lisence));
                                        tagLisence = false;
                                    }

                                    //将相似度设置
                                    lis.setSimilarity(dobleFormat(similarity));
                                    lis.setTag(String.valueOf(tag));
                                    lisenceResult.add(lis);
                                    //同时将数据写入redis
                                    redisTemplate.rpush(RedisCacheKey.LISENCE_COMPARE_DATA, gson.toJson(lis));
                                    lisenceMatch.remove(lis);
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
    private boolean contain(List<Lisence> list, Lisence lisence) {
        boolean result = false;
        if (list != null && list.size() > 0 && lisence != null) {
            for (Lisence lis : list) {
                //比较id
                if (lis.getId().equals(lisence.getId())) {
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
    public void lisenceJob() {
        Map<String, Object> searchMap = new HashMap<>();
        //从数据库查询全部数据
        //查询数据库数据量
        int size = lisenceRepository.countAll();
        //定义新的分页数据,用来查询全部
        PageRequest pageRequestTemp = new PageRequest(0, size);
        //查询全部结果
        Page pageResult = dao.selectAllByPage(pageRequestTemp, searchMap);
        //相似度比较
        similarityMatch(pageResult, searchMap);
        //比较完之后更新站场同步时间
        setSyncTime(RedisCacheKey.LISENCE_COMPARE_TIME);
    }

    public void lisenceOnlyJob() {
        dao.selectOnlyValidateData();
        setSyncTime(RedisCacheKey.LISENCE_ONLY_TIME);
    }

    public void lisenceRequiredJob() {
        List<String> requiredColumn = new ArrayList<>();

        //默认给必填条件加值
        requiredColumn.add("name");
        requiredColumn.add("code");
        requiredColumn.add("lisence_validstart");
        requiredColumn.add("lisence_validend");
        requiredColumn.add("lisence_validstatus");
        requiredColumn.add("lisence_start");
        requiredColumn.add("lisence_end");
        requiredColumn.add("lisence_passlines");
        requiredColumn.add("lisence_busnum");
        requiredColumn.add("lisence_lineid");
        requiredColumn.add("lisence_linename");
        requiredColumn.add("lisence_waykm");
        requiredColumn.add("lisence_waytime");
        requiredColumn.add("lisence_managementunit");
        requiredColumn.add("lisence_bustype");
        requiredColumn.add("lisence_businessarea");
        requiredColumn.add("lisence_linetype");
        requiredColumn.add("lisence_businessnature");
        requiredColumn.add("lisence_nature");
        requiredColumn.add("lisence_usestatus");
        requiredColumn.add("lisence_belongsid");

        Map<String, Object> searchMap = new HashMap<>();

        dao.selectRequiredData(requiredColumn, searchMap);
        setSyncTime(RedisCacheKey.LISENCE_REQUIRED_TIME);
    }

    /**
     * 获取线路牌同步时间
     *
     * @param fieldName
     * @return String
     */
    public String getSyncTime(String fieldName) {
        return RedisUtil.getSyncTime(redisTemplate, RedisCacheKey.LISENCE_COMPARE_TIME, fieldName);
    }

    /**
     * 设置线路牌同步时间
     *
     * @param fieldName
     */
    private void setSyncTime(String fieldName) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RedisUtil.setSyncTime(redisTemplate, RedisCacheKey.LISENCE_COMPARE_TIME, fieldName, format.format(new Date()));
    }

    /**
     * 查询全部唯一性校验失败的数据
     *
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<Lisence> selectOnlyValidateByPage(PageRequest pageRequest, SearchParams searchParams) {

        boolean updateOperation = Boolean.parseBoolean(searchParams.getSearchMap().get("updateOperation").toString());

        //查询缓存数据
        Page<Lisence> pageResult;

        if (updateOperation) {
            //从数据库查询全部数据
            //查询数据库数据量
            int result = dao.selectOnlyValidateData();
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if (result > 0) {
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.LISENCE_ONLY_TIME);
                pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LISENCE_ONLY_DATA);
            } else {
                setSyncTime(RedisCacheKey.LISENCE_ONLY_TIME);
                pageResult = new PageImpl<>(new ArrayList<Lisence>(), pageRequest, 0);
            }
        } else {
            //查询缓存数据
            pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LISENCE_ONLY_DATA);
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
                    setSyncTime(RedisCacheKey.LISENCE_ONLY_TIME);
                    pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LISENCE_ONLY_DATA);
                } else {
                    setSyncTime(RedisCacheKey.LISENCE_ONLY_TIME);
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
    public Page<Lisence> selectRequiredData(PageRequest pageRequest, List<String> requiredColumn, SearchParams searchParams) {

        //默认给必填条件加值
        requiredColumn.add("name");
        requiredColumn.add("code");
        requiredColumn.add("lisence_validstart");
        requiredColumn.add("lisence_validend");
        requiredColumn.add("lisence_validstatus");
        requiredColumn.add("lisence_start");
        requiredColumn.add("lisence_end");
        requiredColumn.add("lisence_passlines");
        requiredColumn.add("lisence_busnum");
        requiredColumn.add("lisence_lineid");
        requiredColumn.add("lisence_linename");
        requiredColumn.add("lisence_waykm");
        requiredColumn.add("lisence_waytime");
        requiredColumn.add("lisence_managementunit");
        requiredColumn.add("lisence_bustype");
        requiredColumn.add("lisence_businessarea");
        requiredColumn.add("lisence_linetype");
        requiredColumn.add("lisence_businessnature");
        requiredColumn.add("lisence_nature");
        requiredColumn.add("lisence_usestatus");
        requiredColumn.add("lisence_belongsid");

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

        boolean updateOperation = Boolean.parseBoolean(searchParams.getSearchMap().get("updateOperation").toString());
        Page<Lisence> pageResult;
        if (updateOperation) {
            //从数据库查询全部数据
            //查询数据库数据量
            int result = dao.selectRequiredData(requiredColumn, searchMap);
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if (result > 0) {
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.LISENCE_REQUIRED_TIME);
                pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LINE_REQUIRED_DATA);
            } else {
                setSyncTime(RedisCacheKey.LISENCE_REQUIRED_TIME);
                pageResult = new PageImpl<>(new ArrayList<Lisence>(), pageRequest, 0);
            }
        } else {
            //查询缓存数据
            pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LISENCE_REQUIRED_DATA);
            //判断缓存是否有值
            if ((!pageResult.getContent().isEmpty()) && pageResult.getContent().size() > 0) {
                return pageResult;
            } else {
                //从数据库查询全部数据
                //查询数据库数据量
                int result = dao.selectRequiredData(requiredColumn, searchMap);
                //如果没有数据直接返回空值,如果有数据,从redis里分页取值
                if (result > 0) {
                    //有数据设置同步时间
                    setSyncTime(RedisCacheKey.LISENCE_REQUIRED_TIME);
                    pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.LISENCE_REQUIRED_DATA);
                } else {
                    setSyncTime(RedisCacheKey.LISENCE_REQUIRED_TIME);
                    return pageResult;
                }
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
        int compareLength = redisTemplate.llen(RedisCacheKey.LISENCE_COMPARE_DATA).intValue();

        if (compareLength > 0) {
            List<String> compareList = redisTemplate.lrange(RedisCacheKey.LISENCE_COMPARE_DATA, 0, compareLength);
            resultMap.put("compareData", compareList);
        } else {
            resultMap.put("compareData", new ArrayList<String>());
        }

        //取唯一性校验的数据
        int onlyLength = redisTemplate.llen(RedisCacheKey.LISENCE_ONLY_DATA).intValue();
        if (onlyLength > 0) {
            List<String> onlyData = redisTemplate.lrange(RedisCacheKey.LISENCE_ONLY_DATA, 0, onlyLength);
            resultMap.put("onlyData", onlyData);
        } else {
            resultMap.put("onlyData", new ArrayList<String>());
        }

        //取必填校验的数据
        int requiredLength = redisTemplate.llen(RedisCacheKey.LISENCE_REQUIRED_DATA).intValue();
        if (requiredLength > 0) {
            List<String> requiredData = redisTemplate.lrange(RedisCacheKey.LISENCE_REQUIRED_DATA, 0, requiredLength);
            resultMap.put("requiredData", requiredData);
        } else {
            resultMap.put("requiredData", new ArrayList<String>());
        }

        return resultMap;
    }

}
