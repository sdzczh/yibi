package com.yibi.web.robot;

import org.quartz.*;

public class Myjob implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("定时任务执行了~~~~~");
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap data = jobDetail.getJobDataMap();
        System.out.println("数据传过来了--->"+data.getString("hello"));
        System.out.println(jobDetail.getKey().getName());
    }
}
