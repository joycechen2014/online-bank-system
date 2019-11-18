package com.hendisantika.onlinebanking.service;

import com.hendisantika.onlinebanking.entity.QuartzBean;
import com.hendisantika.onlinebanking.entity.TransferMoneyDTO;
import com.hendisantika.onlinebanking.job.RecurringTransferMoneyJob;
import java.security.Principal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

  private static Logger logger = LogManager.getLogger(RecurringTransferMoneyJob.class.getName());


  @Autowired
  private Scheduler scheduler;

  public Scheduler getScheduler() {
    return scheduler;
  }

  /**
   * 创建定时任务 定时任务创建之后默认启动状态
   *
   * @param quartzBean 定时任务信息类
   */
  public void createScheduleJob(QuartzBean quartzBean) {
    try {
      //获取到定时任务的执行类  必须是类的绝对路径名称
      //定时任务类需要是job类的具体实现 QuartzJobBean是job的抽象类。
      Class<? extends Job> jobClass = (Class<? extends Job>) Class
          .forName(quartzBean.getJobClass());
      // 构建定时任务信息
      JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(quartzBean.getJobName())
          .build();
      // 设置定时任务执行方式
      CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
          .cronSchedule(quartzBean.getCronExpression());
      // 构建触发器trigger
      CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(quartzBean.getJobName())
          .withSchedule(scheduleBuilder).build();
      scheduler.scheduleJob(jobDetail, trigger);
    } catch (ClassNotFoundException e) {
      logger.error("定时任务类路径出错：请输入类的绝对路径");
    } catch (SchedulerException e) {
      logger.error("创建定时任务出错：" + e);
    }
  }

  /**
   * 根据任务名称暂停定时任务
   *
   * @param jobName 定时任务名称
   */
  public void pauseScheduleJob(String jobName) {
    JobKey jobKey = JobKey.jobKey(jobName);
    try {
      scheduler.pauseJob(jobKey);
    } catch (SchedulerException e) {
      logger.error("暂停定时任务出错：" + e.getMessage());
    }
  }

  /**
   * 根据任务名称恢复定时任务
   *
   * @param jobName 定时任务名称
   */
  public void resumeScheduleJob(String jobName) {
    JobKey jobKey = JobKey.jobKey(jobName);
    try {
      scheduler.resumeJob(jobKey);
    } catch (SchedulerException e) {
      logger.error("启动定时任务出错：" + e.getMessage());
    }
  }

  /**
   * 根据任务名称立即运行一次定时任务
   *
   * @param jobName 定时任务名称
   */
  public void runOnce(String jobName) {
    JobKey jobKey = JobKey.jobKey(jobName);
    try {
      scheduler.triggerJob(jobKey);
    } catch (SchedulerException e) {
      logger.error("运行定时任务出错：" + e.getMessage());
    }
  }

  /**
   * 更新定时任务
   *
   * @param quartzBean 定时任务信息类
   */
  public void updateScheduleJob(QuartzBean quartzBean) {
    try {
      //获取到对应任务的触发器
      TriggerKey triggerKey = TriggerKey.triggerKey(quartzBean.getJobName());
      //设置定时任务执行方式
      CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
          .cronSchedule(quartzBean.getCronExpression());
      //重新构建任务的触发器trigger
      CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
      trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder)
          .build();
      //重置对应的job
      scheduler.rescheduleJob(triggerKey, trigger);
    } catch (SchedulerException e) {
      logger.error("更新定时任务出错：" + e.getMessage());
    }
  }

  /**
   * 根据定时任务名称从调度器当中删除定时任务
   *
   * @param jobName 定时任务名称
   */
  public void deleteScheduleJob(String jobName) throws SchedulerException {
    JobKey jobKey = JobKey.jobKey(jobName);
      scheduler.deleteJob(jobKey);

  }

  public void createScheduleJob(QuartzBean bean, TransferMoneyDTO payload, Principal principal)
      throws SchedulerException {
    scheduler.getContext().put("payload", payload);
    scheduler.getContext().put("principal", principal);
    createScheduleJob(bean);
  }
}
