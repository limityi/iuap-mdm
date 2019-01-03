package com.yonyou.iuap.project.controller;

import com.yonyou.iuap.example.web.BaseController;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.project.excel.WriteStationExcel;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.entity.Station;
import com.yonyou.iuap.project.service.StationService;
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
public class StationController extends BaseController implements ServletContextAware {
    
	@Autowired
	private StationService service;

    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
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

        result.put("stationCompareData",service.selectAllByPage(pageRequest, searchParams));
        result.put("stationCompareTime",service.getSyncTime(RedisCacheKey.STASION_COMPARE_TIME));
        result.put("stationOnlyData",service.selectOnlyValidateByPage(pageRequestOnly,searchParams));
        result.put("stationOnlyTime",service.getSyncTime(RedisCacheKey.STASION_ONLY_TIME));
        result.put("stationRequiredData",service.selectRequiredData(pageRequestRequired,requiredColumn,searchParams));
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

    /**
     * 导出成excel文件
     */
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletResponse response) {

        // 创建输出对象
        WriteStationExcel writeExcel = new WriteStationExcel();
        ServletOutputStream os = null;

        try {
            //查询出全部数据
            Map<String,List<String>> stationMap=service.selectAllCacheForExcel();
            // 把数据写入到excel中，放到应用的临时路径下，再把这个文件传到浏览器
            String temppath = writeExcel.createExcelXlsx(stationMap,this.servletContext.getRealPath("/") + System.currentTimeMillis() + ".xlsx");

            os = response.getOutputStream();
            byte buffer[] = new byte[1024];
            File fileLoad = new File(temppath);

            // 清空输出流
            response.reset();

            // xlsx的ContentType
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            // 设置这个内容，表示下载这个文件
            response.addHeader("Content-Disposition", "attachment; filename =" + URLEncoder.encode("站场数据质量报告.xlsx", "UTF-8"));

            // 设置文件长度
            response.setContentLength((int) fileLoad.length());

            FileInputStream fis = new FileInputStream(fileLoad);

            int len;
            while ((len = fis.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            fis.close();
            //文件传输完毕之后，把应用下的临时文件删除掉，要在输入流关闭之后删除，否则删不掉
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
