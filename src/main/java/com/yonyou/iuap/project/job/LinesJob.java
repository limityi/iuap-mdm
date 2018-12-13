package com.yonyou.iuap.project.job;

import com.yonyou.iuap.project.service.LinesService;
import com.yonyou.iuap.project.service.StationService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 *定时分析
 * Created by zhugaofeng on 2018/12/7.
 *
 */
public class LinesJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        WebApplicationContext Context = ContextLoader.getCurrentWebApplicationContext();        
        final LinesService linesService=(LinesService) Context.getBean("linesService");
        
        linesService.linesJob();
        linesService.linesOnlyJob();
        linesService.linesRequiredJob();
            
    }
}
