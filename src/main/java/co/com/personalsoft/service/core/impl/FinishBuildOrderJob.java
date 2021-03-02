package co.com.personalsoft.service.core.impl;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import co.com.personalsoft.service.core.ResolveJob;

@Component
public class FinishBuildOrderJob extends QuartzJobBean {

  private static final Logger logger = LoggerFactory.getLogger(FinishBuildOrderJob.class);

  private final ResolveJob resolveJob;
  
  public FinishBuildOrderJob(ResolveJob resolveJob) {
    this.resolveJob = resolveJob;
  }
  
  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    logger.info("Excecuting ResolveJob");
    resolveJob.checkAndUpdateBuildingBuildOrder();
  }

}
