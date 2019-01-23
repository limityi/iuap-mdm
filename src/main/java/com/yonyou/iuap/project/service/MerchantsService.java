package com.yonyou.iuap.project.service;

import com.google.gson.Gson;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.cache.RedisUtil;
import com.yonyou.iuap.project.dt.DTEnum;
import com.yonyou.iuap.project.entity.Merchants;
import com.yonyou.iuap.project.repository.MerchantsDao;
import com.yonyou.iuap.project.repository.MerchantsRepository;
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
public class MerchantsService {

    @Autowired
    private MerchantsDao dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MerchantsRepository merchantsRepository;

    @Autowired
    private OverviewService overviewService;

    private Gson gson = new Gson();

    /**
     * 查询所有数据
     *
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<Merchants> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
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
        Page<Merchants> pageResult;

        String requestId = UUID.randomUUID().toString();

        boolean updateOperation = Boolean.parseBoolean(searchMap.get("updateOperation").toString());
        if (updateOperation) {
            this.syncCacheData(requestId);
            //比较完之后再从缓存取值
            pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.MERCHANTS_COMPARE_DATA);
        } else {
            if (searchMap.get("searchParam").equals("null")) {
                //查询缓存数据
                pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.MERCHANTS_COMPARE_DATA);

                //判断缓存是否有值
                if ((!pageResult.getContent().isEmpty()) && pageResult.getContent().size() > 0) {
                    return pageResult;
                } else {
                    this.syncCacheData(requestId);
                    //比较完之后再从缓存取值
                    pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.MERCHANTS_COMPARE_DATA);
                }
            } else {
                pageResult = dao.selectCacheByCondition(pageRequest, RedisCacheKey.MERCHANTS_COMPARE_DATA, searchMap);
            }
        }
        return pageResult;
    }

    public void save(List<Merchants> recordList) {
        List<Merchants> addList = new ArrayList<>(recordList.size());
        List<Merchants> updateList = new ArrayList<>(recordList.size());
        for (Merchants meta : recordList) {
            if (meta.getId() == null) {
                meta.setId(UUID.randomUUID().toString());
                meta.setDr("0");
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

    public void batchDeleteByPrimaryKey(List<Merchants> list) {
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
     * 车辆相似度比较，并将数据存储在redis
     *
     * @param pageResult
     * @param searchMap
     */
    private void similarityMatch(Page<Merchants> pageResult) {
        Map<String, Object> searchMap = new HashMap<>();
        //匹配之前，先删除redis数据
        //redisTemplate.del(RedisCacheKey.MERCHANTS_COMPARE_DATA);
        //处理数据，相似度检查
        //根据查询的参数，看是哪个字段需要检查相似度
        //循环的list
        List<Merchants> merchantsList = pageResult.getContent();
        //匹配的list
        CopyOnWriteArrayList<Merchants> merchantsMatch = new CopyOnWriteArrayList<>(pageResult.getContent());
        //结果的list
        List<Merchants> merchantsResult = new ArrayList<>();

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
            for (Merchants merchants : merchantsList) {

                //放值的标志
                boolean tagMerchants = true;

                try {

                    //判断结果集是否已经包含
                    if (!contain(merchantsResult, merchants)) {
                        //反射取字段值
                        Method method = merchants.getClass().getMethod(matchField, null);
                        String field1 = (String) method.invoke(merchants);

                        //循环去匹配
                        for (Merchants mer : merchantsMatch) {
                            //如果不是同一条数据则进行匹配
                            if (!merchants.getMdm_code().equals(mer.getMdm_code())) {
                                String field2 = (String) mer.getClass().getMethod(matchField, null).invoke(mer);
                                //匹配结果
                                double similarity = SimilarityMatch.getSimilarity(field1, field2);
                                //大于80%
                                if (similarity > 0.9) {
                                    //如果第一次匹配成功,将源数据也放入结果集
                                    if (tagMerchants) {
                                        merchants.setTag(String.valueOf(tag));
                                        merchantsResult.add(merchants);
                                        //同时将数据写入redis
                                        redisTemplate.rpush(RedisCacheKey.MERCHANTS_COMPARE_DATA, gson.toJson(merchants));
                                        tagMerchants = false;
                                    }

                                    //将相似度设置
                                    mer.setSimilarity(dobleFormat(similarity));
                                    mer.setTag(String.valueOf(tag));
                                    merchantsResult.add(mer);
                                    //同时将数据写入redis
                                    redisTemplate.rpush(RedisCacheKey.MERCHANTS_COMPARE_DATA, gson.toJson(mer));
                                    merchantsMatch.remove(mer);
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
        long resultCacheSize = redisTemplate.llen(RedisCacheKey.MERCHANTS_COMPARE_DATA);
        overviewService.updateMdmDataStatistics(DTEnum.MdmSys.MDM.getId(), DTEnum.UserMenus.merchants.getId().split("md_")[1].toUpperCase(), DTEnum.UserMenus.merchants.getDtName(), 2, resultCacheSize);
    }

    /**
     * 判断结果集是否包含比较的数据
     * 包含返回 true
     *
     * @param list
     * @param merchants
     * @return
     */
    private boolean contain(List<Merchants> list, Merchants merchants) {
        boolean result = false;
        if (list != null && list.size() > 0 && merchants != null) {
            for (Merchants mer : list) {
                //比较id
                if (mer.getId().equals(merchants.getId())) {
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
    public void merchantsJob() {
        String requestId = UUID.randomUUID().toString();
        this.syncCacheData(requestId);
    }

    public void merchantsOnlyJob() {
        dao.selectOnlyValidateData();
        setSyncTime(RedisCacheKey.MERCHANTS_ONLY_TIME);
    }

    public void merchantsRequiredJob() {
        List<String> requiredColumn = new ArrayList<>();

        //默认给必填条件加值
        requiredColumn.add("name");
        requiredColumn.add("code");

        Map<String, Object> searchMap = new HashMap<>();

        dao.selectRequiredData(requiredColumn, searchMap);
        setSyncTime(RedisCacheKey.MERCHANTS_REQUIRED_TIME);
    }

    /**
     * 获取车辆同步时间
     *
     * @param fieldName
     * @return String
     */
    public String getSyncTime(String fieldName) {
        return RedisUtil.getSyncTime(redisTemplate, RedisCacheKey.MERCHANTS_COMPARE_TIME, fieldName);
    }

    /**
     * 设置车辆同步时间
     *
     * @param fieldName
     */
    private void setSyncTime(String fieldName) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RedisUtil.setSyncTime(redisTemplate, RedisCacheKey.MERCHANTS_COMPARE_TIME, fieldName, format.format(new Date()));
    }

    /**
     * 查询全部唯一性校验失败的数据
     *
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<Merchants> selectOnlyValidateByPage(PageRequest pageRequest, SearchParams searchParams) {

        boolean updateOperation = Boolean.parseBoolean(searchParams.getSearchMap().get("updateOperation").toString());

        //查询缓存数据
        Page<Merchants> pageResult;

        if (updateOperation) {
            //从数据库查询全部数据
            //查询数据库数据量
            int result = dao.selectOnlyValidateData();
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if (result > 0) {
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.MERCHANTS_ONLY_TIME);
                pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.MERCHANTS_ONLY_DATA);
            } else {
                setSyncTime(RedisCacheKey.MERCHANTS_ONLY_TIME);
                pageResult = new PageImpl<>(new ArrayList<Merchants>(), pageRequest, 0);
            }
        } else {
            //查询缓存数据
            pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.MERCHANTS_ONLY_DATA);
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
                    setSyncTime(RedisCacheKey.MERCHANTS_ONLY_TIME);
                    pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.MERCHANTS_ONLY_DATA);
                } else {
                    setSyncTime(RedisCacheKey.MERCHANTS_ONLY_TIME);
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
    public Page<Merchants> selectRequiredData(PageRequest pageRequest, List<String> requiredColumn, SearchParams searchParams) {

        //默认给必填条件加值
        requiredColumn.add("name");
        requiredColumn.add("code");
        requiredColumn.add("cust_supplier_shortname");
        requiredColumn.add("custsupplier_category");
        requiredColumn.add("custsuptype");
        requiredColumn.add("taxpayerid");
        requiredColumn.add("country");
        requiredColumn.add("province");
        requiredColumn.add("city");
        requiredColumn.add("county_area");
        requiredColumn.add("postalcode");
        requiredColumn.add("address");

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

        String requestId = UUID.randomUUID().toString();

        boolean updateOperation = Boolean.parseBoolean(searchParams.getSearchMap().get("updateOperation").toString());
        Page<Merchants> pageResult = null;
        if (updateOperation) {
            boolean lock = RedisUtil.tryGetDistributedLock(redisTemplate, RedisCacheKey.MERCHANTS_REQUIRED_DATA, requestId, RedisUtil.getLock_timeout());
            if (lock) {
                redisTemplate.del(RedisCacheKey.MERCHANTS_REQUIRED_DATA);
                //从数据库查询全部数据
                //查询数据库数据量
                int result = dao.selectRequiredData(requiredColumn, searchMap);
                //如果没有数据直接返回空值,如果有数据,从redis里分页取值
                if (result > 0) {
                    //有数据设置同步时间
                    setSyncTime(RedisCacheKey.MERCHANTS_REQUIRED_TIME);
                    //释放分布式锁
                    RedisUtil.releaseDistributedLock(redisTemplate, RedisCacheKey.MERCHANTS_REQUIRED_DATA, requestId);
                    pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.MERCHANTS_REQUIRED_DATA);
                } else {
                    setSyncTime(RedisCacheKey.MERCHANTS_REQUIRED_TIME);
                    //释放分布式锁
                    RedisUtil.releaseDistributedLock(redisTemplate, RedisCacheKey.MERCHANTS_REQUIRED_DATA, requestId);
                    pageResult = new PageImpl<>(new ArrayList<Merchants>(), pageRequest, 0);
                }
            }
        } else {
            if (searchMap.get("searchParam").equals("null")) {
                //查询缓存数据
                pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.MERCHANTS_REQUIRED_DATA);
                //判断缓存是否有值
                if ((!pageResult.getContent().isEmpty()) && pageResult.getContent().size() > 0) {
                    return pageResult;
                } else {
                    //释放分布式锁
                    boolean lock = RedisUtil.releaseDistributedLock(redisTemplate, RedisCacheKey.MERCHANTS_REQUIRED_DATA, requestId);
                    if (lock) {
                        redisTemplate.del(RedisCacheKey.MERCHANTS_REQUIRED_DATA);
                        //从数据库查询全部数据
                        //查询数据库数据量
                        int result = dao.selectRequiredData(requiredColumn, searchMap);
                        //如果没有数据直接返回空值,如果有数据,从redis里分页取值
                        if (result > 0) {
                            //有数据设置同步时间
                            setSyncTime(RedisCacheKey.MERCHANTS_REQUIRED_TIME);
                            RedisUtil.releaseDistributedLock(redisTemplate, RedisCacheKey.MERCHANTS_REQUIRED_DATA, requestId);
                            pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.MERCHANTS_REQUIRED_DATA);
                        } else {
                            setSyncTime(RedisCacheKey.MERCHANTS_REQUIRED_TIME);
                            RedisUtil.releaseDistributedLock(redisTemplate, RedisCacheKey.MERCHANTS_REQUIRED_DATA, requestId);
                            pageResult = new PageImpl<>(new ArrayList<Merchants>(), pageRequest, 0);
                        }
                    }
                }
            } else {
                pageResult = dao.selectCacheByConditionRequired(pageRequest, RedisCacheKey.MERCHANTS_COMPARE_DATA, searchMap);
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
        int compareLength = redisTemplate.llen(RedisCacheKey.MERCHANTS_COMPARE_DATA).intValue();

        if (compareLength > 0) {
            List<String> compareList = redisTemplate.lrange(RedisCacheKey.MERCHANTS_COMPARE_DATA, 0, compareLength);
            resultMap.put("compareData", compareList);
        } else {
            resultMap.put("compareData", new ArrayList<String>());
        }

        //取唯一性校验的数据
        int onlyLength = redisTemplate.llen(RedisCacheKey.MERCHANTS_ONLY_DATA).intValue();
        if (onlyLength > 0) {
            List<String> onlyData = redisTemplate.lrange(RedisCacheKey.MERCHANTS_ONLY_DATA, 0, onlyLength);
            resultMap.put("onlyData", onlyData);
        } else {
            resultMap.put("onlyData", new ArrayList<String>());
        }

        //取必填校验的数据
        int requiredLength = redisTemplate.llen(RedisCacheKey.MERCHANTS_REQUIRED_DATA).intValue();
        if (requiredLength > 0) {
            List<String> requiredData = redisTemplate.lrange(RedisCacheKey.MERCHANTS_REQUIRED_DATA, 0, requiredLength);
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
        int result = merchantsRepository.removeData(code);

        if (result > 0) {

            Map<String, Object> searchMap = new HashMap<>();
            String requestId = UUID.randomUUID().toString();
            this.syncCacheData(requestId);
        }
    }

    /**
     * 缓存处理(相似度)
     *
     * @param requestId
     */
    private void syncCacheData(String requestId) {

        boolean lock = RedisUtil.tryGetDistributedLock(redisTemplate, RedisCacheKey.MERCHANTS_COMPARE_DATA, requestId, RedisUtil.getLock_timeout());

        if (lock) {
            //匹配之前，先删除redis数据
            redisTemplate.del(RedisCacheKey.MERCHANTS_COMPARE_DATA);
            //从数据库查询全部数据
            //查询数据库数据量
            int size = merchantsRepository.countAll();
            //定义新的分页数据,用来查询全部
            PageRequest pageRequestTemp = new PageRequest(0, size);
            //查询全部结果
            Page<Merchants> pageResult = dao.selectAllByPage(pageRequestTemp);
            //相似度比较
            similarityMatch(pageResult);
            //比较完之后更新对比同步时间
            setSyncTime(RedisCacheKey.MERCHANTS_COMPARE_TIME);

            //释放分布式锁
            RedisUtil.releaseDistributedLock(redisTemplate, RedisCacheKey.MERCHANTS_COMPARE_DATA, requestId);
        }

    }

}
