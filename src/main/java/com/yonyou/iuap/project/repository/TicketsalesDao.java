package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.Ticketsales;
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
 * @Description 客票代售网点
 *
 * @author binbin
 * @date 2018/12/18 15:28
 */
@Repository
public class TicketsalesDao {

    @Qualifier("mdBaseDAO")
    @Autowired
    private MetadataDAO dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TicketsalesRepository ticketsalesRepository;

    private Gson gson = new Gson();

    public Page<Ticketsales> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        List<Ticketsales> list = ticketsalesRepository.selectAllData(searchParams);
        Page<Ticketsales> resultPage = new PageImpl<>(list);
        return resultPage;
    }


    public void batchInsert(List<Ticketsales> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<Ticketsales> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<Ticketsales> list) {
        dao.remove(list);
    }

    public Page<Ticketsales> selectAllByCache(PageRequest pageRequest, String dataKey) {
        List<String> resultCache = redisTemplate.lrange(dataKey, pageRequest.getPageNumber() * pageRequest.getPageSize(), (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1);
        long resultCacheSize = redisTemplate.llen(dataKey);
        List<Ticketsales> resultList = new ArrayList<>();

        if (resultCache != null && resultCache.size() > 0) {
            for (int i = 0; i < resultCache.size(); i++) {
                resultList.add(i, gson.fromJson(resultCache.get(i), Ticketsales.class));
            }
        }
        return new PageImpl<>(resultList, pageRequest, resultCacheSize);
    }

    public int selectOnlyValidateData() {
        List<Ticketsales> resultList = ticketsalesRepository.selectOnlyValidateData();
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            redisTemplate.del(RedisCacheKey.TICKETSALES_ONLY_DATA);
            for (Ticketsales ticketsales : resultList
                    ) {
                redisTemplate.rpush(RedisCacheKey.TICKETSALES_ONLY_DATA, gson.toJson(ticketsales));
            }
            return resultList.size();
        } else {
            return 0;
        }
    }

    public int selectRequiredData(List<String> columns) {
        List<Ticketsales> resultList = ticketsalesRepository.selectRequiredData(columns);

        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            redisTemplate.del(RedisCacheKey.TICKETSALES_REQUIRED_DATA);

            for (Ticketsales ticketsales : resultList) {
                redisTemplate.rpush(RedisCacheKey.TICKETSALES_REQUIRED_DATA, gson.toJson(ticketsales));
            }
            return resultList.size();
        }
        return 0;
    }

}
