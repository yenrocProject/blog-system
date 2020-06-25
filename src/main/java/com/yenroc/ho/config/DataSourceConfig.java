package com.yenroc.ho.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * dataSource 加载
 */
@Configuration
@PropertySource(
        value = {"classpath:jdbc.properties"},
        ignoreResourceNotFound = true
)
@ImportResource({"classpath:datasource.xml"})
public class DataSourceConfig {
    public DataSourceConfig() {
    }
}
