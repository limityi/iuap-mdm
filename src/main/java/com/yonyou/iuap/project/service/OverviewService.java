package com.yonyou.iuap.project.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据总览Service接口
 */
public interface OverviewService {

    /**
     * @Description 获取mdm注册系统
     * @author binbin
     * @date 2018/12/26 17:10
     */
    List<Map<String, Object>> getMdmSysregister(String code);


    /**
     * @Description 获取主数据根目录
     * @author binbin
     * @date 2019/1/17 15:19
     */
    List<Map<String, Object>> getMdmRootCategory();

    /**
     * @Description 获取主数据种类
     * @author binbin
     * @date 2019/1/17 15:35
     */
    List<Map<String, Object>> getMdmCategory(String code);

    /**
     * @Description 根据表名获取主数据存量计总
     * @author binbin
     * @date 2018/12/26 17:41
     */
    Long getMdmDataCount(String tableName);


    /**
     * @Description 根据表名和时间获取主数据日志计总
     * @author binbin
     * @date 2019/1/23 18:11
     */
    Long getMdmLogDataCount(String tableName, Date statDate);

    /**
     * @Description 获取根据系统、表名和统计类型获取统计数据
     * @author binbin
     * @date 2019/1/23 16:56
     */
    Map<String, Object> getStatisticsCount(String sys, String tableName, Integer statType);

    /**
     * @Description 获取统计数据计总
     * @author binbin
     * @date 2019/1/22 16:04
     */
    Long getStatisticsTotal(Integer type, Date statDate, String sys);

    /**
     * @Description 获取计总数据
     * @author binbin
     * @date 2019/1/21 10:35
     */
    List<Map<String, Object>> getDataTotals();

    /**
     * @Description 获取分类数据统计
     * @author binbin
     * @date 2019/1/21 10:36
     */
    List<Map<String, Object>> getDataCategories();

    /**
     * @Description 根据分类标志获取分类数据统计
     * @author binbin
     * @date 2019/1/21 11:22
     */
    List<Map<String, Object>> getCategoryData(Integer mode);

    /**
     * @Description 按系统统计数据
     * @author binbin
     * @date 2019/1/21 10:37
     */
    List<Map<String, Object>> getDataSystems();

    /**
     * @Description 根据统计类型获取系统计总
     * @author binbin
     * @date 2019/1/23 11:03
     */
    List<Map<String, Object>> getSysListMap(Integer mode);


    /**
     * @Description 根据系统标志获取系统数据统计
     * @author binbin
     * @date 2019/1/21 10:47
     */
    List<Map<String, Object>> getSysCategoryData(Integer mode, String systemId);

    /**
     * @Description 保存统计数据
     * @author binbin
     * @date 2019/1/21 16:47
     */
    void updateMdmDataStatistics(String sys, String tableId, String tableName, Integer type, Long value);

    /**
     * @Description 保存存量数据计总
     * @author binbin
     * @date 2019/1/23 14:54
     */
    void updateMdmDataCount();

}
