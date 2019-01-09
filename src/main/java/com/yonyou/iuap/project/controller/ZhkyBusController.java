package com.yonyou.iuap.project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import com.yonyou.iuap.example.web.BaseController;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.service.ZhkyBusService;

@RestController
@RequestMapping(value = "/ZhkyBus")
public class ZhkyBusController extends BaseController implements ServletContextAware{
	
	@Autowired
	private ZhkyBusService service;
	
	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext=servletContext;	
	}
	
	/**
     * 查询分页数据
     * 
     * @param pageRequest
     * @param searchParams
     * @return
     */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams) {
		//返回值为Map
		Map<String, Object> result=new HashMap<>();
		
		//取分页数据
		Map<String, Object> searchMap=searchParams.getSearchMap();
		int pageIndexOnly=Integer.valueOf(searchMap.get("pageIndexOnly").toString());
        int pageSizeOnly=Integer.valueOf(searchMap.get("pageSizeOnly").toString());
        searchMap.remove("pageIndexOnly");
        searchMap.remove("pageSizeOnly");
        
        /*int pageIndexRequired=Integer.valueOf(searchMap.get("pageIndexRequired").toString());
        int pageSizeRequired=Integer.valueOf(searchMap.get("pageSizeRequired").toString());
        searchMap.remove("pageIndexRequired");
        searchMap.remove("pageSizeRequired");*/
        
        searchParams.setSearchMap(searchMap);
        PageRequest pageRequestOnly=new PageRequest(pageIndexOnly,pageSizeOnly);
        //PageRequest pageRequestRequired=new PageRequest(pageIndexRequired,pageSizeRequired);
		
        List<String> requiredColumn=new ArrayList<>();
		
        result.put("zhkybusIneqNameData",service.selectIneqNameByPage(pageRequest, searchParams));
        result.put("zhkybusIneqNameTime",service.getSyncTime(RedisCacheKey.ZHKYBUS_INEQNAME_TIME));
        result.put("zhkybusOnlyData",service.selectOnlyValidateByPage(pageRequestOnly,searchParams));
        result.put("zhkybusOnlyTime",service.getSyncTime(RedisCacheKey.ZHKYBUS_ONLY_TIME));
        //result.put("zhkystationIneqNameData",service.selectIneqNameByPage(pageRequestRequired, searchParams));
        //result.put("zhkystationIneqNameTime",service.getSyncTime(RedisCacheKey.ZHKYSTATION_INEQNAME_TIME));
        return buildSuccess(result);
    }

}
