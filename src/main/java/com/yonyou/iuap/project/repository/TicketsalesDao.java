package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.Station;
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
 * @author binbin
 * @Description 客票代售网点
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

    public Page<Ticketsales> selectAllByPage(PageRequest pageRequest) {
        List<Ticketsales> list = ticketsalesRepository.selectAllData();
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
        redisTemplate.del(RedisCacheKey.TICKETSALES_ONLY_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (Ticketsales ticketsales : resultList) {
                redisTemplate.rpush(RedisCacheKey.TICKETSALES_ONLY_DATA, gson.toJson(ticketsales));
            }
            return resultList.size();
        } else {
            return 0;
        }
    }

    public int selectRequiredData(List<String> columns, Map<String, Object> searchParams) {
        searchParams.put("requiredColumns", columns);
        List<Ticketsales> resultList = ticketsalesRepository.selectRequiredData(searchParams);
        redisTemplate.del(RedisCacheKey.TICKETSALES_REQUIRED_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (Ticketsales ticketsales : resultList) {
                redisTemplate.rpush(RedisCacheKey.TICKETSALES_REQUIRED_DATA, gson.toJson(ticketsales));
            }
            return resultList.size();
        }
        return 0;
    }
    
    /**
     * 根据条件分页查询redis数据
     * @param pageRequest
     * @param dataKey
     * @param searchMap
     * @return
     */
    public Page<Ticketsales> selectCacheByCondition(PageRequest pageRequest,String dataKey,Map<String, Object> searchMap){

        //查询缓存中数据的长度
        Long resultCacheSize = redisTemplate.llen(dataKey);

        //查询所有数据
        List<String> resultAllCache = redisTemplate.lrange(dataKey, 0, resultCacheSize.intValue());

        //返回结果
        List<Ticketsales> resultList = new ArrayList<>();

        List<Ticketsales> resultListPage = new ArrayList<>();

        String condition=searchMap.get("searchParam").toString();

        //如果有数据,转化数据
        if (resultAllCache != null && resultAllCache.size() > 0) {
            for (int i = 0; i < resultAllCache.size(); i++) {
            	Ticketsales ticketsales = gson.fromJson(resultAllCache.get(i), Ticketsales.class);
                //模糊筛选
                if((ticketsales.getName()!=null && ticketsales.getName().contains(condition))||
                		(ticketsales.getBusiness_org()!=null && ticketsales.getBusiness_org().contains(condition))){
                    resultList.add(ticketsales);
                }
            }
        }

        if(resultList.size()<pageRequest.getPageSize()){
            return new PageImpl<>(resultList, pageRequest, resultList.size());
        }else {
            //取分页数据
            int start = pageRequest.getPageNumber() * pageRequest.getPageSize();
            int end = (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1;

            for (int i = start; i <= end; i++) {
                try {
                    resultListPage.add(resultList.get(i));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return new PageImpl<>(resultListPage, pageRequest, resultList.size());
        }

    }

    /**
     * 根据条件分页查询redis数据
     * @param pageRequest
     * @param dataKey
     * @param searchMap
     * @return
     */
    public Page<Ticketsales> selectCacheByConditionRequired(PageRequest pageRequest,String dataKey,Map<String, Object> searchMap){

        //查询缓存中数据的长度
        Long resultCacheSize = redisTemplate.llen(dataKey);

        //查询所有数据
        List<String> resultAllCache = redisTemplate.lrange(dataKey, 0, resultCacheSize.intValue());

        //返回结果
        List<Ticketsales> resultList = new ArrayList<>();

        List<Ticketsales> resultListPage = new ArrayList<>();

        String condition=searchMap.get("searchParam").toString();

        //如果有数据,转化数据
        if (resultAllCache != null && resultAllCache.size() > 0) {
            for (int i = 0; i < resultAllCache.size(); i++) {
            	Ticketsales ticketsales = gson.fromJson(resultAllCache.get(i), Ticketsales.class);
                //模糊筛选
                if((ticketsales.getName()!=null && ticketsales.getName().contains(condition))||
                		(ticketsales.getBusiness_org()!=null && ticketsales.getBusiness_org().contains(condition))){
                    resultList.add(ticketsales);
                }
            }
        }

        if(resultList!=null&&resultList.size()>0){
            if(resultList.size()<pageRequest.getPageSize()){
                return new PageImpl<>(resultList, pageRequest, resultList.size());
            }else {
                //取分页数据
                int start = pageRequest.getPageNumber() * pageRequest.getPageSize();
                int end = (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1;

                for (int i = start; i <= end; i++) {
                    try {
                        resultListPage.add(resultList.get(i));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                return new PageImpl<>(resultListPage, pageRequest, resultList.size());
            }
        }else{
            return new PageImpl<>(resultListPage, pageRequest, 0);
        }
    }

}
