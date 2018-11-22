package com.yonyou.iuap.station.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.station.cache.RedisCacheKey;
import com.yonyou.iuap.station.cache.RedisTemplate;
import com.yonyou.iuap.station.entity.NbStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springside.modules.nosql.redis.JedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: CardTableMetaDao</p>
 * <p>Description: </p>
 */
@Repository
public class NbStationDao {
	
	@Qualifier("mdBaseDAO")
	@Autowired
	private MetadataDAO dao;

    @Autowired
    private JedisTemplate jedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    private Gson gson=new Gson();
	
	//根据某一非主键字段查询实体
	public List<NbStation> findByCode(String code){
		String sql = "select * from mdm_nbstation where code=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(code);
        List<NbStation> list = dao.queryByClause(NbStation.class, sql, sqlparam);
        return list;
	}
	//根据某一非主键字段查询实体
	public List<NbStation> findByName(String name){
		String sql = "select * from mdm_nbstation where name=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(name);
        List<NbStation> list = dao.queryByClause(NbStation.class, sql, sqlparam);
        return list;
	}
    
    public Page<NbStation> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {

        StringBuilder sql = new StringBuilder(" select * from mdm_nbstation");
        SQLParameter sqlparam = new SQLParameter();
        if (!searchParams.isEmpty()) {
            sql.append(" where ");
            for (String key : searchParams.keySet()) {
                if (key.equalsIgnoreCase("searchParam")) {
                    sql.append("(code like ? OR name like ?) AND");
                    for (int i = 0; i < 2; i++) {
                        sqlparam.addParam("%" + searchParams.get(key) + "%");
                    }
                }
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 4));
        }
        return dao.queryPage(sql.toString(), sqlparam, pageRequest, NbStation.class);
    }
    
    
    public void batchInsert(List<NbStation> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<NbStation> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<NbStation> list) {
        dao.remove(list);
    }

    /**
     * 分页查询redis缓存数据
     * @param pageRequest
     * @return
     */
    public Page<NbStation> selectAllByCache(PageRequest pageRequest){

        //分页查询redis
        List<String> resultCache=redisTemplate.lrange(RedisCacheKey.STASION_COED,pageRequest.getPageNumber()*pageRequest.getPageSize(),(pageRequest.getPageNumber()+1)*pageRequest.getPageSize());

        //查询缓存中数据的长度
        long resultCacheSize=redisTemplate.llen(RedisCacheKey.STASION_COED);

        //返回结果
        List<NbStation> resultList= new ArrayList<>();

        //如果有数据,转化数据
        if(resultCache!=null&&resultCache.size()>0){
            for(int i = 0; i<resultCache.size(); i++){
                resultList.add(i, gson.fromJson(resultCache.get(i),NbStation.class) );
            }
        }
        return new PageImpl<>(resultList,pageRequest,resultCacheSize);
    }
}
