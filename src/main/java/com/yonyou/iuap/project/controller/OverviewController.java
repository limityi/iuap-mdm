package com.yonyou.iuap.project.controller;

import com.yonyou.iuap.project.dt.DTEnum;
import com.yonyou.iuap.project.service.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据总览接口
 */
@RestController
@RequestMapping(value = "/overview")
public class OverviewController {

    @Autowired
    private OverviewService overviewService;

    /**
     * @Description 获取总览数据
     * @author binbin
     * @date 2018/12/25 17:03
     */
    @RequestMapping(value = "/data")
    public @ResponseBody
    Map<String, Object> data(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        data.put("total", overviewService.getDataTotals());
        data.put("category", overviewService.getDataCategories());
        data.put("system", overviewService.getDataSystems());
        return data;
    }

    @RequestMapping(value = "/sysdata")
    public @ResponseBody
    Map<String, Object> sysdata(HttpServletRequest request, Integer mode, String systemId) {
        switch (systemId) {
            case "0":
                systemId = DTEnum.MdmSys.NC65.getId();
                break;
            case "1":
                systemId = DTEnum.MdmSys.NYT.getId();
                break;
            case "2":
                systemId = DTEnum.MdmSys.RC.getId();
                break;
            case "3":
                systemId = DTEnum.MdmSys.XJKY.getId();
                break;
            case "4":
                systemId = DTEnum.MdmSys.ZHKY.getId();
                break;
            case "5":
                systemId = DTEnum.MdmSys.MDM.getId();
                break;
            default:
                systemId = DTEnum.MdmSys.MDM.getId();
                break;
        }

        List<Map<String, Object>> item = overviewService.getSysCategoryData(mode, systemId);
        Map<String, Object> onec = new HashMap<>();
        onec.put("name", mode + systemId);
        onec.put("data", item);
        return onec;
    }

}
