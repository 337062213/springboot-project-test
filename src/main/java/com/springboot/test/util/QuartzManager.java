 package com.springboot.test.util;

import java.util.Date;
import java.util.HashSet;
import org.apache.commons.lang.time.DateFormatUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springboot.test.service.task.TestJob;

public class QuartzManager {
    
     private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory(); 
     private static String JOB_NAME = "EXTJWEB_NAME";
     private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
     private static String TRIGGER_NAME = "EXTJWEB_TRIGGER_NAME";
     private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
     private static String CRON_EXPRESSION = "0 0/1 * * * ?";
     
     private static Logger logger = LoggerFactory.getLogger(QuartzManager.class);
     
     /**  
      * addJob 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名    
      * @param jobName    任务名   
      * @throws SchedulerException  
      * @throws ParseException  
      */    
     public static void addSimpleJob() {    
         try { 
             // 计划
             Scheduler scheduler = gSchedulerFactory.getScheduler();
             // 任务
             JobDetail jobDetail = JobBuilder.newJob(TestJob.class).withIdentity(JOB_NAME, JOB_GROUP_NAME).withDescription(JOB_GROUP_NAME + JOB_NAME).build();
             
             SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
             simpleScheduleBuilder.withIntervalInSeconds(3);
             simpleScheduleBuilder.withRepeatCount(5);
             // 触发器    
             Trigger trigger = TriggerBuilder.newTrigger().withIdentity(TRIGGER_NAME, TRIGGER_GROUP_NAME).withSchedule(simpleScheduleBuilder).startAt(new Date()).build();   
             scheduler.scheduleJob(jobDetail, trigger);    
             // 启动    
             if (!scheduler.isShutdown()){    
                 scheduler.start();    
             }   
         } catch (Exception e) {    
             e.printStackTrace();    
             throw new RuntimeException(e);    
         }    
     }    
     
     /**  
      * addJob 添加一个定时任务   
      * @param jobName    任务名  
      * @param jobGroupName   任务组名  
      * @param triggerName   触发器名  
      * @param triggerGroupName  触发器组名   
      * @param time   时间设置，参考quartz说明文档  
      * @throws SchedulerException  
      * @throws ParseException  
      */    
     public static void addCronJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName){    
         try {    
             Scheduler scheduler = gSchedulerFactory.getScheduler();    
             JobDetail jobDetail = JobBuilder.newJob(TestJob.class).withIdentity(jobName, jobGroupName).withDescription(jobName + jobGroupName).build();  
             // 触发器  SimpleTrigger  CronTrigger   
             Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(CronScheduleBuilder.cronSchedule(CRON_EXPRESSION)).build();     
             scheduler.scheduleJob(jobDetail, trigger);
             // 启动    
             if (!scheduler.isShutdown()){    
                 scheduler.start();    
             }
         } catch (Exception e) {    
             e.printStackTrace();    
             throw new RuntimeException(e);    
         }    
     }        
     
     /**  
      * modifyJobTime 修改一个任务的触发时间  
      * @param triggerName  
      * @param triggerGroupName  
      * @param time  
      */    
     public static void modifyJobTime(String triggerName,    
             String triggerGroupName, String cronExpression) {    
         try {    
             Scheduler scheduler = gSchedulerFactory.getScheduler();  
             TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
             JobKey jobKey = new JobKey(triggerName, triggerGroupName);
             CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
             String jobDescription = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
             CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(jobDescription).withSchedule(cronScheduleBuilder).build();    
             if(cronTrigger == null) {    
                 return;    
             }    
             JobDetail jobDetail = scheduler.getJobDetail(jobKey);
             jobDetail.getJobBuilder().withDescription(jobDescription);
             HashSet<Trigger> triggerSet = new HashSet<>();
             triggerSet.add(cronTrigger);
             scheduler.scheduleJob(jobDetail, triggerSet, true);    
         } catch (Exception e) {    
             e.printStackTrace();    
             throw new RuntimeException(e);    
         }    
     }       
     
     /**  
      * removeJob 移除一个任务  
      * @param jobName  
      * @param jobGroupName  
      * @param triggerName  
      * @param triggerGroupName  
      */    
     public static void removeJob(String jobName, String jobGroupName,    
             String triggerName, String triggerGroupName) {    
         Scheduler scheduler;
        try {
            scheduler = gSchedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            if (scheduler.checkExists(triggerKey)) {
                scheduler.pauseTrigger(triggerKey);
                scheduler.unscheduleJob(triggerKey);
                logger.info("===> delete, triggerKey:{}", triggerKey);
            }
        } catch (SchedulerException e1) {
             e1.printStackTrace();
        }
     }
     
     /**  
      * startJobs 启动所有定时任务  
      */    
     public static void startJobs() {    
         try {    
             Scheduler sched = gSchedulerFactory.getScheduler();    
             sched.start();    
         } catch (Exception e) {    
             e.printStackTrace();    
             throw new RuntimeException(e);    
         }    
     }    
     
     /**  
      * shutdownJobs 关闭所有定时任务  
      */    
     public static void shutdownJobs() {    
         try {    
             Scheduler sched = gSchedulerFactory.getScheduler();    
             if(!sched.isShutdown()) {    
                 sched.shutdown();    
             }    
         } catch (Exception e) {    
             e.printStackTrace();    
             throw new RuntimeException(e);    
         }    
     }
     
     public static void main(String[] args) {
         
             addSimpleJob();
             String jobName="jobName1", jobGroupName="jobGroupName1", triggerName="triggerName1",triggerGroupName="triggerGroupName1";
             addCronJob( jobName, jobGroupName, triggerName, triggerGroupName);

     }

 }    

