package co.com.personalsoft.service.core.impl;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.com.personalsoft.service.core.JobInit;

@Service
public class JobInitImpl implements JobInit {

  private static final Logger logger = LoggerFactory.getLogger(JobInitImpl.class);
  
  private final Scheduler scheduler;
  
  public JobInitImpl(Scheduler scheduler) {
    this.scheduler = scheduler;
  }
  
  @Override
  public void setRunner() {
    setStarterBuildOrderCron();
    setFinisherBuildOrderCron();
  }
  
  private void setStarterBuildOrderCron() {
    String cronExpression = "0 0 6 ? * * *";
    logger.debug("CRON_EXPRESSION: {}", cronExpression);

    JobDetail job = JobBuilder.newJob(StartBuildOrderJob.class)
        .withIdentity("Starter build order job", "Starter build order group").build();

    Trigger trigger =
        TriggerBuilder.newTrigger().withIdentity("Starter build order trigger", "Starter build order group")
            .forJob(job).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();

    try {
      scheduler.scheduleJob(job, trigger);
      logger.info("Successfully started Starter build order job.");
    } catch (SchedulerException e) {
      logger.error("Error starting Starter build order job: {0}", e);
    }
  }
  
  private void setFinisherBuildOrderCron() {
    String cronExpression = "0 0 18 ? * * *";
    logger.debug("CRON_EXPRESSION: {}", cronExpression);

    JobDetail job = JobBuilder.newJob(FinishBuildOrderJob.class)
        .withIdentity("Finisher build order job", "Finisher build order group").build();

    Trigger trigger =
        TriggerBuilder.newTrigger().withIdentity("Finisher build order trigger", "Finisher build order group")
            .forJob(job).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();

    try {
      scheduler.scheduleJob(job, trigger);
      logger.info("Successfully started Finisher build order job.");
    } catch (SchedulerException e) {
      logger.error("Error starting Finisher build order job: {0}", e);
    }
  }
}
