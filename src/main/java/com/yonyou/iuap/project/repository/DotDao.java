package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.Dot;
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
 * @Description 网上飞网点
 *
 * @author binbin
 * @date 2018/12/18 15:15
 */
@Repository
public class DotDao {

    @Qualifier("mdBaseDAO")
    @Autowired
    private MetadataDAO dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DotRepository dotRepository;

    private Gson gson = new Gson();

    public Page<Dot> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        List<Dot> list = dotRepository.selectAllData(searchParams);
        Page<Dot> resultPage = new PageImpl<>(list);
        return resultPage;
    }


    public void batchInsert(List<Dot> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<Dot> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<Dot> list) {
        dao.remove(list);
    }

    public Page<Dot> selectAllByCache(PageRequest pageRequest, String dataKey) {
        List<String> resultCache = redisTemplate.lrange(dataKey, pageRequest.getPageNumber() * pageRequest.getPageSize(), (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1);
        long resultCacheSize = redisTemplate.llen(dataKey);
        List<Dot> resultList = new ArrayList<>();

        if (resultCache != null && resultCache.size() > 0) {
            for (int i = 0; i < resultCache.size(); i++) {
                resultList.add(i, gson.fromJson(resultCache.get(i), Dot.class));
            }
        }
        return new PageImpl<>(resultList, pageRequest, resultCacheSize);
    }

    public int selectOnlyValidateData() {
        List<Dot> resultList = dotRepository.selectOnlyValidateData();
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            redisTemplate.del(RedisCacheKey.DOT_ONLY_DATA);
            for (Dot dot : resultList
                    ) {
                redisTemplate.rpush(RedisCacheKey.DOT_ONLY_DATA, gson.toJson(dot));
            }
            return resultList.size();
        } else {
            return 0;
        }
    }

    public int selectRequiredData(List<String> columns) {
        List<Dot> resultList = dotRepository.selectRequiredData(columns);

        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            redisTemplate.del(RedisCacheKey.DOT_REQUIRED_DATA);

            for (Dot dot : resultList) {
                redisTemplate.rpush(RedisCacheKey.DOT_REQUIRED_DATA, gson.toJson(dot));
            }
            return resultList.size();
        }
        return 0;
    }

}
