package com.yenroc.ho.quartz.jobs;

import com.yenroc.ho.utils.DateUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 禁止并发执行多个相同定义的JobDetail, 这个注解是加在Job类上的, 但意思并不是不能同时执行多个Job
 * 而是不能并发执行同一个Job Definition(由JobDetail定义), 但是可以同时执行多个不同的JobDetail
 */
@DisallowConcurrentExecution
public class TestJob implements Job {

    private Logger log = LoggerFactory.getLogger(TestJob.class);


    @Override
    public void execute(JobExecutionContext context) {
        log.info("TestJob currentDate=[{}]", DateUtil.getCurDate());
    }
}
