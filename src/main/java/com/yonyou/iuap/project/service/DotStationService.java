package com.yonyou.iuap.project.service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

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
import com.yonyou.iuap.project.entity.BusLine;
import com.yonyou.iuap.project.entity.Dot;
import com.yonyou.iuap.project.entity.DotStation;
import com.yonyou.iuap.project.entity.Station;
import com.yonyou.iuap.project.repository.DotStationDao;
import com.yonyou.iuap.project.repository.DotStationRepository;
import com.yonyou.iuap.project.util.SimilarityMatch;

@Service
public class DotStationService {

	@Autowired
	private DotStationDao dao;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private DotStationRepository dotstationRepository;
	
	private Gson gson =new Gson();
	
	/**
     * 获取同步时间
     * @param fieldName
     * @return String
     */
    public String getSyncTime(String fieldName){
        return RedisUtil.getSyncTime(redisTemplate,RedisCacheKey.DOTSTATION_TIME,fieldName);
    }
    
    /**
     * 设置同步时间
     * @param fieldName
     */
    private void setSyncTime(String fieldName){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RedisUtil.setSyncTime(redisTemplate,RedisCacheKey.DOTSTATION_TIME,fieldName, format.format(new Date()));
    }
            
    
    /**
	 * 查询所有数据
	 * @param pageRequest
	 * @param searchParams
	 * @return
	 */
	public Page<DotStation> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {

		Map<String, Object> searchMap = searchParams.getSearchMap();

		String inputStr = String.valueOf(searchMap.get("searchParam"));
		if (!inputStr.isEmpty()) {
			try {
				String con = URLDecoder.decode(inputStr, "UTF-8");
				searchMap.put("searchParam", con);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		// 查询缓存数据
		Page<DotStation> pageResult;
		//Page<Dot> pageResult;

		boolean updateOperation = Boolean.parseBoolean(searchMap.get("updateOperation").toString());
		if (updateOperation) {
			// 从数据库查询全部数据
			// 查询网上飞数据库数据量
			int size = dotstationRepository.countAll();
			// 定义新的分页数据，用来查询全部
			PageRequest pageRequestTemp = new PageRequest(0, size);			
			// 查询网上飞全部结果
			//pageResult = dao.selectAllByPageDot(pageRequestTemp);
			// 相似度比较
			pageResult = similarityMatch();
			// 比较完之后更新对比同步时间
			setSyncTime(RedisCacheKey.DOTSTATION_INEQNAME_TIME);
			// 比较完之后再从缓存取值
			pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.DOTSTATION_INEQNAME_DATA);
		} else {
			// 查询缓存数据
			pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.DOTSTATION_INEQNAME_DATA);

			// 判断缓存是否有值
			if ((!pageResult.getContent().isEmpty()) && pageResult.getContent().size() > 0) {
				return pageResult;
			} else {
				// 从数据库查询全部数据
				// 查询数据库数据量
				int size = dotstationRepository.countAll();
				// 定义新的分页数据,用来查询全部
				PageRequest pageRequestTemp = new PageRequest(0, size);
				// 查询全部结果
				//pageResult = dao.selectAllByPageDot(pageRequestTemp);
				// 相似度比较
				similarityMatch();
				// 比较完之后更新对比同步时间
				setSyncTime(RedisCacheKey.DOTSTATION_INEQNAME_TIME);
				// 比较完之后再从缓存取值
				pageResult = dao.selectAllByCache(pageRequest, RedisCacheKey.DOTSTATION_INEQNAME_DATA);
			}
		}
		return pageResult;
	}
	
	private Page<DotStation> similarityMatch() {
		//Map<String, Object> searchMap =new HashMap<>();
		// 匹配之前，先删除redis数据
		redisTemplate.del(RedisCacheKey.DOTSTATION_INEQNAME_DATA);
		// 处理数据，相似度检查
		// 根据查询的参数，看是哪个字段需要检查相似度
		// 循环的list
		//List<Dot> dotList = pageResult.getContent();		
		// 匹配的list
		//CopyOnWriteArrayList<Station> stationMatch = new CopyOnWriteArrayList<>(pageResult.getContent());		
		List<Dot> dotList = dotstationRepository.selectAllData();
		List<Station> stationList = dotstationRepository.selectAllData1();
				
		// 结果的list
		List<DotStation> dotResult = new ArrayList<>();

		for (Dot dot : dotList) {
			String field1=dot.getName();
			String code1=dot.getCode();
			
			for (Station station : stationList) {
				String field2=station.getName();
				String code2=station.getCode();
				double similarity = SimilarityMatch.getSimilarity(field1, field2);
				// 大于80%小于100%
				if(similarity > 0.9 && similarity < 1.0){
				String similarityS=String.valueOf(similarity);
				DotStation dotStation=new DotStation(similarityS,code1,field1,code2,field2);
				dotStation.setSimilarity(dobleFormat(similarity));
				dotResult.add(dotStation);
				redisTemplate.rpush(RedisCacheKey.DOTSTATION_INEQNAME_DATA, gson.toJson(dotStation));

				}
			}
			
		}
		Page<DotStation> resultPage=new PageImpl<>(dotResult);
		return resultPage;																								
		
	}
	
	
	/**
	 * 将double转化为百分数返回
	 * 
	 * @param number
	 * @return
	 */
	private String dobleFormat(double number) {
		NumberFormat num = NumberFormat.getPercentInstance();
		num.setMinimumFractionDigits(2);
		return num.format(number);
	}
	
	/**
     * 从缓存中取所有数据导出
     * @return
     */
    public Map<String,List<String>> selectAllCacheForExcel(){
        Map<String,List<String>> resultMap=new HashMap<>();
        //取相似度匹配结果
        int compareLength=redisTemplate.llen(RedisCacheKey.DOTSTATION_INEQNAME_DATA).intValue();

        if(compareLength>0){
            List<String> compareList=redisTemplate.lrange(RedisCacheKey.DOTSTATION_INEQNAME_DATA,0,compareLength);
            resultMap.put("compareData",compareList);
        }else{
            resultMap.put("compareData",new ArrayList<String>());
        }

        //取唯一性校验的数据
        /*int onlyLength=redisTemplate.llen(RedisCacheKey.DOTSTATION_ONLY_DATA).intValue();
        if(onlyLength>0){
            List<String> onlyData=redisTemplate.lrange(RedisCacheKey.DOTSTATION_ONLY_DATA,0,onlyLength);
            resultMap.put("onlyData",onlyData);
        }else{
            resultMap.put("onlyData",new ArrayList<String>());
        }*/

        //取必填校验的数据
        /*int requiredLength=redisTemplate.llen(RedisCacheKey.BUS_REQUIRED_DATA).intValue();
        if(requiredLength>0){
            List<String> requiredData=redisTemplate.lrange(RedisCacheKey.BUS_REQUIRED_DATA,0,requiredLength);
            resultMap.put("requiredData",requiredData);
        }else{
            resultMap.put("requiredData",new ArrayList<String>());
        }*/

        return resultMap;
    }
}
