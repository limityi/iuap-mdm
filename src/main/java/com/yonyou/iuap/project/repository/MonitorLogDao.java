package com.yonyou.iuap.project.repository;

import com.google.gson.Gson;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.project.cache.RedisCacheKey;
import com.yonyou.iuap.project.cache.RedisTemplate;
import com.yonyou.iuap.project.entity.MonitorLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class MonitorLogDao {

    @Autowired
    private MetadataDAO dao;

    @Autowired
    private MonitorRepository repository;

    @Autowired
    private RedisTemplate redisTemplate;

    public MonitorLog findById(String id) throws DAOException {

        String sql = "select * from mdm_monitorlog  where dr='0' and id=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(id);
        List<MonitorLog> list = dao.queryByClause(MonitorLog.class, sql, sqlparam);
        return list == null || list.isEmpty() ? null : list.get(0);
    }


    public void delete(MonitorLog entity) {

        if (null != entity) {
            dao.remove(entity);
        }
    }

    /**
     * 分页查询监控子表数据
     * @param pageRequest
     * @param searchParams
     * @return
     * @throws DAOException
     */
    public Page<MonitorLog> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams)
            throws DAOException {
        String dataType=String.valueOf(searchParams.get("dataType"));
        String type=String.valueOf(searchParams.get("type[value]"));

        //先从缓存里取数据
        Page<MonitorLog> resultPage=this.selectAllByCache(pageRequest,RedisCacheKey.MONITOR_LOG+"-"+type+"-"+dataType);

        if(resultPage.getContent().isEmpty()||resultPage.getContent().size()==0){
            Gson gson=new Gson();

            //取查询参数
            Map<String,String> parm=new HashMap<>();

            int dataNumber=Integer.valueOf(String.valueOf(searchParams.get("dataNumber[value]")));

            //构建查询时间
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

            //添加查询类别
            parm.put("dataType",type);
            //添加当前日期的终止日期
            parm.put("endDate",format.format(endDate));

            //添加开始时间和状态
            switch (dataType){
                case "total_number_day":
                    parm.put("startDate",format.format(dayDate));
                    break;
                case "success_number_day":
                    parm.put("startDate",format.format(dayDate));
                    parm.put("status","end");
                    break;
                case "fail_number_day":
                    parm.put("startDate",format.format(dayDate));
                    parm.put("status","stop");
                    break;
                case "total_number_week":
                    parm.put("startDate",format.format(weekDate));
                    break;
                case "success_number_week":
                    parm.put("startDate",format.format(weekDate));
                    parm.put("status","end");
                    break;
                case "fail_number_week":
                    parm.put("startDate",format.format(weekDate));
                    parm.put("status","stop");
                    break;
                case "total_number_month":
                    parm.put("startDate",format.format(monthDate));
                    break;
                case "success_number_month":
                    parm.put("startDate",format.format(monthDate));
                    parm.put("status","end");
                    break;
                case "fail_number_month":
                    parm.put("startDate",format.format(monthDate));
                    parm.put("status","stop");
                    break;
            }

            List<MonitorLog> monitorLogs=repository.selectMonitorLogData(parm);

            if(!monitorLogs.isEmpty()&&monitorLogs.size()>0){
                for(MonitorLog monitorLog : monitorLogs){
                    redisTemplate.rpush(RedisCacheKey.MONITOR_LOG+"-"+type+"-"+dataType,gson.toJson(monitorLog));
                }
                redisTemplate.expire(RedisCacheKey.MONITOR_LOG+"-"+type+"-"+dataType,180);
            }
            return this.selectAllByCache(pageRequest,RedisCacheKey.MONITOR_LOG+"-"+type+"-"+dataType);
        }

        return resultPage;
    }


    /**
     * 分页查询redis缓存数据
     * @param pageRequest
     * @return
     */
    public Page<MonitorLog> selectAllByCache(PageRequest pageRequest, String dataKey){

        Gson gson=new Gson();
        //分页查询redis
        List<String> resultCache=redisTemplate.lrange(dataKey,pageRequest.getPageNumber()*pageRequest.getPageSize(),(pageRequest.getPageNumber()+1)*pageRequest.getPageSize()-1);

        //查询缓存中数据的长度
        long resultCacheSize=redisTemplate.llen(dataKey);

        //返回结果
        List<MonitorLog> resultList= new ArrayList<>();

        //如果有数据,转化数据
        if(resultCache!=null&&resultCache.size()>0){
            for(int i = 0; i<resultCache.size(); i++){
                resultList.add(i, gson.fromJson(resultCache.get(i),MonitorLog.class) );
            }
        }
        return new PageImpl<>(resultList,pageRequest,resultCacheSize);
    }

}
