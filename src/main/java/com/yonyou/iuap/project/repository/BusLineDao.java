package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.dt.DTEnum;
import com.yonyou.iuap.project.entity.BusLine;
import com.yonyou.iuap.project.entity.SjzyOrg;
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
 * <p>Title: CardTableMetaDao</p>
 * <p>Description: </p>
 */
@Repository
public class BusLineDao {

    @Qualifier("mdBaseDAO")
    @Autowired
    private MetadataDAO dao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BusLineRepository repository;

    @Autowired
    private OrgRepository orgRepository;

    @Autowired
    private OverviewService overviewService;

    private Gson gson = new Gson();

    public Page<BusLine> selectAllByPage(PageRequest pageRequest) {

        List<BusLine> list = repository.selectAllData();
        Page<BusLine> resultPage = new PageImpl<>(list);
        return resultPage;
    }

    /**
     * 分页查询redis缓存数据
     *
     * @param pageRequest
     * @return
     */
    public Page<BusLine> selectAllByCache(PageRequest pageRequest, String dataKey) {

        //分页查询redis
        List<String> resultCache = redisTemplate.lrange(dataKey, pageRequest.getPageNumber() * pageRequest.getPageSize(), (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1);

        //查询缓存中数据的长度
        long resultCacheSize = redisTemplate.llen(dataKey);

        //返回结果
        List<BusLine> resultList = new ArrayList<>();

        //如果有数据,转化数据
        if (resultCache != null && resultCache.size() > 0) {
            for (int i = 0; i < resultCache.size(); i++) {
                BusLine busLine = gson.fromJson(resultCache.get(i), BusLine.class);

                String code = busLine.getLine_institutionname();
                SjzyOrg org = orgRepository.getSjzyOrgByCode(code);
                if (org != null) {
                    busLine.setLine_institutionname(org.getName());
                }

                resultList.add(i, busLine);
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
        List<BusLine> resultList = repository.selectOnlyValidateData();
        redisTemplate.del(RedisCacheKey.BUSLINE_ONLY_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            //向redis放数据
            for (BusLine busline : resultList) {
                redisTemplate.rpush(RedisCacheKey.BUSLINE_ONLY_DATA, gson.toJson(busline));
            }
            i = resultList.size();
        }
        overviewService.updateMdmDataStatistics(DTEnum.MdmSys.MDM.getId(),DTEnum.UserMenus.busline.getId().split("md_")[1].toUpperCase(), DTEnum.UserMenus.busline.getDtName(), 1, (long) i);
        return i;
    }

    /**
     * 查询必填项没有数据的数据
     *
     * @return
     */
    public int selectRequiredData(List<String> columns, Map<String, Object> searchParams) {
        searchParams.put("requiredColumns", columns);
        List<BusLine> resultList = repository.selectRequiredData(searchParams);
        redisTemplate.del(RedisCacheKey.BUSLINE_REQUIRED_DATA);
        if ((!resultList.isEmpty()) && resultList.size() > 0) {
            for (BusLine busline : resultList) {
                redisTemplate.rpush(RedisCacheKey.BUSLINE_REQUIRED_DATA, gson.toJson(busline));
            }
            return resultList.size();
        }
        return 0;
    }

    public void batchInsert(List<BusLine> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<BusLine> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<BusLine> list) {
        dao.remove(list);
    }
    
    /**
     * 根据条件分页查询redis数据
     * @param pageRequest
     * @param dataKey
     * @param searchMap
     * @return
     */
    public Page<BusLine> selectCacheByCondition(PageRequest pageRequest,String dataKey,Map<String, Object> searchMap){

        //查询缓存中数据的长度
        Long resultCacheSize = redisTemplate.llen(dataKey);

        //查询所有数据
        List<String> resultAllCache = redisTemplate.lrange(dataKey, 0, resultCacheSize.intValue());

        //返回结果
        List<BusLine> resultList = new ArrayList<>();

        List<BusLine> resultListPage = new ArrayList<>();

        String condition=searchMap.get("searchParam").toString();

        //如果有数据,转化数据
        if (resultAllCache != null && resultAllCache.size() > 0) {
            for (int i = 0; i < resultAllCache.size(); i++) {
            	BusLine busline = gson.fromJson(resultAllCache.get(i), BusLine.class);
                //模糊筛选
                if((busline.getName()!=null && busline.getName().contains(condition))||
                		(busline.getName1()!=null && busline.getName1().contains(condition))){
                    resultList.add(busline);
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
    public Page<BusLine> selectCacheByConditionRequired(PageRequest pageRequest,String dataKey,Map<String, Object> searchMap){

        //查询缓存中数据的长度
        Long resultCacheSize = redisTemplate.llen(dataKey);

        //查询所有数据
        List<String> resultAllCache = redisTemplate.lrange(dataKey, 0, resultCacheSize.intValue());

        //返回结果
        List<BusLine> resultList = new ArrayList<>();

        List<BusLine> resultListPage = new ArrayList<>();

        String condition=searchMap.get("searchParam").toString();

        //如果有数据,转化数据
        if (resultAllCache != null && resultAllCache.size() > 0) {
            for (int i = 0; i < resultAllCache.size(); i++) {
            	BusLine busline = gson.fromJson(resultAllCache.get(i), BusLine.class);
                //模糊筛选
                if((busline.getName()!=null && busline.getName().contains(condition))||
                		(busline.getName1()!=null && busline.getName1().contains(condition))){
                    resultList.add(busline);
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
