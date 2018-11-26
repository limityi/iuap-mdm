package com.yonyou.iuap.project.job;

import com.yonyou.iuap.project.service.StationService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *定时分析
 * Created by XiongYi on 2018/11/26.
 *
 */
public class StationJob implements Job {

    @Autowired
    private StationService stationService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        stationService.stationJob();
    }
}
