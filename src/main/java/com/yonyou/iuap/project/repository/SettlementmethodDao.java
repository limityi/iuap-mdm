package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.Settlementmethod;
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
 * @author binbin
 * @Description 结算方式
 * @date 2018/12/18 15:25
 */
@Repository
public class SettlementmethodDao {

    @Qualifier("mdBaseDAO")
    @Autowired
    private MetadataDAO dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SettlementmethodRepository settlementmethodRepository;

    private Gson gson = new Gson();

    public Page<Settlementmethod> selectAllByPage(PageRequest pageRequest) {
        List<Settlementmethod> list = settlementmethodRepository.selectAllData();
        Page<Settlementmethod> resultPage = new PageImpl<>(list);
        return resultPage;
    }


    public void batchInsert(List<Settlementmethod> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<Settlementmethod> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<Settlementmethod> list) {
        dao.remove(list);
    }

    public Page<Settlementmethod> selectAllByCache(PageRequest pageRequest, String dataKey) {
        List<String> resultCache = redisTemplate.lrange(dataKey, pageRequest.getPageNumber() * pageRequest.getPageSize(), (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1);
        long resultCacheSize = redisTemplate.llen(dataKey);
        List<Settlementmethod> resultList = new ArrayList<>();

        if (resultCache != null && resultCache.size() > 0) {
            for (int i = 0; i < resultCache.size(); i++) {
                resultList.add(i, gson.fromJson(resultCache.get(i), Settlementmethod.class));
            }
        }
        return new PageImpl<>(resultList, pageRequest, resultCacheSize);
    }

    public int selectOnlyValidateData() {
        List<Settlementmethod> resultList = settlementmethodRepository.selectOnlyValidateData();
        redisTemplate.del(RedisCacheKey.SETTLEMENTMETHOD_ONLY_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (Settlementmethod settlementmethod : resultList) {
                redisTemplate.rpush(RedisCacheKey.SETTLEMENTMETHOD_ONLY_DATA, gson.toJson(settlementmethod));
            }
            return resultList.size();
        } else {
            return 0;
        }
    }

    public int selectRequiredData(List<String> columns, Map<String, Object> searchParams) {
        searchParams.put("requiredColumns", columns);
        List<Settlementmethod> resultList = settlementmethodRepository.selectRequiredData(searchParams);
        redisTemplate.del(RedisCacheKey.SETTLEMENTMETHOD_REQUIRED_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (Settlementmethod settlementmethod : resultList) {
                redisTemplate.rpush(RedisCacheKey.SETTLEMENTMETHOD_REQUIRED_DATA, gson.toJson(settlementmethod));
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
    public Page<Settlementmethod> selectCacheByConditionRequired(PageRequest pageRequest,String dataKey,Map<String, Object> searchMap){

        //查询缓存中数据的长度
        Long resultCacheSize = redisTemplate.llen(dataKey);

        //查询所有数据
        List<String> resultAllCache = redisTemplate.lrange(dataKey, 0, resultCacheSize.intValue());

        //返回结果
        List<Settlementmethod> resultList = new ArrayList<>();

        List<Settlementmethod> resultListPage = new ArrayList<>();

        String condition=searchMap.get("searchParam").toString();

        //如果有数据,转化数据
        if (resultAllCache != null && resultAllCache.size() > 0) {
            for (int i = 0; i < resultAllCache.size(); i++) {
            	Settlementmethod settlementmethod = gson.fromJson(resultAllCache.get(i), Settlementmethod.class);
                //模糊筛选
                if(settlementmethod.getName()!=null && settlementmethod.getName().contains(condition)){
                    resultList.add(settlementmethod);
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
