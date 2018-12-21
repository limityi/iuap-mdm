package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.Stores;
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
 * @Description 便利店
 *
 * @author binbin
 * @date 2018/12/18 15:12
 */
@Repository
public class StoresDao {

    @Qualifier("mdBaseDAO")
    @Autowired
    private MetadataDAO dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StoresRepository storesRepository;

    private Gson gson = new Gson();

    public Page<Stores> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        List<Stores> list = storesRepository.selectAllData(searchParams);
        Page<Stores> resultPage = new PageImpl<>(list);
        return resultPage;
    }


    public void batchInsert(List<Stores> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<Stores> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<Stores> list) {
        dao.remove(list);
    }

    public Page<Stores> selectAllByCache(PageRequest pageRequest, String dataKey) {
        List<String> resultCache = redisTemplate.lrange(dataKey, pageRequest.getPageNumber() * pageRequest.getPageSize(), (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1);
        long resultCacheSize = redisTemplate.llen(dataKey);
        List<Stores> resultList = new ArrayList<>();

        if (resultCache != null && resultCache.size() > 0) {
            for (int i = 0; i < resultCache.size(); i++) {
                resultList.add(i, gson.fromJson(resultCache.get(i), Stores.class));
            }
        }
        return new PageImpl<>(resultList, pageRequest, resultCacheSize);
    }

    public int selectOnlyValidateData() {
        List<Stores> resultList = storesRepository.selectOnlyValidateData();
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            redisTemplate.del(RedisCacheKey.STORES_ONLY_DATA);
            for (Stores stores : resultList
                    ) {
                redisTemplate.rpush(RedisCacheKey.STORES_ONLY_DATA, gson.toJson(stores));
            }
            return resultList.size();
        } else {
            return 0;
        }
    }

    public int selectRequiredData(List<String> columns) {
        List<Stores> resultList = storesRepository.selectRequiredData(columns);

        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            redisTemplate.del(RedisCacheKey.STORES_REQUIRED_DATA);

            for (Stores stores : resultList) {
                redisTemplate.rpush(RedisCacheKey.STORES_REQUIRED_DATA, gson.toJson(stores));
            }
            return resultList.size();
        }
        return 0;
    }

}
