package com.yonyou.iuap.project.job;

import com.yonyou.iuap.project.service.StationService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 *定时分析
 * Created by XiongYi on 2018/11/26.
 *
 */
public class StationJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        WebApplicationContext Context = ContextLoader.getCurrentWebApplicationContext();
        final StationService stationService= (StationService) Context.getBean("stationService");

        stationService.stationJob();
        stationService.stationOnlyJob();
        stationService.stationRequiredJob();

    }
}
