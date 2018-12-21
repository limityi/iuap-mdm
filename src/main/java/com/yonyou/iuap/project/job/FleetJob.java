package com.yonyou.iuap.project.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.yonyou.iuap.project.service.FleetService;

/**
 *定时分析
 * Created by zhugaofeng on 2018/12/19.
 *
 */
public class FleetJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		WebApplicationContext Context = ContextLoader.getCurrentWebApplicationContext();        
        final FleetService fleetService=(FleetService) Context.getBean("fleetService");
        
        fleetService.fleetJob();
        fleetService.fleetOnlyJob();
        fleetService.fleetRequiredJob();
}
}
