package com.yenroc.ho.config;

import com.yenroc.ho.quartz.jobs.TestJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzBindJobConfig {

    private Logger log = LoggerFactory.getLogger(QuartzBindJobConfig.class);

    @Autowired
    private QuartzJobFactory quartzJobFactory;

    @Autowired
    private Scheduler scheduler;

    public void scheduleBind() {
        try {
            scheduler.setJobFactory(quartzJobFactory);
            JobDetail tesJobDetail = JobBuilder.newJob(TestJob.class)
                    .withIdentity("testJob", "testJob").withDescription("定时任务demo")
                    .build();
            CronScheduleBuilder tesJobCronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
            CronTrigger tesJobCronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("testJobTrigger", "testJob")
                    .withSchedule(tesJobCronScheduleBuilder).build();
            scheduler.scheduleJob(tesJobDetail, tesJobCronTrigger);
            scheduler.start();
        } catch (SchedulerException e) {
            // e.printStackTrace();
        }
    }
}
