package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.Costitem;
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
 * @Description 费用项目
 * @date 2018/12/18 15:06
 */
@Repository
public class CostitemDao {

    @Qualifier("mdBaseDAO")
    @Autowired
    private MetadataDAO dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CostitemRepository costitemRepository;

    private Gson gson = new Gson();

    public Page<Costitem> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        List<Costitem> list = costitemRepository.selectAllData(searchParams);
        Page<Costitem> resultPage = new PageImpl<>(list);
        return resultPage;
    }


    public void batchInsert(List<Costitem> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<Costitem> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<Costitem> list) {
        dao.remove(list);
    }

    public Page<Costitem> selectAllByCache(PageRequest pageRequest, String dataKey) {
        List<String> resultCache = redisTemplate.lrange(dataKey, pageRequest.getPageNumber() * pageRequest.getPageSize(), (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1);
        long resultCacheSize = redisTemplate.llen(dataKey);
        List<Costitem> resultList = new ArrayList<>();

        if (resultCache != null && resultCache.size() > 0) {
            for (int i = 0; i < resultCache.size(); i++) {
                resultList.add(i, gson.fromJson(resultCache.get(i), Costitem.class));
            }
        }
        return new PageImpl<>(resultList, pageRequest, resultCacheSize);
    }

    public int selectOnlyValidateData() {
        List<Costitem> resultList = costitemRepository.selectOnlyValidateData();
        redisTemplate.del(RedisCacheKey.COSTITEM_ONLY_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (Costitem costitem : resultList) {
                redisTemplate.rpush(RedisCacheKey.COSTITEM_ONLY_DATA, gson.toJson(costitem));
            }
            return resultList.size();
        } else {
            return 0;
        }
    }

    public int selectRequiredData(List<String> columns) {
        List<Costitem> resultList = costitemRepository.selectRequiredData(columns);
        redisTemplate.del(RedisCacheKey.COSTITEM_REQUIRED_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (Costitem costitem : resultList) {
                redisTemplate.rpush(RedisCacheKey.COSTITEM_REQUIRED_DATA, gson.toJson(costitem));
            }
            return resultList.size();
        }
        return 0;
    }

}
