package com.yonyou.iuap.project.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.cache.RedisUtil;
import com.yonyou.iuap.project.entity.ZhkyLine;
import com.yonyou.iuap.project.entity.ZhkyStation;
import com.yonyou.iuap.project.repository.ZhkyLineDao;
import com.yonyou.iuap.project.repository.ZhkyLineRepository;

@Service
public class ZhkyLineService {
	
	@Autowired
	private ZhkyLineRepository zhkylineRepository;
	
	@Autowired
	private ZhkyLineDao dao;

	@Autowired
	private RedisTemplate redisTemplate;
	
	private Gson gson =new Gson();
	
	public void zhkylineOnlyJob(){
        dao.selectOnlyValidateData();
        setSyncTime(RedisCacheKey.ZHKYLINE_ONLY_TIME);
    }

	/**
     * 获取同步时间
     * @param fieldName
     * @return String
     */
    public String getSyncTime(String fieldName){
        return RedisUtil.getSyncTime(redisTemplate,RedisCacheKey.ZHKYLINE_TIME,fieldName);
    }
    
    /**
     * 设置同步时间
     * @param fieldName
     */
    private void setSyncTime(String fieldName){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RedisUtil.setSyncTime(redisTemplate,RedisCacheKey.ZHKYLINE_TIME,fieldName, format.format(new Date()));
    }
    
    /**
     * 查询全部为映射校验失败的数据
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<ZhkyLine> selectOnlyValidateByPage(PageRequest pageRequest,SearchParams searchParams){

        boolean updateOperation=Boolean.parseBoolean(searchParams.getSearchMap().get("updateOperation").toString());

        //查询缓存数据
        Page<ZhkyLine> pageResult;

        if(updateOperation){
            //从数据库查询全部数据
            //查询数据库数据量
            int result=dao.selectOnlyValidateData();
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if(result>0){
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.ZHKYLINE_ONLY_TIME);
                pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.ZHKYLINE_ONLY_DATA);
            }else {
                setSyncTime(RedisCacheKey.ZHKYLINE_ONLY_TIME);
                pageResult=new PageImpl<>(new ArrayList<ZhkyLine>(),pageRequest,0);
            }
        }else{
            //查询缓存数据
            pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.ZHKYLINE_ONLY_DATA);
            //判断缓存是否有值
            if((!pageResult.getContent().isEmpty())&&pageResult.getContent().size()>0){
                return pageResult;
            }else{
                //从数据库查询全部数据
                //查询数据库数据量
                int result=dao.selectOnlyValidateData();
                //如果没有数据直接返回空值,如果有数据,从redis里分页取值
                if(result>0){
                    //有数据设置同步时间
                    setSyncTime(RedisCacheKey.ZHKYLINE_ONLY_TIME);
                    pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.ZHKYLINE_ONLY_DATA);
                }else{
                    setSyncTime(RedisCacheKey.ZHKYLINE_ONLY_TIME);
                    return pageResult;
                }
            }
        }
        return pageResult;
    }
    
    public Page<ZhkyLine> selectIneqNameByPage(PageRequest pageRequest,SearchParams searchParams){
        boolean updateOperation=Boolean.parseBoolean(searchParams.getSearchMap().get("updateOperation").toString());
        

        //查询缓存数据
        Page<ZhkyLine> pageResult;

        if(updateOperation){
            //从数据库查询全部数据
            //查询数据库数据量
            int result=dao.selectIneqNameData();
            
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if(result>0){
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.ZHKYLINE_INEQNAME_TIME);
                pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.ZHKYLINE_INEQNAME_DATA);
            }else {
                setSyncTime(RedisCacheKey.ZHKYLINE_INEQNAME_TIME);
                pageResult=new PageImpl<>(new ArrayList<ZhkyLine>(),pageRequest,0);
            }
        }else{
            //查询缓存数据
            pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.ZHKYLINE_INEQNAME_DATA);
            //判断缓存是否有值
            if((!pageResult.getContent().isEmpty())&&pageResult.getContent().size()>0){
                return pageResult;
            }else{
                //从数据库查询全部数据
                //查询数据库数据量
                int result=dao.selectIneqNameData();
                //如果没有数据直接返回空值,如果有数据,从redis里分页取值
                if(result>0){
                    //有数据设置同步时间
                    setSyncTime(RedisCacheKey.ZHKYLINE_INEQNAME_TIME);
                    pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.ZHKYLINE_INEQNAME_DATA);
                }else{
                    setSyncTime(RedisCacheKey.ZHKYLINE_INEQNAME_TIME);
                    return pageResult;
                }
            }
        }
        return pageResult;
    }
    
}
