package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.Settlementmethod;
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
 * @Description 结算方式
 *
 * @author binbin
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

    public Page<Settlementmethod> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        List<Settlementmethod> list = settlementmethodRepository.selectAllData(searchParams);
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
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            redisTemplate.del(RedisCacheKey.STASION_ONLY_DATA);
            for (Settlementmethod settlementmethod : resultList
                    ) {
                redisTemplate.rpush(RedisCacheKey.STASION_ONLY_DATA, gson.toJson(settlementmethod));
            }
            return resultList.size();
        } else {
            return 0;
        }
    }

    public int selectRequiredData(List<String> columns) {
        List<Settlementmethod> resultList = settlementmethodRepository.selectRequiredData(columns);

        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            redisTemplate.del(RedisCacheKey.STASION_REQUIRED_DATA);

            for (Settlementmethod settlementmethod : resultList) {
                redisTemplate.rpush(RedisCacheKey.STASION_REQUIRED_DATA, gson.toJson(settlementmethod));
            }
            return resultList.size();
        }
        return 0;
    }

}
