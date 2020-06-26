package com.yenroc.ho.config;

import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * 加载配置文件 对quartz进行实例化和属性设置 注意 使用集群方案 需要连接数据库，而内存版则不用配置数据库
 */
@Configuration
public class QuartzConfig {

    public static final String QUARTZ_PROPERTIES_PATH = "spring.quartz.properties";

    /**
     * 配置JobFactory
     * @param applicationContext
     * @return
     */
    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    /**
     * SchedulerFactoryBean这个类的真正作用提供了对org.quartz.Scheduler的创建与配置，并且会管理它的生命周期与Spring同步
     *  org.quartz.Scheduler: 调度器。所有的调度都是由它控制
     * @param jobFactory 为SchedulerFactory配置JobFactory
     * @param dataSource 为SchedulerFactory配置数据源
     * @param transactionManager 事务管理器
     * @return
     * @throws IOException
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, DataSource dataSource, PlatformTransactionManager transactionManager) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setAutoStartup(true);//设置自行启动
        //可选,QuartzScheduler启动时更新己存在的Job,这样就不用每次修改targetObject后删除qrtz_job_details表对应记录
        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties());
        //集群版配置
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        return factory;
    }

    /**
     * 从properties文件中读取Quartz配置属性
     * @return
     * @throws IOException
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource(QUARTZ_PROPERTIES_PATH));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * 配置JobFactory,为quartz作业添加自动连接支持
     */
    public final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements
            ApplicationContextAware {
        private transient AutowireCapableBeanFactory beanFactory;
        @Override
        public void setApplicationContext(final ApplicationContext context) {
            beanFactory = context.getAutowireCapableBeanFactory();
        }
        @Override
        protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
            final Object job = super.createJobInstance(bundle);
            beanFactory.autowireBean(job);
            return job;
        }
    }

}
