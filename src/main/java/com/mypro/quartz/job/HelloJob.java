package com.mypro.quartz.job;

import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import static org.quartz.DateBuilder.evenMinuteDate;  
import static org.quartz.JobBuilder.newJob;  
import static org.quartz.TriggerBuilder.newTrigger; 
import static org.quartz.CronScheduleBuilder.cronSchedule;

import com.mypro.quartz.QuartzScheduleMgr;

public class HelloJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.err.println("Hello Quartz! - " + new Date());
	}
	
	/**
	 * 调用任务,需要添加内容,当shutdown时需要判断是否所有的任务都执行完毕，以及是否需要改变单例模式
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.err.println("------- Initializing ----------------------");  
		  
        //从调度管理器中获取调度对象  
        Scheduler sched = QuartzScheduleMgr.getInstanceScheduler();
        System.err.println("------- Initialization Complete -----------");
  
        // computer a time that is on the next round minute  
        Date runTime = evenMinuteDate(new Date());  
  
        System.err.println("------- Scheduling Job  -------------------");
  
        // define the job and tie it to our HelloJob class  
        //创建相关的job信息  
        JobDetail job = newJob(HelloJob.class)  
            .withIdentity("job1", "group1")  
            .build();  
         
        //创建一个触发器的名称  
        /*Trigger trigger = newTrigger()  
            .withIdentity("trigger1", "group1")  
            .startAt(runTime)  
            .build();  */
        
        //cron
        CronTrigger cronTrigger = newTrigger()
        		.withIdentity("trigger2", "group1")
        		.withSchedule(cronSchedule("0/20 * * * * ?"))
        		.build();
        sched.scheduleJob(job, cronTrigger);
        
        //设置调度相关的Job  
        //sched.scheduleJob(job, trigger);  
        System.err.println(job.getKey() + " will run at: " + runTime);    
  
        //启动调度任务  
        sched.start();  
  
        //任务运行状态
        System.err.println("-----------任务状态:"+sched.getTriggerState(new TriggerKey("trigger2", "group1")));
        System.err.println("------- Started Scheduler -----------------");  
  
        try {  
            Thread.sleep(25L * 1000L);   
            // executing...  
        } catch (Exception e) {  
        }  
        //暂时停止Job任务开始执行  
        System.err.println("-------pauseJob.. -------------");  
        sched.pauseJob(job.getKey());  
          
        try {  
            Thread.sleep(10L * 1000L);   
        } catch (Exception e) {  
        }  
        System.err.println("------- resumeJob... -------------");  
        //恢复Job任务开始执行  
        sched.resumeJob(job.getKey());  
        try {  
            Thread.sleep(10L * 1000L);   
            // executing...  
        } catch (Exception e) {  
        }  
          
        // wait long enough so that the scheduler as an opportunity to   
        // run the job!  
        System.err.println("------- Waiting 65 seconds... -------------");  
        try {  
            // wait 65 seconds to show job  
            Thread.sleep(65L * 1000L);   
            // executing...  
        } catch (Exception e) {  
        }  
        
        // shut down the scheduler  
        System.err.println("------- Shutting Down ---------------------");  
        sched.shutdown(true);  
        System.err.println("------- Shutdown Complete -----------------"); 
      
	}
}
