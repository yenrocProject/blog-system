package com.yenroc.ho.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ConfigurationProperties(prefix = "file", ignoreUnknownFields = true, ignoreInvalidFields= true)
@PropertySource("classpath:file.properties")
@Data
public class FileConfig implements Serializable {

    /**
     * 文件上传路径
     */
    private String uploadPath;

    /**
     * 可预览的图片类型
     */
    private String previewImgType;

    /**
     * 可预览的文本型类型
     */
    private String previewTextType;

}
