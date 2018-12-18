package com.yonyou.iuap.project.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.yonyou.iuap.project.service.BusLineService;

/**
 *定时分析
 * Created by zhugaofeng on 2018/12/14.
 *
 */
public class BusLineJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		WebApplicationContext Context = ContextLoader.getCurrentWebApplicationContext();        
        final BusLineService buslineService=(BusLineService) Context.getBean("buslineService");
        
        buslineService.buslineJob();
        buslineService.buslineOnlyJob();
        buslineService.buslineRequiredJob();

	}

}
