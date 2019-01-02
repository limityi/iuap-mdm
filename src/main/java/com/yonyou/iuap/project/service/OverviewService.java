package com.yonyou.iuap.project.service;

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
     * @Description 数据存量计总
     * @author binbin
     * @date 2018/12/26 17:41
     */
    Map<String, Object> getMdmDataCount(String tableName);

    /**
     * @Description 数据重复计总
     * @author binbin
     * @date 2018/12/27 12:03
     */
    Map<String, Object> getMdmDataRepeatCount(String tableName);

}
