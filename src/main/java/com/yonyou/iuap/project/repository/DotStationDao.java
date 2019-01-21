package com.yonyou.iuap.project.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.Dot;
import com.yonyou.iuap.project.entity.DotStation;
import com.yonyou.iuap.project.entity.Station;

@Repository
public class DotStationDao {

	@Qualifier("mdBaseDAO")
    @Autowired
    private MetadataDAO dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DotStationRepository repository;

    private Gson gson = new Gson();
    
    public Page<Dot> selectAllByPageDot(PageRequest pageRequest) {
        List<Dot> list = repository.selectAllData();
        Page<Dot> resultPage = new PageImpl<>(list);
        return resultPage;
    }
    
    public Page<Station> selectAllByPageStation(PageRequest pageRequest1) {
        List<Station> list = repository.selectAllData1();
        Page<Station> resultPage = new PageImpl<>(list);
        return resultPage;
    }

    /**
     * 分页查询redis缓存数据
     *
     * @param pageRequest
     * @return
     */
    public Page<DotStation> selectAllByCache(PageRequest pageRequest, String dataKey) {

        //分页查询redis
        List<String> resultCache = redisTemplate.lrange(dataKey, pageRequest.getPageNumber() * pageRequest.getPageSize(), (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1);

        //查询缓存中数据的长度
        long resultCacheSize = redisTemplate.llen(dataKey);

        //返回结果
        List<DotStation> resultList = new ArrayList<>();

        //如果有数据,转化数据
        if (resultCache != null && resultCache.size() > 0) {
            for (int i = 0; i < resultCache.size(); i++) {
                resultList.add(i, gson.fromJson(resultCache.get(i), DotStation.class));
            }
        }
        return new PageImpl<>(resultList, pageRequest, resultCacheSize);
    }

}
