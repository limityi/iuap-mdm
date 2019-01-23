package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.dt.DTEnum;
import com.yonyou.iuap.project.entity.Costitem;
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

    @Autowired
    private OverviewService overviewService;

    private Gson gson = new Gson();

    public Page<Costitem> selectAllByPage(PageRequest pageRequest) {
        List<Costitem> list = costitemRepository.selectAllData();
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
        int i = 0;
        List<Costitem> resultList = costitemRepository.selectOnlyValidateData();
        redisTemplate.del(RedisCacheKey.COSTITEM_ONLY_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (Costitem costitem : resultList) {
                redisTemplate.rpush(RedisCacheKey.COSTITEM_ONLY_DATA, gson.toJson(costitem));
            }
            i = resultList.size();
        }
        overviewService.updateMdmDataStatistics(DTEnum.MdmSys.MDM.getId(),DTEnum.UserMenus.costitem.getId().split("md_")[1].toUpperCase(), DTEnum.UserMenus.costitem.getDtName(), 1, (long) i);
        return i;
    }

    public int selectRequiredData(List<String> columns, Map<String, Object> searchParams) {
        searchParams.put("requiredColumns", columns);
        List<Costitem> resultList = costitemRepository.selectRequiredData(searchParams);
        redisTemplate.del(RedisCacheKey.COSTITEM_REQUIRED_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (Costitem costitem : resultList) {
                redisTemplate.rpush(RedisCacheKey.COSTITEM_REQUIRED_DATA, gson.toJson(costitem));
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
    public Page<Costitem> selectCacheByCondition(PageRequest pageRequest, String dataKey, Map<String, Object> searchMap) {

        //查询缓存中数据的长度
        Long resultCacheSize = redisTemplate.llen(dataKey);

        //查询所有数据
        List<String> resultAllCache = redisTemplate.lrange(dataKey, 0, resultCacheSize.intValue());

        //返回结果
        List<Costitem> resultList = new ArrayList<>();

        List<Costitem> resultListPage = new ArrayList<>();

        String condition = searchMap.get("searchParam").toString();

        //如果有数据,转化数据
        if (resultAllCache != null && resultAllCache.size() > 0) {
            for (int i = 0; i < resultAllCache.size(); i++) {
                Costitem costitem = gson.fromJson(resultAllCache.get(i), Costitem.class);
                //模糊筛选
                if ((costitem.getName() != null && costitem.getName().contains(condition)) ||
                        (costitem.getPk_group() != null && costitem.getPk_group().contains(condition))) {
                    resultList.add(costitem);
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
    public Page<Costitem> selectCacheByConditionRequired(PageRequest pageRequest, String dataKey, Map<String, Object> searchMap) {

        //查询缓存中数据的长度
        Long resultCacheSize = redisTemplate.llen(dataKey);

        //查询所有数据
        List<String> resultAllCache = redisTemplate.lrange(dataKey, 0, resultCacheSize.intValue());

        //返回结果
        List<Costitem> resultList = new ArrayList<>();

        List<Costitem> resultListPage = new ArrayList<>();

        String condition = searchMap.get("searchParam").toString();

        //如果有数据,转化数据
        if (resultAllCache != null && resultAllCache.size() > 0) {
            for (int i = 0; i < resultAllCache.size(); i++) {
                Costitem costitem = gson.fromJson(resultAllCache.get(i), Costitem.class);
                //模糊筛选
                if ((costitem.getName() != null && costitem.getName().contains(condition)) ||
                        (costitem.getPk_group() != null && costitem.getPk_group().contains(condition))) {
                    resultList.add(costitem);
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
