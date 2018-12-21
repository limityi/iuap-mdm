package com.yonyou.iuap.project.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.yonyou.iuap.project.service.BusService;

/**
 *定时分析
 * Created by zhugaofeng on 2018/12/19.
 *
 */
public class BusJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		WebApplicationContext Context = ContextLoader.getCurrentWebApplicationContext();        
        final BusService busService=(BusService) Context.getBean("busService");
        
        busService.BusJob();
        busService.BusOnlyJob();
        busService.BusRequiredJob();
		
	}

}
