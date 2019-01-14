package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.Merchants;
import com.yonyou.iuap.project.entity.Station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: CardTableMetaDao</p>
 * <p>Description: </p>
 */
@Repository
public class MerchantsDao {

    @Qualifier("mdBaseDAO")
    @Autowired
    private MetadataDAO dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MerchantsRepository repository;

    private Gson gson = new Gson();

    public Page<Merchants> selectAllByPage(PageRequest pageRequest) {
        List<Merchants> list = repository.selectAllData();
        Page<Merchants> resultPage = new PageImpl<>(list);
        return resultPage;
    }

    public void batchInsert(List<Merchants> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<Merchants> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<Merchants> list) {
        dao.remove(list);
    }

    /**
     * 分页查询redis缓存数据
     *
     * @param pageRequest
     * @return
     */
    public Page<Merchants> selectAllByCache(PageRequest pageRequest, String dataKey) {

        //分页查询redis
        List<String> resultCache = redisTemplate.lrange(dataKey, pageRequest.getPageNumber() * pageRequest.getPageSize(), (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1);

        //查询缓存中数据的长度
        long resultCacheSize = redisTemplate.llen(dataKey);

        //返回结果
        List<Merchants> resultList = new ArrayList<>();

        //如果有数据,转化数据
        if (resultCache != null && resultCache.size() > 0) {
            for (int i = 0; i < resultCache.size(); i++) {
                resultList.add(i, gson.fromJson(resultCache.get(i), Merchants.class));
            }
        }
        return new PageImpl<>(resultList, pageRequest, resultCacheSize);
    }

    /**
     * 查询唯一性校验失败的数据
     *
     * @return
     */
    public int selectOnlyValidateData() {
        //查询唯一性校验失败的数据
        List<Merchants> resultList = repository.selectOnlyValidateData();
        redisTemplate.del(RedisCacheKey.MERCHANTS_ONLY_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            //向redis放数据
            for (Merchants merchants : resultList) {
                redisTemplate.rpush(RedisCacheKey.MERCHANTS_ONLY_DATA, gson.toJson(merchants));
            }
            return resultList.size();
        } else {
            return 0;
        }
    }

    /**
     * 查询必填项没有数据的数据
     *
     * @return
     */
    public int selectRequiredData(List<String> columns, Map<String, Object> searchParams) {
        searchParams.put("requiredColumns", columns);
        List<Merchants> resultList = repository.selectRequiredData(searchParams);
        redisTemplate.del(RedisCacheKey.MERCHANTS_REQUIRED_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (Merchants merchants : resultList) {
                redisTemplate.rpush(RedisCacheKey.MERCHANTS_REQUIRED_DATA, gson.toJson(merchants));
            }
            return resultList.size();
        }
        return 0;
    }
    
    /**
     * 根据条件分页查询redis数据
     * @param pageRequest
     * @param dataKey
     * @param searchMap
     * @return
     */
    public Page<Merchants> selectCacheByCondition(PageRequest pageRequest,String dataKey,Map<String, Object> searchMap){

        //查询缓存中数据的长度
        Long resultCacheSize = redisTemplate.llen(dataKey);

        //查询所有数据
        List<String> resultAllCache = redisTemplate.lrange(dataKey, 0, resultCacheSize.intValue());

        //返回结果
        List<Merchants> resultList = new ArrayList<>();

        List<Merchants> resultListPage = new ArrayList<>();

        String condition=searchMap.get("searchParam").toString();

        //如果有数据,转化数据
        if (resultAllCache != null && resultAllCache.size() > 0) {
            for (int i = 0; i < resultAllCache.size(); i++) {
            	Merchants merchants = gson.fromJson(resultAllCache.get(i), Merchants.class);
                //模糊筛选
                if(merchants.getName()!=null && merchants.getName().contains(condition)){
                    resultList.add(merchants);
                }
            }
        }

        if(resultList.size()<pageRequest.getPageSize()){
            return new PageImpl<>(resultList, pageRequest, resultList.size());
        }else {
            //取分页数据
            int start = pageRequest.getPageNumber() * pageRequest.getPageSize();
            int end = (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1;

            for (int i = start; i <= end; i++) {
                try {
                    resultListPage.add(resultList.get(i));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return new PageImpl<>(resultListPage, pageRequest, resultList.size());
        }

    }

    /**
     * 根据条件分页查询redis数据
     * @param pageRequest
     * @param dataKey
     * @param searchMap
     * @return
     */
    public Page<Merchants> selectCacheByConditionRequired(PageRequest pageRequest,String dataKey,Map<String, Object> searchMap){

        //查询缓存中数据的长度
        Long resultCacheSize = redisTemplate.llen(dataKey);

        //查询所有数据
        List<String> resultAllCache = redisTemplate.lrange(dataKey, 0, resultCacheSize.intValue());

        //返回结果
        List<Merchants> resultList = new ArrayList<>();

        List<Merchants> resultListPage = new ArrayList<>();

        String condition=searchMap.get("searchParam").toString();

        //如果有数据,转化数据
        if (resultAllCache != null && resultAllCache.size() > 0) {
            for (int i = 0; i < resultAllCache.size(); i++) {
            	Merchants merchants = gson.fromJson(resultAllCache.get(i), Merchants.class);
            	//System.out.println(resultAllCache.get(i));
                //模糊筛选
                if(merchants.getName()!=null && merchants.getName().contains(condition)){
                    resultList.add(merchants);                   
                }
            }
        }

        if(resultList!=null&&resultList.size()>0){
            if(resultList.size()<pageRequest.getPageSize()){
                return new PageImpl<>(resultList, pageRequest, resultList.size());
            }else {
                //取分页数据
                int start = pageRequest.getPageNumber() * pageRequest.getPageSize();
                int end = (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1;

                for (int i = start; i <= end; i++) {
                    try {
                        resultListPage.add(resultList.get(i));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                return new PageImpl<>(resultListPage, pageRequest, resultList.size());
            }
        }else{
            return new PageImpl<>(resultListPage, pageRequest, 0);
        }
    }

}
