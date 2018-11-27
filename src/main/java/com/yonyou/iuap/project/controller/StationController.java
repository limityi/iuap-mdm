package com.yonyou.iuap.project.controller;

import com.yonyou.iuap.example.web.BaseController;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.entity.Station;
import com.yonyou.iuap.project.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: 站场
 * </p>
 * <p>
 * Description: 站场卡片表示例的controller层
 * </p>
 * @author xiongyi
 * @date 2018年11月27日10:01:59
 */
@RestController
@RequestMapping(value = "/Station")
public class StationController extends BaseController {
    
	@Autowired
	private StationService service;
    

    /**
     * 查询分页数据
     * 
     * @param pageRequest
     * @param searchParams
     * @return
     */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams) {
	    //返回值转为Map
        Map<String,Object> result=new HashMap<>();

        //取分页数据
        Map<String, Object> searchMap=searchParams.getSearchMap();

        int pageIndexOnly=Integer.valueOf(searchMap.get("pageIndexOnly").toString());
        int pageSizeOnly=Integer.valueOf(searchMap.get("pageSizeOnly").toString());
        searchMap.remove("pageIndexOnly");
        searchMap.remove("pageSizeOnly");

        int pageIndexRequired=Integer.valueOf(searchMap.get("pageIndexRequired").toString());
        int pageSizeRequired=Integer.valueOf(searchMap.get("pageSizeRequired").toString());
        searchMap.remove("pageIndexRequired");
        searchMap.remove("pageSizeRequired");

        searchParams.setSearchMap(searchMap);
        PageRequest pageRequestOnly=new PageRequest(pageIndexOnly,pageSizeOnly);
        PageRequest pageRequestRequired=new PageRequest(pageIndexRequired,pageSizeRequired);

        List<String> requiredColumn=new ArrayList<>();
        requiredColumn.add("code");

        result.put("stationCompareData",service.selectAllByPage(pageRequest, searchParams));
        result.put("stationCompareTime",service.getSyncTime(RedisCacheKey.STASION_COMPARE_TIME));
        result.put("stationOnlyData",service.selectOnlyValidateByPage(pageRequestOnly,new HashMap<String, Object>()));
        result.put("stationOnlyTime",service.getSyncTime(RedisCacheKey.STASION_ONLY_TIME));
        result.put("stationRequiredData",service.selectRequiredData(pageRequestRequired,requiredColumn));
        result.put("stationRequiredTime",service.getSyncTime(RedisCacheKey.STASION_REQUIRED_TIME));
        return buildSuccess(result);
    }

    /**
     * 保存数据
     * 
     * @param list
     * @return
     */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<Station> list) {
    	service.save(list);
        return buildSuccess();
    }

    /**
     * 删除数据
     * 
     * @param list
     * @return
     */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
    public @ResponseBody Object del(@RequestBody List<Station> list) {
    	service.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }
    
}
