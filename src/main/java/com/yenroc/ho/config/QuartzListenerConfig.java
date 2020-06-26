package com.yenroc.ho.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class QuartzListenerConfig implements ApplicationListener<ContextRefreshedEvent> {

    private Logger log = LoggerFactory.getLogger(QuartzListenerConfig.class);

    @Autowired
    private QuartzBindJobConfig quartzBindJobConfig;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("QuartzListener scheduleBind start...");
        quartzBindJobConfig.scheduleBind();
        log.info("QuartzListener scheduleBind end...");
    }
}
