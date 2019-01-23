package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.dt.DTEnum;
import com.yonyou.iuap.project.entity.Org;
import com.yonyou.iuap.project.service.OverviewService;
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

    @Autowired
    private OverviewService overviewService;

    private Gson gson = new Gson();


    public Page<Org> selectAllByPage(PageRequest pageRequest) {
        List<Org> list = orgRepository.selectAllData();
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
        int i = 0;
        List<Org> resultList = orgRepository.selectOnlyValidateData();
        redisTemplate.del(RedisCacheKey.ORG_ONLY_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (Org org : resultList) {
                redisTemplate.rpush(RedisCacheKey.ORG_ONLY_DATA, gson.toJson(org));
            }
            i = resultList.size();
        }
        overviewService.updateMdmDataStatistics(DTEnum.MdmSys.MDM.getId(),DTEnum.UserMenus.org.getId().split("md_")[1].toUpperCase(), DTEnum.UserMenus.org.getDtName(), 1, (long) i);
        return i;
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

    /**
     * 根据条件分页查询redis数据
     *
     * @param pageRequest
     * @param dataKey
     * @param searchMap
     * @return
     */
    public Page<Org> selectCacheByCondition(PageRequest pageRequest, String dataKey, Map<String, Object> searchMap) {

        //查询缓存中数据的长度
        Long resultCacheSize = redisTemplate.llen(dataKey);

        //查询所有数据
        List<String> resultAllCache = redisTemplate.lrange(dataKey, 0, resultCacheSize.intValue());

        //返回结果
        List<Org> resultList = new ArrayList<>();

        List<Org> resultListPage = new ArrayList<>();

        String condition = searchMap.get("searchParam").toString();

        //如果有数据,转化数据
        if (resultAllCache != null && resultAllCache.size() > 0) {
            for (int i = 0; i < resultAllCache.size(); i++) {
                Org org = gson.fromJson(resultAllCache.get(i), Org.class);
                //模糊筛选
                if ((org.getName() != null && org.getName().contains(condition)) || (org.getFatherorg_name() != null && org.getFatherorg_name().contains(condition))) {
                    resultList.add(org);
                }
            }
        }

        if (resultList.size() < pageRequest.getPageSize()) {
            return new PageImpl<>(resultList, pageRequest, resultList.size());
        } else {
            //取分页数据
            int start = pageRequest.getPageNumber() * pageRequest.getPageSize();
            int end = (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1;

            for (int i = start; i <= end; i++) {
                try {
                    resultListPage.add(resultList.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return new PageImpl<>(resultListPage, pageRequest, resultList.size());
        }

    }

    /**
     * 根据条件分页查询redis数据
     *
     * @param pageRequest
     * @param dataKey
     * @param searchMap
     * @return
     */
    public Page<Org> selectCacheByConditionRequired(PageRequest pageRequest, String dataKey, Map<String, Object> searchMap) {

        //查询缓存中数据的长度
        Long resultCacheSize = redisTemplate.llen(dataKey);

        //查询所有数据
        List<String> resultAllCache = redisTemplate.lrange(dataKey, 0, resultCacheSize.intValue());

        //返回结果
        List<Org> resultList = new ArrayList<>();

        List<Org> resultListPage = new ArrayList<>();

        String condition = searchMap.get("searchParam").toString();

        //如果有数据,转化数据
        if (resultAllCache != null && resultAllCache.size() > 0) {
            for (int i = 0; i < resultAllCache.size(); i++) {
                Org org = gson.fromJson(resultAllCache.get(i), Org.class);
                //模糊筛选
                if ((org.getName() != null && org.getName().contains(condition)) || (org.getFatherorg_name() != null && org.getFatherorg_name().contains(condition))) {
                    resultList.add(org);
                }
            }
        }

        if (resultList != null && resultList.size() > 0) {
            if (resultList.size() < pageRequest.getPageSize()) {
                return new PageImpl<>(resultList, pageRequest, resultList.size());
            } else {
                //取分页数据
                int start = pageRequest.getPageNumber() * pageRequest.getPageSize();
                int end = (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1;

                for (int i = start; i <= end; i++) {
                    try {
                        resultListPage.add(resultList.get(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return new PageImpl<>(resultListPage, pageRequest, resultList.size());
            }
        } else {
            return new PageImpl<>(resultListPage, pageRequest, 0);
        }
    }

}
