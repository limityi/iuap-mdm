package com.yonyou.iuap.project.job;

import com.yonyou.iuap.project.service.OverviewService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class DataStatisticsJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        WebApplicationContext Context = ContextLoader.getCurrentWebApplicationContext();
        final OverviewService overviewService = (OverviewService) Context.getBean("overviewService");

        overviewService.updateMdmDataCount();

    }
}
