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
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.Fleet;

/**
 * <p>Title: CardTableMetaDao</p>
 * <p>Description: </p>
 */
@Repository
public class FleetDao {
	
	@Qualifier("mdBaseDAO")
	@Autowired
	private MetadataDAO dao;
	
	@Autowired
    private RedisTemplate redisTemplate;
	
	@Autowired
    private FleetRepository repository;
	
	private Gson gson=new Gson();
	
	public Page<Fleet> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        
    	List<Fleet> list = repository.selectAllData(searchParams);
    	Page<Fleet> resultPage=new PageImpl<>(list);
    	return resultPage;
    }
	
	public void batchInsert(List<Fleet> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<Fleet> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<Fleet> list) {
        dao.remove(list);
    }
    
    /**
     * 分页查询redis缓存数据
     * @param pageRequest
     * @return
     */
    public Page<Fleet> selectAllByCache(PageRequest pageRequest,String dataKey){

        //分页查询redis
        List<String> resultCache=redisTemplate.lrange(dataKey,pageRequest.getPageNumber()*pageRequest.getPageSize(),(pageRequest.getPageNumber()+1)*pageRequest.getPageSize());

        //查询缓存中数据的长度
        long resultCacheSize=redisTemplate.llen(dataKey);

        //返回结果
        List<Fleet> resultList= new ArrayList<>();

        //如果有数据,转化数据
        if(resultCache!=null&&resultCache.size()>0){
            for(int i = 0; i<resultCache.size(); i++){
                resultList.add(i, gson.fromJson(resultCache.get(i),Fleet.class) );
            }
        }
        return new PageImpl<>(resultList,pageRequest,resultCacheSize);
    }
    
    /**
     * 查询唯一性校验失败的数据
     * @return
     */
    public int selectOnlyValidateData(){
        //查询唯一性校验失败的数据
        List<Fleet> resultList=repository.selectOnlyValidateData();
        if((!resultList.isEmpty())&&resultList.size()>0){
            redisTemplate.del(RedisCacheKey.FLEET_ONLY_DATA);
            //向redis放数据
            for (Fleet fleet:resultList
                 ) {
                redisTemplate.rpush(RedisCacheKey.FLEET_ONLY_DATA,gson.toJson(fleet));
            }
            return resultList.size();
        }else{
            return 0;
        }
    }
    
    /**
     * 查询必填项没有数据的数据
     * @return
     */
    public int selectRequiredData(List<String> columns){
        List<Fleet> resultList=repository.selectRequiredData(columns);

        if((!resultList.isEmpty())&&resultList.size()>0){
            redisTemplate.del(RedisCacheKey.FLEET_REQUIRED_DATA);

            for(Fleet fleet : resultList){
                redisTemplate.rpush(RedisCacheKey.FLEET_REQUIRED_DATA,gson.toJson(fleet));
            }
            return resultList.size();
        }
        return 0;
    }

}