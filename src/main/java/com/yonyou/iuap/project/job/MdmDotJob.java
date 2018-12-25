package com.yonyou.iuap.project.job;

import com.yonyou.iuap.project.express.WsfSiteServicce;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 小件快运系统同步任务 同步网上飞网点主数据
 * Created by XiongYi on 2018/12/25.
 *
 */
public class MdmDotJob implements Job{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        WsfSiteServicce service=new WsfSiteServicce();
        service.excute();
    }

}
