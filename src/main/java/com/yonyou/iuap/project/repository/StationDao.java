package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.Station;
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
public class StationDao {

    @Qualifier("mdBaseDAO")
    @Autowired
    private MetadataDAO dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StationRepository repository;

    private Gson gson = new Gson();


    public Page<Station> selectAllByPage(PageRequest pageRequest) {
        List<Station> list = repository.selectAllData();
        Page<Station> resultPage = new PageImpl<>(list);
        return resultPage;
    }


    public void batchInsert(List<Station> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<Station> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<Station> list) {
        dao.remove(list);
    }

    /**
     * 分页查询redis缓存数据
     *
     * @param pageRequest
     * @return
     */
    public Page<Station> selectAllByCache(PageRequest pageRequest, String dataKey) {

        //分页查询redis
        List<String> resultCache = redisTemplate.lrange(dataKey, pageRequest.getPageNumber() * pageRequest.getPageSize(), (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1);

        //查询缓存中数据的长度
        long resultCacheSize = redisTemplate.llen(dataKey);

        //返回结果
        List<Station> resultList = new ArrayList<>();

        //如果有数据,转化数据
        if (resultCache != null && resultCache.size() > 0) {
            for (int i = 0; i < resultCache.size(); i++) {
                resultList.add(i, gson.fromJson(resultCache.get(i), Station.class));
            }
        }
        return new PageImpl<>(resultList, pageRequest, resultCacheSize);
    }

    /**
     * 根据条件分页查询redis数据
     * @param pageRequest
     * @param dataKey
     * @param searchMap
     * @return
     */
    public Page<Station> selectCacheByCondition(PageRequest pageRequest,String dataKey,Map<String, Object> searchMap){

        //查询缓存中数据的长度
        Long resultCacheSize = redisTemplate.llen(dataKey);

        //查询所有数据
        List<String> resultAllCache = redisTemplate.lrange(dataKey, 0, resultCacheSize.intValue());

        //返回结果
        List<Station> resultList = new ArrayList<>();

        List<Station> resultListPage = new ArrayList<>();

        String condition=searchMap.get("searchParam").toString();

        //如果有数据,转化数据
        if (resultAllCache != null && resultAllCache.size() > 0) {
            for (int i = 0; i < resultAllCache.size(); i++) {
                Station station = gson.fromJson(resultAllCache.get(i), Station.class);
                //模糊筛选
                if(station.getName().contains(condition)||station.getStation_companyname().contains(condition)){
                    resultList.add(station);
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
    public Page<Station> selectCacheByConditionRequired(PageRequest pageRequest,String dataKey,Map<String, Object> searchMap){

        //查询缓存中数据的长度
        Long resultCacheSize = redisTemplate.llen(dataKey);

        //查询所有数据
        List<String> resultAllCache = redisTemplate.lrange(dataKey, 0, resultCacheSize.intValue());

        //返回结果
        List<Station> resultList = new ArrayList<>();

        List<Station> resultListPage = new ArrayList<>();

        String condition=searchMap.get("searchParam").toString();

        //如果有数据,转化数据
        if (resultAllCache != null && resultAllCache.size() > 0) {
            for (int i = 0; i < resultAllCache.size(); i++) {
                Station station = gson.fromJson(resultAllCache.get(i), Station.class);
                //模糊筛选
                if(station.getName().contains(condition)){
                    resultList.add(station);
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

    /**
     * 查询唯一性校验失败的数据
     *
     * @return
     */
    public int selectOnlyValidateData() {
        //查询唯一性校验失败的数据
        List<Station> resultList = repository.selectOnlyValidateData();
        redisTemplate.del(RedisCacheKey.STASION_ONLY_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            //向redis放数据
            for (Station station : resultList) {
                redisTemplate.rpush(RedisCacheKey.STASION_ONLY_DATA, gson.toJson(station));
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
        List<Station> resultList = repository.selectRequiredData(searchParams);
        redisTemplate.del(RedisCacheKey.STASION_REQUIRED_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (Station station : resultList) {
                redisTemplate.rpush(RedisCacheKey.STASION_REQUIRED_DATA, gson.toJson(station));
            }
            return resultList.size();
        }
        return 0;
    }

    public static void main(String[] args){
        String str="郑云平";
        System.out.println(str.contains("郑"));
    }

}
