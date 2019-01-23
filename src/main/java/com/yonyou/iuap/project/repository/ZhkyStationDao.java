package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.dt.DTEnum;
import com.yonyou.iuap.project.entity.ZhkyStation;
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
public class ZhkyStationDao {

    @Qualifier("mdBaseDAO")
    @Autowired
    private MetadataDAO dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ZhkyStationRepository repository;

    @Autowired
    private OverviewService overviewService;

    private Gson gson = new Gson();

    public Page<ZhkyStation> selectIneqNamePage(PageRequest pageRequest, Map<String, Object> searchParams) {

        List<ZhkyStation> list = repository.selectAllData(searchParams);
        Page<ZhkyStation> resultPage = new PageImpl<>(list);
        return resultPage;
    }

    /**
     * 分页查询redis缓存数据
     *
     * @param pageRequest
     * @return
     */
    public Page<ZhkyStation> selectAllByCache(PageRequest pageRequest, String dataKey) {

        //分页查询redis
        List<String> resultCache = redisTemplate.lrange(dataKey, pageRequest.getPageNumber() * pageRequest.getPageSize(), (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1);

        //查询缓存中数据的长度
        long resultCacheSize = redisTemplate.llen(dataKey);
//        overviewService.updateMdmDataStatistics(DTEnum.ZhkyMenus.zhkystation1.getId().split("md_")[1].toUpperCase(), DTEnum.ZhkyMenus.zhkystation1.getDtName(), 2, resultCacheSize);

        //返回结果
        List<ZhkyStation> resultList = new ArrayList<>();

        //如果有数据,转化数据
        if (resultCache != null && resultCache.size() > 0) {
            for (int i = 0; i < resultCache.size(); i++) {
                resultList.add(i, gson.fromJson(resultCache.get(i), ZhkyStation.class));
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
        int i = 0;
        List<ZhkyStation> resultList = repository.selectOnlyValidateData();
        redisTemplate.del(RedisCacheKey.ZHKYSTATION_ONLY_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            //向redis放数据
            for (ZhkyStation zhs : resultList) {
                redisTemplate.rpush(RedisCacheKey.ZHKYSTATION_ONLY_DATA, gson.toJson(zhs));
            }
            i = resultList.size();
        }
        overviewService.updateMdmDataStatistics(DTEnum.MdmSys.ZHKY.getId(),DTEnum.ZhkyMenus.zhkystation1.getId().split("md_")[1].toUpperCase(), DTEnum.ZhkyMenus.zhkystation1.getDtName(), 1, (long)i);
        return i;
    }

    public int selectIneqNameData() {
        //查询唯一性校验失败的数据
        List<ZhkyStation> resultList = repository.selectIneqNameData();
        redisTemplate.del(RedisCacheKey.ZHKYSTATION_INEQNAME_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            //向redis放数据
            for (ZhkyStation zhs : resultList) {
                redisTemplate.rpush(RedisCacheKey.ZHKYSTATION_INEQNAME_DATA, gson.toJson(zhs));
            }
            return resultList.size();
        } else {
            return 0;
        }
    }

}
