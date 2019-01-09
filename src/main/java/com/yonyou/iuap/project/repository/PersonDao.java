package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.Person;
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
public class PersonDao {

    @Qualifier("mdBaseDAO")
    @Autowired
    private MetadataDAO dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PersonRepository repository;

    private Gson gson = new Gson();

    public Page<Person> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {

        List<Person> list = repository.selectAllData(searchParams);
        Page<Person> resultPage = new PageImpl<>(list);
        return resultPage;
    }

    public void batchInsert(List<Person> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<Person> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<Person> list) {
        dao.remove(list);
    }

    /**
     * 分页查询redis缓存数据
     *
     * @param pageRequest
     * @return
     */
    public Page<Person> selectAllByCache(PageRequest pageRequest, String dataKey) {

        //分页查询redis
        List<String> resultCache = redisTemplate.lrange(dataKey, pageRequest.getPageNumber() * pageRequest.getPageSize(), (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1);

        //查询缓存中数据的长度
        long resultCacheSize = redisTemplate.llen(dataKey);

        //返回结果
        List<Person> resultList = new ArrayList<>();

        //如果有数据,转化数据
        if (resultCache != null && resultCache.size() > 0) {
            for (int i = 0; i < resultCache.size(); i++) {
                resultList.add(i, gson.fromJson(resultCache.get(i), Person.class));
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
        List<Person> resultList = repository.selectOnlyValidateData();
        redisTemplate.del(RedisCacheKey.PERSON_ONLY_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (Person person : resultList) {
                redisTemplate.rpush(RedisCacheKey.PERSON_ONLY_DATA, gson.toJson(person));
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
        List<Person> resultList = repository.selectRequiredData(searchParams);
        redisTemplate.del(RedisCacheKey.PERSON_REQUIRED_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (Person person : resultList) {
                redisTemplate.rpush(RedisCacheKey.PERSON_REQUIRED_DATA, gson.toJson(person));
            }
            return resultList.size();
        }
        return 0;
    }

}
