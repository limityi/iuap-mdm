package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.NytStation;
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

@Repository
public class NytStationDao {
	
	@Qualifier("mdBaseDAO")
    @Autowired
    private MetadataDAO dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private NytStationRepository repository;

    @Autowired
    private OverviewService overviewService;

    private Gson gson = new Gson();
    
    public Page<NytStation> selectIneqNamePage(PageRequest pageRequest, Map<String, Object> searchParams) {

        List<NytStation> list = repository.selectAllData(searchParams);
        Page<NytStation> resultPage = new PageImpl<>(list);
        return resultPage;
    }

    /**
     * 分页查询redis缓存数据
     *
     * @param pageRequest
     * @return
     */
    public Page<NytStation> selectAllByCache(PageRequest pageRequest, String dataKey) {

        //分页查询redis
        List<String> resultCache = redisTemplate.lrange(dataKey, pageRequest.getPageNumber() * pageRequest.getPageSize(), (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1);

        //查询缓存中数据的长度
        long resultCacheSize = redisTemplate.llen(dataKey);

        //返回结果
        List<NytStation> resultList = new ArrayList<>();

        //如果有数据,转化数据
        if (resultCache != null && resultCache.size() > 0) {
            for (int i = 0; i < resultCache.size(); i++) {
                resultList.add(i, gson.fromJson(resultCache.get(i), NytStation.class));
            }
        }
        return new PageImpl<>(resultList, pageRequest, resultCacheSize);
    }

    /**
     * 查询未映射的数据
     *
     * @return
     */
    public int selectOnlyValidateData() {
        //查询未映射的数据
        List<NytStation> resultList = repository.selectOnlyValidateData();
        redisTemplate.del(RedisCacheKey.NYTSTATION_ONLY_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            //向redis放数据
            for (NytStation nyts : resultList) {
                redisTemplate.rpush(RedisCacheKey.NYTSTATION_ONLY_DATA, gson.toJson(nyts));
            }
            return resultList.size();
        } else {
            return 0;
        }
    }

    public int selectIneqNameData() {
        //查询名称对比的数据
        List<NytStation> resultList = repository.selectIneqNameData();
        redisTemplate.del(RedisCacheKey.NYTSTATION_INEQNAME_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            //向redis放数据
            for (NytStation nyts : resultList) {
                redisTemplate.rpush(RedisCacheKey.NYTSTATION_INEQNAME_DATA, gson.toJson(nyts));
            }
            return resultList.size();
        } else {
            return 0;
        }
    }

}
