package com.yonyou.iuap.project.service;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.project.entity.Monitor;
import com.yonyou.iuap.project.repository.MonitorDao;
import com.yonyou.iuap.project.repository.MonitorRepository;
import com.yonyou.iuap.project.util.SysConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MonitorService {
    private Logger logger = LoggerFactory.getLogger(MonitorService.class);

    @Autowired
    private MonitorDao dao;

    @Autowired
    private MonitorLogService childService;

    @Autowired
    private MonitorRepository repository;

    @Transactional
    public Monitor save(Monitor entity) {
        logger.debug("execute  Monitor save .");
        return dao.save(entity);
    }

    /**
     * 批量删除数据
     *
     * @param list
     */
    @Transactional
    public void batchDeleteEntity(List<Monitor> list) {
        this.batchDelChild(list);
        dao.batchDelete(list);
    }

    /**
     * 删除主表下面的子表数据
     *
     * @param list
     * @throws DAOException
     */
    private void batchDelChild(List<Monitor> list) throws DAOException {
        dao.batchDelChild(list);
    }

    /**
     * 根据传递的参数，进行分页查询
     */
    public Page<Monitor> selectAllByPage(Map<String, Object> searchParams, PageRequest pageRequest) {

        //取时间并格式化
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //构建查询参数-日期
        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        //取当前时间前一天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date dayDate = calendar.getTime();
        //取当前时间前一周
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date weekDate = calendar.getTime();
        //取当前时间前一月
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date monthDate = calendar.getTime();

        //统计返回值
        Map<String, BigDecimal> result;

        //返回前端数据
        List<Monitor> monitors = new ArrayList<>();

        List<String> dataTypes = new ArrayList<>();

        //筛选数据
        Object obj = searchParams.get("systemName");
        if (obj != null) {
            int length = 0;
            String systemName = obj.toString();
            for (Map.Entry entry : SysConst.monitorSystemName.entrySet()) {
                if (entry.getValue().equals(systemName)) {
                    dataTypes.add(entry.getKey().toString());
                }
            }
        } else {
            dataTypes = Arrays.asList(SysConst.DataType);
        }

        //取分页数据
        int start = pageRequest.getPageNumber() * pageRequest.getPageSize();
        int end = (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize() - 1;

        if (end >= dataTypes.size()) {
            end = dataTypes.size() - 1;
        }
        //循环取数
        for (int i = start; i <= end; i++) {

            Monitor monitor = new Monitor();

            String dataType = dataTypes.get(i);

            String tableName = dataType.toUpperCase() + "_LOG";

            monitor.setSystem_name(SysConst.monitorSystemName.get(dataType));
            monitor.setData_type(dataType);
            monitor.setIntegration_mode("0");
            monitor.setIntegration_type("0");
            monitor.setIntegration_strategy("0");
            monitor.setIntegration_cycle("1天/次");

            //查询
            Map<String, String> parmMap = new HashMap<>();

            parmMap.put("end_date", format.format(endDate));
            parmMap.put("start_date", format.format(dayDate));
            parmMap.put("table_name", tableName);

            result = repository.selectMonitorData(parmMap);

            monitor.setTotal_number_day(result.get("SUCCESS_NUMBER").intValue() + result.get("FAIL_NUMBER").intValue());
            monitor.setSuccess_number_day(result.get("SUCCESS_NUMBER").intValue());
            monitor.setFail_number_day(result.get("FAIL_NUMBER").intValue());

            parmMap.put("start_date", format.format(weekDate));
            result = repository.selectMonitorData(parmMap);

            monitor.setTotal_number_week(result.get("SUCCESS_NUMBER").intValue() + result.get("FAIL_NUMBER").intValue());
            monitor.setSuccess_number_week(result.get("SUCCESS_NUMBER").intValue());
            monitor.setFail_number_week(result.get("FAIL_NUMBER").intValue());

            parmMap.put("start_date", format.format(monthDate));
            result = repository.selectMonitorData(parmMap);

            monitor.setTotal_number_month(result.get("SUCCESS_NUMBER").intValue() + result.get("FAIL_NUMBER").intValue());
            monitor.setSuccess_number_month(result.get("SUCCESS_NUMBER").intValue());
            monitor.setFail_number_month(result.get("FAIL_NUMBER").intValue());

            monitors.add(monitor);
        }
        return new PageImpl<>(monitors, pageRequest, dataTypes.size());
    }
}
