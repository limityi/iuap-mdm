package com.yonyou.iuap.project.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.iuap.project.entity.Monitor;
import com.yonyou.iuap.project.service.MonitorService;
import com.yonyou.iuap.example.web.BaseController;
import com.yonyou.iuap.example.entity.meta.EnumVo;
import com.yonyou.iuap.example.utils.EnumUtils;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;

@RestController
@RequestMapping(value = "/Monitor")
public class MonitorController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(MonitorController.class);

    @Autowired
    private MonitorService service;

    /**
     * 前端传入参数，查询数据，列表展示
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(PageRequest pageRequest, @FrontModelExchange(modelType = Monitor.class) SearchParams searchParams) {
        logger.debug("execute data search.");
        Page<Monitor> page = service.selectAllByPage(searchParams.getSearchMap(), pageRequest);
        return buildSuccess(page);
    }

    /**
     * 增加主子表数据保存，传入json数据
     * 
     * @param userAndUserJob
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestBody Monitor obj, HttpServletRequest request) {
        logger.debug("execute add operate.");
        Monitor u = service.save(obj) ;
        return super.buildSuccess(u);
    }


    /**
     * 更新主子表数据更新，传入json数据
     * 
     * @param userAndUserJob
     * @param request
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@RequestBody Monitor obj, HttpServletRequest request) {
        logger.debug("execute add operate.");
        Monitor u = service.save(obj) ;
        return super.buildSuccess(u); 
    }

    /** 批量删除实体类 */
    @RequestMapping(value = "/delBatch", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteBatch(@RequestBody List<Monitor> objs, HttpServletRequest request) throws Exception {
        logger.debug("execute del operate.");
        service.batchDeleteEntity(objs);
        return super.buildSuccess(objs);
    }
    
    
}
