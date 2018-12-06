package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

import java.math.BigDecimal;
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

}
