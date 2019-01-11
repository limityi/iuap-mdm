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
 * @author binbin
 * @Description 服务区
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

    public Page<ServiceArea> selectAllByPage(PageRequest pageRequest) {
        List<ServiceArea> list = serviceAreaRepository.selectAllData();
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

    /**
     * 分页查询redis缓存数据
     *
     * @param pageRequest
     * @return
     */
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
        redisTemplate.del(RedisCacheKey.SERVICE_AREA_ONLY_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (ServiceArea serviceArea : resultList) {
                redisTemplate.rpush(RedisCacheKey.SERVICE_AREA_ONLY_DATA, gson.toJson(serviceArea));
            }
            return resultList.size();
        } else {
            return 0;
        }
    }

    public int selectRequiredData(List<String> columns, Map<String, Object> searchParams) {
        searchParams.put("requiredColumns", columns);
        List<ServiceArea> resultList = serviceAreaRepository.selectRequiredData(searchParams);
        redisTemplate.del(RedisCacheKey.SERVICE_AREA_REQUIRED_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (ServiceArea serviceArea : resultList) {
                redisTemplate.rpush(RedisCacheKey.SERVICE_AREA_REQUIRED_DATA, gson.toJson(serviceArea));
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
    public Page<ServiceArea> selectCacheByCondition(PageRequest pageRequest,String dataKey,Map<String, Object> searchMap){

        //查询缓存中数据的长度
        Long resultCacheSize = redisTemplate.llen(dataKey);

        //查询所有数据
        List<String> resultAllCache = redisTemplate.lrange(dataKey, 0, resultCacheSize.intValue());

        //返回结果
        List<ServiceArea> resultList = new ArrayList<>();

        List<ServiceArea> resultListPage = new ArrayList<>();

        String condition=searchMap.get("searchParam").toString();

        //如果有数据,转化数据
        if (resultAllCache != null && resultAllCache.size() > 0) {
        	
            for (int i = 0; i < resultAllCache.size(); i++) {
            	ServiceArea servicearea = gson.fromJson(resultAllCache.get(i), ServiceArea.class);
                //模糊筛选
                if((servicearea.getName()!=null&&servicearea.getName().contains(condition))||(servicearea.getAdministrativeregion()!=null&&servicearea.getAdministrativeregion().contains(condition))){
                    resultList.add(servicearea);
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
    public Page<ServiceArea> selectCacheByConditionRequired(PageRequest pageRequest,String dataKey,Map<String, Object> searchMap){

        //查询缓存中数据的长度
        Long resultCacheSize = redisTemplate.llen(dataKey);

        //查询所有数据
        List<String> resultAllCache = redisTemplate.lrange(dataKey, 0, resultCacheSize.intValue());

        //返回结果
        List<ServiceArea> resultList = new ArrayList<>();

        List<ServiceArea> resultListPage = new ArrayList<>();

        String condition=searchMap.get("searchParam").toString();

        //如果有数据,转化数据
        if (resultAllCache != null && resultAllCache.size() > 0) {
            for (int i = 0; i < resultAllCache.size(); i++) {
            	ServiceArea servicearea = gson.fromJson(resultAllCache.get(i), ServiceArea.class);
                //模糊筛选
                if(servicearea.getName()!=null&&servicearea.getName().contains(condition)){
                    resultList.add(servicearea);
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
