package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.project.entity.MonitorLog;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 监控mapper接口
 * Created by XiongYi on 2018/12/05.
 */
@MyBatisRepository
public interface MonitorRepository {

    /**
     * 查询监控主表数据
     * @param dataMap
     * @return
     */
    Map<String,BigDecimal> selectMonitorData(Map<String,String> dataMap);

    /**
     * 查询监控日志详细数据
     * @param dataMap
     * @return
     */
    List<MonitorLog> selectMonitorLogData(Map<String,String> dataMap);

}
