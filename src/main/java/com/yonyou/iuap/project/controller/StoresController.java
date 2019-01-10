package com.yonyou.iuap.project.controller;

import com.yonyou.iuap.example.web.BaseController;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.entity.Stores;
import com.yonyou.iuap.project.excel.WriteStoresExcel;
import com.yonyou.iuap.project.service.StoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 便利店
 *
 * @author binbin
 * @date 2018/12/18 16:31
 */
@RestController
@RequestMapping(value = "/stores")
public class StoresController extends BaseController implements ServletContextAware {

    @Autowired
    private StoresService service;

    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * @Description 获取便利店列表
     * @author binbin
     * @date 2018/12/17 15:03
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    Object page(PageRequest pageRequest, SearchParams searchParams) {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> searchMap = searchParams.getSearchMap();

        int pageIndexOnly = Integer.valueOf(searchMap.get("pageIndexOnly").toString());
        int pageSizeOnly = Integer.valueOf(searchMap.get("pageSizeOnly").toString());
        searchMap.remove("pageIndexOnly");
        searchMap.remove("pageSizeOnly");

        int pageIndexRequired = Integer.valueOf(searchMap.get("pageIndexRequired").toString());
        int pageSizeRequired = Integer.valueOf(searchMap.get("pageSizeRequired").toString());
        searchMap.remove("pageIndexRequired");
        searchMap.remove("pageSizeRequired");

        searchParams.setSearchMap(searchMap);
        PageRequest pageRequestOnly = new PageRequest(pageIndexOnly, pageSizeOnly);
        PageRequest pageRequestRequired = new PageRequest(pageIndexRequired, pageSizeRequired);

        List<String> requiredColumn = new ArrayList<>();

        result.put("stationCompareData", service.selectAllByPage(pageRequest, searchParams));
        result.put("stationCompareTime", service.getSyncTime(RedisCacheKey.STORES_COMPARE_TIME));

        result.put("stationOnlyData", service.selectOnlyValidateByPage(pageRequestOnly, searchParams));
        result.put("stationOnlyTime", service.getSyncTime(RedisCacheKey.STORES_ONLY_TIME));

        result.put("stationRequiredData", service.selectRequiredData(pageRequestRequired, requiredColumn, searchParams));
        result.put("stationRequiredTime", service.getSyncTime(RedisCacheKey.STORES_REQUIRED_TIME));
        return buildSuccess(result);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    Object save(@RequestBody List<Stores> list) {
        service.save(list);
        return buildSuccess();
    }


    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public @ResponseBody
    Object del(@RequestBody List<Stores> list) {
        service.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }

    /**
     * 去除比较数据
     *
     * @param code
     * @return
     */
	@RequestMapping(value = "/removeData", method = RequestMethod.POST)
    public @ResponseBody Object removeData(@RequestBody String code) {
    	service.removeData(code);
        return buildSuccess();
    }

    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletResponse response) {
        WriteStoresExcel writeExcel = new WriteStoresExcel();
        ServletOutputStream os = null;
        try {
            Map<String, List<String>> stationMap = service.selectAllCacheForExcel();
            String temppath = writeExcel.createExcelXlsx(stationMap, this.servletContext.getRealPath("/") + System.currentTimeMillis() + ".xlsx");

            os = response.getOutputStream();
            byte buffer[] = new byte[1024];
            File fileLoad = new File(temppath);

            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.addHeader("Content-Disposition", "attachment; filename =" + URLEncoder.encode("便利店数据质量报告.xlsx", "UTF-8"));
            response.setContentLength((int) fileLoad.length());
            FileInputStream fis = new FileInputStream(fileLoad);

            int len;
            while ((len = fis.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            fis.close();
            if (fileLoad.exists()) {
                fileLoad.delete();
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
