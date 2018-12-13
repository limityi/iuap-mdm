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

import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.Lines;
import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;

/**
 * <p>Title: CardTableMetaDao</p>
 * <p>Description: </p>
 */
@Repository
public class LinesDao {
	
	@Qualifier("mdBaseDAO")
	@Autowired
	private MetadataDAO dao;
	
	@Autowired
    private RedisTemplate redisTemplate;
	
	@Autowired
    private LinesRepository repository;
	
	private Gson gson=new Gson();
	
	//根据某一非主键字段查询实体
	/*public List<Lines> findByCode(String code){
		String sql = "select * from mdm_lines where code=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(code);
        List<Lines> list = dao.queryByClause(Lines.class, sql, sqlparam);
        return list;
	}*/
	//根据某一非主键字段查询实体
	/*public List<Lines> findByName(String name){
		String sql = "select * from mdm_lines where name=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(name);
        List<Lines> list = dao.queryByClause(Lines.class, sql, sqlparam);
        return list;
	}*/
    
    public Page<Lines> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        /*String sql = " select * from mdm_lines"; 
        SQLParameter sqlparam = new SQLParameter();
        if (!searchParams.isEmpty()) {
            sql = sql + " where ";
            for (String key : searchParams.keySet()) {
                if (key.equalsIgnoreCase("searchParam")) {
                    sql =sql + "(code like ? OR name like ?) AND";
                    for (int i = 0; i < 2; i++) {
                        sqlparam.addParam("%" + searchParams.get(key) + "%");
                    }
                }
            }
            sql = sql.substring(0, sql.length() - 4);
        }
        return dao.queryPage(sql, sqlparam, pageRequest, Lines.class);*/
    	List<Lines> list = repository.selectAllData(searchParams);
    	Page<Lines> resultPage=new PageImpl<>(list);
    	return resultPage;
    }
    
    
    public void batchInsert(List<Lines> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<Lines> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<Lines> list) {
        dao.remove(list);
    }
    
    /**
     * 分页查询redis缓存数据
     * @param pageRequest
     * @return
     */
    public Page<Lines> selectAllByCache(PageRequest pageRequest,String dataKey){

        //分页查询redis
        List<String> resultCache=redisTemplate.lrange(dataKey,pageRequest.getPageNumber()*pageRequest.getPageSize(),(pageRequest.getPageNumber()+1)*pageRequest.getPageSize());

        //查询缓存中数据的长度
        long resultCacheSize=redisTemplate.llen(dataKey);

        //返回结果
        List<Lines> resultList= new ArrayList<>();

        //如果有数据,转化数据
        if(resultCache!=null&&resultCache.size()>0){
            for(int i = 0; i<resultCache.size(); i++){
                resultList.add(i, gson.fromJson(resultCache.get(i),Lines.class) );
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
        List<Lines> resultList=repository.selectOnlyValidateData();
        if((!resultList.isEmpty())&&resultList.size()>0){
            redisTemplate.del(RedisCacheKey.LINE_ONLY_DATA);
            //向redis放数据
            for (Lines lines:resultList
                 ) {
                redisTemplate.rpush(RedisCacheKey.LINE_ONLY_DATA,gson.toJson(lines));
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
        List<Lines> resultList=repository.selectRequiredData(columns);

        if((!resultList.isEmpty())&&resultList.size()>0){
            redisTemplate.del(RedisCacheKey.LINE_REQUIRED_DATA);

            for(Lines lines : resultList){
                redisTemplate.rpush(RedisCacheKey.LINE_REQUIRED_DATA,gson.toJson(lines));
            }
            return resultList.size();
        }
        return 0;
    }
}
