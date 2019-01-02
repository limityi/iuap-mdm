package com.yonyou.iuap.project.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

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
import com.yonyou.iuap.project.entity.Bus;
import com.yonyou.iuap.project.entity.ZhkyStation;
import com.yonyou.iuap.project.repository.ZhkyStationDao;
import com.yonyou.iuap.project.repository.ZhkyStationRepository;

@Service
public class ZhkyStationService {

	@Autowired
	private ZhkyStationDao dao;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	ZhkyStationRepository zhkystationRepository;
	
	private Gson gson =new Gson();
	
	public void zhkystationOnlyJob(){
        dao.selectOnlyValidateData();
        setSyncTime(RedisCacheKey.ZHKYSTATION_ONLY_TIME);
    }
	
	/**
     * 获取同步时间
     * @param fieldName
     * @return String
     */
    public String getSyncTime(String fieldName){
        return RedisUtil.getSyncTime(redisTemplate,RedisCacheKey.ZHKYSTATION_TIME,fieldName);
    }
    
    /**
     * 设置同步时间
     * @param fieldName
     */
    private void setSyncTime(String fieldName){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RedisUtil.setSyncTime(redisTemplate,RedisCacheKey.ZHKYSTATION_TIME,fieldName, format.format(new Date()));
    }
    
    /**
     * 查询全部为映射校验失败的数据
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<ZhkyStation> selectOnlyValidateByPage(PageRequest pageRequest,SearchParams searchParams){

        boolean updateOperation=Boolean.parseBoolean(searchParams.getSearchMap().get("updateOperation").toString());

        //查询缓存数据
        Page<ZhkyStation> pageResult;

        if(updateOperation){
            //从数据库查询全部数据
            //查询数据库数据量
            int result=dao.selectOnlyValidateData();
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if(result>0){
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.ZHKYSTATION_ONLY_TIME);
                pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.ZHKYSTATION_ONLY_DATA);
            }else {
                setSyncTime(RedisCacheKey.ZHKYSTATION_ONLY_TIME);
                pageResult=new PageImpl<>(new ArrayList<ZhkyStation>(),pageRequest,0);
            }
        }else{
            //查询缓存数据
            pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.ZHKYSTATION_ONLY_DATA);
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
                    setSyncTime(RedisCacheKey.ZHKYSTATION_ONLY_TIME);
                    pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.ZHKYSTATION_ONLY_DATA);
                }else{
                    setSyncTime(RedisCacheKey.ZHKYSTATION_ONLY_TIME);
                    return pageResult;
                }
            }
        }
        return pageResult;
    }
    
    public Page<ZhkyStation> selectIneqNameByPage(PageRequest pageRequest,SearchParams searchParams){
        boolean updateOperation=Boolean.parseBoolean(searchParams.getSearchMap().get("updateOperation").toString());
   
        //查询缓存数据
        Page<ZhkyStation> pageResult;

        if(updateOperation){
            //从数据库查询全部数据
            //查询数据库数据量
            int result=dao.selectIneqNameData();
            
            //如果没有数据直接返回空值,如果有数据,从redis里分页取值
            if(result>0){
                //有数据设置同步时间
                setSyncTime(RedisCacheKey.ZHKYSTATION_INEQNAME_TIME);
                pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.ZHKYSTATION_INEQNAME_DATA);
            }else {
                setSyncTime(RedisCacheKey.ZHKYSTATION_INEQNAME_TIME);
                pageResult=new PageImpl<>(new ArrayList<ZhkyStation>(),pageRequest,0);
            }
        }else{
            //查询缓存数据
            pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.ZHKYSTATION_INEQNAME_DATA);
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
                    setSyncTime(RedisCacheKey.ZHKYSTATION_INEQNAME_TIME);
                    pageResult=dao.selectAllByCache(pageRequest,RedisCacheKey.ZHKYSTATION_INEQNAME_DATA);
                }else{
                    setSyncTime(RedisCacheKey.ZHKYSTATION_INEQNAME_TIME);
                    return pageResult;
                }
            }
        }
        return pageResult;
    }
    
}
