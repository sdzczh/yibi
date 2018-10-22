package com.yibi.web.robot;

import com.yibi.web.robot.job.BuyIntrustJob;
import com.yibi.web.robot.job.BuyMarketJob;
import com.yibi.web.robot.job.SaleIntrustJob;
import com.yibi.web.robot.job.SaleMarketJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuartzManager {
    @Autowired
    private Scheduler scheduler;
    /**
     * 添加任务，并启动
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public  void addJob(String jobName, String jobGroupName,
                        String triggerName, String triggerGroupName, int taskType, int  timeInterval, Date startTime, Date endTime, int taskId){
        try {
            Class jobClass = null;
            if(taskType ==0){
                jobClass =  BuyIntrustJob.class;
            }else if(taskType==1){
                jobClass =  SaleIntrustJob.class;
            }else if(taskType==2){
                jobClass =  BuyMarketJob.class;
            }else if(taskType==3){
                jobClass =  SaleMarketJob.class;
            }
            if(jobClass == null){
                throw new RuntimeException("任务类型不正确");
            }
            // 任务名，任务组，任务执行类
            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            jobDetail.getJobDataMap().put("taskId", taskId);
            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            if(endTime !=null){
                triggerBuilder.startAt(startTime);
            }else{
                triggerBuilder.startNow();
            }
            // 触发器时间设定
            triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().
                    withIntervalInSeconds(timeInterval)
                    .withRepeatCount(-1));
            //设置结束时间
            if(endTime!=null){
                triggerBuilder.endAt(endTime);
            }
            // 创建Trigger对象
            Trigger trigger = triggerBuilder.build();

            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(jobDetail, trigger);

            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改任务的执行时间
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @param timeInterval
     * @param endTime
     * @return void
     * @date 2018-6-25
     * @author lina
     */
    public  void modifyJobTime(String jobName,String jobGroupName, String triggerName,
                               String triggerGroupName, int  timeInterval,Date startTime,Date endTime) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            Trigger trigger =  scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }

            /** 方式一 ：调用 rescheduleJob 开始 */
            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            if(endTime !=null){
                triggerBuilder.startAt(startTime);
            }else{
                triggerBuilder.startNow();
            }
            // 触发器时间设定
            triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().
                    withIntervalInSeconds(timeInterval)
                    .withRepeatCount(-1));
            //设置结束时间
            if(endTime!=null){
                triggerBuilder.endAt(endTime);
            }
            // 创建Trigger对象
            trigger = triggerBuilder.build();
            // 方式一 ：修改一个任务的触发时间
            scheduler.rescheduleJob(triggerKey, trigger);
            /** 方式一 ：调用 rescheduleJob 结束 */

            /** 方式二：先删除，然后在创建一个新的Job  */
            //JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
            //Class<? extends Job> jobClass = jobDetail.getJobClass();
            //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
            //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
            /** 方式二 ：先删除，然后在创建一个新的Job */
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除任务
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @return void
     * @date 2018-6-25
     * @author lina
     */
    public  void removeJob(String jobName, String jobGroupName,
                           String triggerName, String triggerGroupName) {
        try {

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  启动所有的任务
     * @return void
     * @date 2018-6-25
     * @author lina
     */
    public  void startJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 停止所有任务
     * @return void
     * @date 2018-6-25
     * @author lina
     */
    public  void shutdownJobs() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取所有计划中的任务列表
     * @return
     */
    public List<Map<String,Object>> queryAllJob(){
        List<Map<String,Object>> jobList=null;
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            jobList = new ArrayList<Map<String,Object>>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String,Object> map=new HashMap<>();
                    map.put("jobName",jobKey.getName());
                    map.put("jobGroupName",jobKey.getGroup());
                    map.put("description","触发器:" + trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    map.put("jobStatus",triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        map.put("jobTime",cronExpression);
                    }
                    jobList.add(map);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    /**
     * 获取所有正在运行的job
     * @return
     */
    public List<Map<String,Object>> queryRunJon(){
        List<Map<String,Object>> jobList=null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<Map<String,Object>>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Map<String,Object> map=new HashMap<String, Object>();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                map.put("jobName",jobKey.getName());
                map.put("jobGroupName",jobKey.getGroup());
                map.put("description","触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                map.put("jobStatus",triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    map.put("jobTime",cronExpression);
                }
                jobList.add(map);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }
}
