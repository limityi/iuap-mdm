package com.yonyou.iuap.project.repository;

import java.util.ArrayList;
import java.util.List;

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
import com.yonyou.iuap.project.entity.ZhkyLine;

@Repository
public class ZhkyLineDao {
	
	@Qualifier("mdBaseDAO")
	@Autowired
	private MetadataDAO dao;
	
	@Autowired
    private RedisTemplate redisTemplate;
	
	@Autowired
	private ZhkyLineRepository repository;
	
	private Gson gson=new Gson();
	
	/**
     * 分页查询redis缓存数据
     * @param pageRequest
     * @return
     */
    public Page<ZhkyLine> selectAllByCache(PageRequest pageRequest,String dataKey){

        //分页查询redis
        List<String> resultCache=redisTemplate.lrange(dataKey,pageRequest.getPageNumber()*pageRequest.getPageSize(),(pageRequest.getPageNumber()+1)*pageRequest.getPageSize()-1);

        //查询缓存中数据的长度
        long resultCacheSize=redisTemplate.llen(dataKey);

        //返回结果
        List<ZhkyLine> resultList= new ArrayList<>();

        //如果有数据,转化数据
        if(resultCache!=null&&resultCache.size()>0){
            for(int i = 0; i<resultCache.size(); i++){
                resultList.add(i, gson.fromJson(resultCache.get(i),ZhkyLine.class) );
            }
        }
        return new PageImpl<>(resultList,pageRequest,resultCacheSize);
    }
    
    /**
     * 查询未映射校验失败的数据
     * @return
     */
    public int selectOnlyValidateData(){
        //查询唯一性校验失败的数据
        List<ZhkyLine> resultList=repository.selectOnlyValidateData();
        if((!resultList.isEmpty())&&resultList.size()>0){
            redisTemplate.del(RedisCacheKey.ZHKYLINE_ONLY_DATA);
            //向redis放数据
            for (ZhkyLine zhl:resultList
                 ) {
                redisTemplate.rpush(RedisCacheKey.ZHKYLINE_ONLY_DATA,gson.toJson(zhl));
            }
            return resultList.size();
        }else{
            return 0;
        }
    }
    
    public int selectIneqNameData(){
        //查询唯一性校验失败的数据
        List<ZhkyLine> resultList=repository.selectIneqNameData();
        if((!resultList.isEmpty())&&resultList.size()>0){
            redisTemplate.del(RedisCacheKey.ZHKYLINE_INEQNAME_DATA);
            //向redis放数据
            for (ZhkyLine zhl:resultList
                 ) {
                redisTemplate.rpush(RedisCacheKey.ZHKYLINE_INEQNAME_DATA,gson.toJson(zhl));
            }
            return resultList.size();
        }else{
            return 0;
        }
    }

}
