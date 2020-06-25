package com.yenroc.ho.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ConfigurationProperties(prefix = "global", ignoreUnknownFields = true, ignoreInvalidFields= true)
@PropertySource("classpath:blog-global.properties")
@Data
public class BlogGlobalConfig implements Serializable {

    /**
     * applicationName
     */
    private String applicationName;

}
