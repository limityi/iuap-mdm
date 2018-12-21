package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.ServiceArea;
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
 * @Description 服务区
 *
 * @author binbin
 * @date 2018/12/18 15:23
 */
@Repository
public class ServiceAreaDao {

    @Qualifier("mdBaseDAO")
    @Autowired
    private MetadataDAO dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ServiceAreaRepository serviceAreaRepository;

    private Gson gson = new Gson();

    public Page<ServiceArea> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        List<ServiceArea> list = serviceAreaRepository.selectAllData(searchParams);
        Page<ServiceArea> resultPage = new PageImpl<>(list);
        return resultPage;
    }


    public void batchInsert(List<ServiceArea> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<ServiceArea> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<ServiceArea> list) {
        dao.remove(list);
    }

    public Page<ServiceArea> selectAllByCache(PageRequest pageRequest, String dataKey) {
        List<String> resultCache = redisTemplate.lrange(dataKey, pageRequest.getPageNumber() * pageRequest.getPageSize(), (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1);
        long resultCacheSize = redisTemplate.llen(dataKey);
        List<ServiceArea> resultList = new ArrayList<>();

        if (resultCache != null && resultCache.size() > 0) {
            for (int i = 0; i < resultCache.size(); i++) {
                resultList.add(i, gson.fromJson(resultCache.get(i), ServiceArea.class));
            }
        }
        return new PageImpl<>(resultList, pageRequest, resultCacheSize);
    }

    public int selectOnlyValidateData() {
        List<ServiceArea> resultList = serviceAreaRepository.selectOnlyValidateData();
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            redisTemplate.del(RedisCacheKey.STASION_ONLY_DATA);
            for (ServiceArea serviceArea : resultList
                    ) {
                redisTemplate.rpush(RedisCacheKey.STASION_ONLY_DATA, gson.toJson(serviceArea));
            }
            return resultList.size();
        } else {
            return 0;
        }
    }

    public int selectRequiredData(List<String> columns) {
        List<ServiceArea> resultList = serviceAreaRepository.selectRequiredData(columns);

        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            redisTemplate.del(RedisCacheKey.STASION_REQUIRED_DATA);

            for (ServiceArea serviceArea : resultList) {
                redisTemplate.rpush(RedisCacheKey.STASION_REQUIRED_DATA, gson.toJson(serviceArea));
            }
            return resultList.size();
        }
        return 0;
    }

}
