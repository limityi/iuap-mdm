package com.yonyou.iuap.project.repository;

import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.SQLHelper;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;
import com.yonyou.iuap.project.entity.Monitor;
import com.yonyou.iuap.project.entity.MonitorLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Repository
public class MonitorDao {

    @Autowired
    private MetadataDAO dao;

    @Autowired
    private MonitorRepository repository;

	@Transactional
    public Monitor save(Monitor entity) {
    	if(entity.getId() ==null ){
    		 entity.setStatus(VOStatus.NEW);
    		 entity.setId(UUID.randomUUID().toString());
             entity.setDr(0);// 未删除标识         
    	}else{
    		entity.setStatus(VOStatus.UPDATED);
    	}
    	
		if(entity.getId_log()!=null && entity.getId_log().size()>0){
    		for(MonitorLog child : entity.getId_log() ){
    			if(child.getId() == null ){
    				child.setStatus(VOStatus.NEW);
    				child.setDr(entity.getDr());
    			}else{
    				child.setStatus(VOStatus.UPDATED);
    			}
    		}
    		dao.save(entity, entity.getId_log().toArray(new MonitorLog[entity.getId_log().size()]));
    	}else{
    		dao.save(entity);
    	}
    	return entity ;
    }


    public int delete(Monitor entity) throws Exception {

        if (null == entity) {
            return 0;
        }
        dao.remove(entity);
        return 1;
    }

    public Page<Monitor> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) throws DAOException {

	    //默认查询站场数据
	    searchParams.put("dataType","station");

	    //返回接收类
        Monitor monitor=new Monitor();

        //取时间并格式化
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //构建查询参数-日期
        Date endDate=new Date();
        Calendar calendar=Calendar.getInstance();
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

        //增加默认值
        String dataType=searchParams.get("dataType").toString();

        //日统计返回值
        Map<String,BigDecimal> result;

        if(dataType!=null&&dataType.equals("station")){

            monitor.setSystem_name("数据资源管理平台");
            monitor.setData_type("station");
            monitor.setIntegration_mode("0");
            monitor.setIntegration_type("0");
            monitor.setIntegration_strategy("0");
            monitor.setIntegration_cycle("1天/次");

            //查询
            Map<String,String> parmMap=new HashMap<>();

            parmMap.put("data_type","station");
            parmMap.put("end_date", format.format(endDate));
            parmMap.put("start_date",format.format(dayDate));

            result=repository.selectMonitorData(parmMap);

            monitor.setTotal_number_day(result.get("SUCCESS_NUMBER").intValue()+result.get("FAIL_NUMBER").intValue());
            monitor.setSuccess_number_day(result.get("SUCCESS_NUMBER").intValue());
            monitor.setFail_number_day(result.get("FAIL_NUMBER").intValue());

            parmMap.put("start_date",format.format(weekDate));
            result=repository.selectMonitorData(parmMap);

            monitor.setTotal_number_week(result.get("SUCCESS_NUMBER").intValue()+result.get("FAIL_NUMBER").intValue());
            monitor.setSuccess_number_week(result.get("SUCCESS_NUMBER").intValue());
            monitor.setFail_number_week(result.get("FAIL_NUMBER").intValue());

            parmMap.put("start_date",format.format(monthDate));
            result=repository.selectMonitorData(parmMap);

            monitor.setTotal_number_month(result.get("SUCCESS_NUMBER").intValue()+result.get("FAIL_NUMBER").intValue());
            monitor.setSuccess_number_month(result.get("SUCCESS_NUMBER").intValue());
            monitor.setFail_number_month(result.get("FAIL_NUMBER").intValue());
        }

        List<Monitor> monitors=new ArrayList<>();

        monitors.add(monitor);

        return new PageImpl<>(monitors, pageRequest, 1);


        //String dataType="station";


        /*String sql = " select * from mdm_monitor"; // user_name = ?
        SQLParameter sqlparam = new SQLParameter();

     	if (null != searchParams && !searchParams.isEmpty()) {
            sql = sql + " where ";
            for (String key : searchParams.keySet()) {
                sql = sql + FastBeanHelper.getColumn(Monitor.class, key) + " like ? AND ";
                sqlparam.addParam("%" + searchParams.get(key) + "%");
            }
            sql = sql.substring(0, sql.length() - 4);
        }
        return dao.queryPage(sql, sqlparam, pageRequest, Monitor.class);*/
    }

    public void batchDelete(List<Monitor> list) throws DAOException {

        dao.remove(list);
    }

    public void batchDelChild(List<Monitor> list) throws DAOException {
        SQLParameter sqlparam = new SQLParameter();
		String deleteSQL = SQLHelper.createDeleteIn("mdm_monitorlog", "fk_id_log", list.size());
        for (Monitor item : list) {
            sqlparam.addParam(item.getId());
        }
        dao.update(deleteSQL, sqlparam);
    }     

}
