package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.Org;
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
 * @Description 客商Dao
 * @date 2018/12/17 16:06
 */
@Repository
public class OrgDao {

    @Qualifier("mdBaseDAO")
    @Autowired
    private MetadataDAO dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrgRepository orgRepository;

    private Gson gson = new Gson();


    public Page<Org> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        List<Org> list = orgRepository.selectAllData(searchParams);
        Page<Org> resultPage = new PageImpl<>(list);
        return resultPage;
    }


    public void batchInsert(List<Org> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<Org> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<Org> list) {
        dao.remove(list);
    }

    public Page<Org> selectAllByCache(PageRequest pageRequest, String dataKey) {
        List<String> resultCache = redisTemplate.lrange(dataKey, pageRequest.getPageNumber() * pageRequest.getPageSize(), (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1);
        long resultCacheSize = redisTemplate.llen(dataKey);
        List<Org> resultList = new ArrayList<>();

        if (resultCache != null && resultCache.size() > 0) {
            for (int i = 0; i < resultCache.size(); i++) {
                Org org = gson.fromJson(resultCache.get(i), Org.class);

                String mdmcode = org.getFatherorg_name();
                Org pidOrg = orgRepository.getOrgByMdmCode(mdmcode);
                if (pidOrg != null) {
                    org.setFatherorg_name(pidOrg.getName());
                }

                resultList.add(i, org);
            }
        }
        return new PageImpl<>(resultList, pageRequest, resultCacheSize);
    }

    public int selectOnlyValidateData() {
        List<Org> resultList = orgRepository.selectOnlyValidateData();
        redisTemplate.del(RedisCacheKey.ORG_ONLY_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (Org org : resultList) {
                redisTemplate.rpush(RedisCacheKey.ORG_ONLY_DATA, gson.toJson(org));
            }
            return resultList.size();
        } else {
            return 0;
        }
    }

    public int selectRequiredData(List<String> columns, Map<String, Object> searchParams) {
        searchParams.put("requiredColumns", columns);
        List<Org> resultList = orgRepository.selectRequiredData(searchParams);
        redisTemplate.del(RedisCacheKey.ORG_REQUIRED_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (Org org : resultList) {
                redisTemplate.rpush(RedisCacheKey.ORG_REQUIRED_DATA, gson.toJson(org));
            }
            return resultList.size();
        }
        return 0;
    }

}
