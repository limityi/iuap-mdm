package com.yonyou.iuap.project.controller;

import com.yonyou.iuap.project.dt.DTEnum;
import com.yonyou.iuap.project.service.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

        List<Map<String, Object>> totals = new ArrayList<>();
        Map<String, Object> one = new HashMap<>();
        one.put("id", 0);
        one.put("name", "接收数据");
        one.put("today", "1230");
        one.put("yoyear", "5674");
        totals.add(one);

        Map<String, Object> two = new HashMap<>();
        two.put("id", 1);
        two.put("name", "共享数据");
        two.put("today", "1283");
        two.put("yoyear", "1567");
        totals.add(two);

        Map<String, Object> three = new HashMap<>();
        three.put("id", 2);
        three.put("name", "重复数据");
        three.put("today", "1398");
        three.put("yoyear", "6764");
        totals.add(three);

        Map<String, Object> four = new HashMap<>();
        four.put("id", 3);
        four.put("name", "相似数据");
        four.put("today", "1200");
        four.put("yoyear", "5600");
        totals.add(four);

        data.put("total", totals);


        List<Map<String, Object>> item = new ArrayList<>();
        item.add(overviewService.getMdmDataCount(DTEnum.MdmTable.MDM_STATION.getId()));
        item.add(overviewService.getMdmDataCount(DTEnum.MdmTable.MDM_LINE.getId()));
        item.add(overviewService.getMdmDataCount(DTEnum.MdmTable.MDM_BUS.getId()));
        item.add(overviewService.getMdmDataCount(DTEnum.MdmTable.MDM_MERCHANTS.getId()));

        List<Map<String, Object>> categories = new ArrayList<>();
        Map<String, Object> onec = new HashMap<>();
        onec.put("id", 0);
        onec.put("name", "数据存量分析");
        onec.put("data", item);
        categories.add(onec);

        Map<String, Object> twoc = new HashMap<>();
        twoc.put("id", 1);
        twoc.put("name", "重复数据占比");
        twoc.put("data", item);
        categories.add(twoc);

        Map<String, Object> threec = new HashMap<>();
        threec.put("id", 2);
        threec.put("name", "相似数据占比");
        threec.put("data", item);
        categories.add(threec);
        data.put("category", categories);


        List<Map<String, Object>> slist = overviewService.getMdmSysregister(null);
        for (int i = 0; i < slist.size(); i++) {
            slist.get(i).put("value", i * 10 + 50);
        }

        List<Map<String, Object>> systems = new ArrayList<>();
        Map<String, Object> ones = new HashMap<>();
        ones.put("id", 0);
        ones.put("name", "系统接收情况");
        ones.put("data", slist);
        ones.put("selected", item);
        systems.add(ones);

        Map<String, Object> twos = new HashMap<>();
        twos.put("id", 1);
        twos.put("name", "系统共享情况");
        twos.put("data", slist);
        twos.put("selected", item);
        systems.add(twos);

        Map<String, Object> threes = new HashMap<>();
        threes.put("id", 3);
        threes.put("name", "系统接口监控");
        threes.put("data", slist);
        threes.put("selected", item);
        systems.add(threes);

        data.put("system", systems);

        return data;
    }

    @RequestMapping(value = "/sysdata")
    public @ResponseBody
    Map<String, Object> sysdata(HttpServletRequest request, String mode, String systemId) {
        List<Map<String, Object>> item = new ArrayList<>();
        Map<String, Object> station = new HashMap<>();
        station.put("name", "站场");
        station.put("value", "500");
        item.add(station);

        Map<String, Object> line = new HashMap<>();
        line.put("name", "线路");
        line.put("value", "120");
        item.add(line);

        Map<String, Object> bus = new HashMap<>();
        bus.put("name", "车辆");
        bus.put("value", "150");
        item.add(bus);

        Map<String, Object> guest = new HashMap<>();
        guest.put("name", "客商");
        guest.put("value", "250");
        item.add(guest);

        Map<String, Object> onec = new HashMap<>();
        onec.put("name", mode + systemId);
        onec.put("data", item);

        return onec;
    }


}
