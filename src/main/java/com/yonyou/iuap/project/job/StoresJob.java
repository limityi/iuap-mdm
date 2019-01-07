package com.yonyou.iuap.project.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.yonyou.iuap.project.service.StoresService;

/**
 *定时分析
 * Created by zhugaofeng on 2019/1/3.
 *
 */
public class StoresJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		WebApplicationContext Context = ContextLoader.getCurrentWebApplicationContext();
		final StoresService storesService=(StoresService) Context.getBean("storesService");
		
		storesService.storesJob();
		storesService.storesOnlyJob();
		storesService.storesRequiredJob();
	}

}
