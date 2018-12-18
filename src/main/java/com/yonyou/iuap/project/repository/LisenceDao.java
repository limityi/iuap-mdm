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
import com.yonyou.iuap.project.entity.Lisence;

/**
 * <p>Title: CardTableMetaDao</p>
 * <p>Description: </p>
 */
@Repository
public class LisenceDao {
	
	@Qualifier("mdBaseDAO")
	@Autowired
	private MetadataDAO dao;
	
	@Autowired
    private RedisTemplate redisTemplate;
	
	@Autowired
    private LisenceRepository repository;
	
	private Gson gson=new Gson();
	
	public Page<Lisence> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        
    	List<Lisence> list = repository.selectAllData(searchParams);
    	Page<Lisence> resultPage=new PageImpl<>(list);
    	return resultPage;
    }
	
	public void batchInsert(List<Lisence> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<Lisence> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<Lisence> list) {
        dao.remove(list);
    }
    
    /**
     * 分页查询redis缓存数据
     * @param pageRequest
     * @return
     */
    public Page<Lisence> selectAllByCache(PageRequest pageRequest,String dataKey){

        //分页查询redis
        List<String> resultCache=redisTemplate.lrange(dataKey,pageRequest.getPageNumber()*pageRequest.getPageSize(),(pageRequest.getPageNumber()+1)*pageRequest.getPageSize());

        //查询缓存中数据的长度
        long resultCacheSize=redisTemplate.llen(dataKey);

        //返回结果
        List<Lisence> resultList= new ArrayList<>();

        //如果有数据,转化数据
        if(resultCache!=null&&resultCache.size()>0){
            for(int i = 0; i<resultCache.size(); i++){
                resultList.add(i, gson.fromJson(resultCache.get(i),Lisence.class) );
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
        List<Lisence> resultList=repository.selectOnlyValidateData();
        if((!resultList.isEmpty())&&resultList.size()>0){
            redisTemplate.del(RedisCacheKey.LISENCE_ONLY_DATA);
            //向redis放数据
            for (Lisence lisence:resultList
                 ) {
                redisTemplate.rpush(RedisCacheKey.LISENCE_ONLY_DATA,gson.toJson(lisence));
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
        List<Lisence> resultList=repository.selectRequiredData(columns);

        if((!resultList.isEmpty())&&resultList.size()>0){
            redisTemplate.del(RedisCacheKey.LISENCE_REQUIRED_DATA);

            for(Lisence lisence : resultList){
                redisTemplate.rpush(RedisCacheKey.LISENCE_REQUIRED_DATA,gson.toJson(lisence));
            }
            return resultList.size();
        }
        return 0;
    }

}
