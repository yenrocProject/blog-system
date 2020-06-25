package com.yenroc.ho.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;

@Component
public class PropertiesSourceConfig extends ReloadableResourceBundleMessageSource {

    private final Logger log = LoggerFactory.getLogger(PropertiesSourceConfig.class);
    private static String LOCALE_EN = "en";
    private static String LOCALE_FRENCH = "";
    private static String LOCALE_GERMAN = "";
    private static String LOCALE_ITALIAN = "ita";
    private static String LOCALE_JAPANESE = "ja";
    private static String LOCALE_KOREAN = "";
    private static String LOCALE_SIMPLIFIED_CHINESE = "zh_CN";
    private static String LOCALE_TRADITIONAL_CHINESE = "CN";

    private static String LOCALE_PREFIX = "_";
    private static String LOCALE_SUFFIX = ".properties";

    public PropertiesSourceConfig() {
    }

    public void setBasenames(String[] basenames) {
        this.log.info("多语言配置文件加载开始");
        this.getBasenameSet().clear();
        String[] var2 = basenames;
        int var3 = basenames.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String baseName = var2[var4];
            String folderPrefix;
            if (baseName.contains("**/*")) {
                folderPrefix = baseName.substring(0, baseName.lastIndexOf("**/*"));
            } else {
                folderPrefix = baseName.substring(0, baseName.lastIndexOf("*"));
            }

            ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
            Resource[] source = null;

            try {
                source = resourceLoader.getResources(baseName);
            } catch (IOException var14) {
                this.log.error(MessageFormat.format("初始化多语言的配置文件出错：[baseName={0}]", baseName), var14);
                continue;
            }

            for(int i = 0; i < source.length; ++i) {
                Resource resource = source[i];
                if (!resource.getFilename().endsWith(LOCALE_PREFIX + LOCALE_EN + LOCALE_SUFFIX) && !resource.getFilename().endsWith(LOCALE_PREFIX + LOCALE_FRENCH + LOCALE_SUFFIX) && !resource.getFilename().endsWith(LOCALE_PREFIX + LOCALE_GERMAN + LOCALE_SUFFIX) && !resource.getFilename().endsWith(LOCALE_PREFIX + LOCALE_ITALIAN + LOCALE_SUFFIX) && !resource.getFilename().endsWith(LOCALE_PREFIX + LOCALE_JAPANESE + LOCALE_SUFFIX) && !resource.getFilename().endsWith(LOCALE_PREFIX + LOCALE_KOREAN + LOCALE_SUFFIX) && !resource.getFilename().endsWith(LOCALE_PREFIX + LOCALE_SIMPLIFIED_CHINESE + LOCALE_SUFFIX) && !resource.getFilename().endsWith(LOCALE_PREFIX + LOCALE_TRADITIONAL_CHINESE + LOCALE_SUFFIX)) {
                    String fileName = resource.getFilename().replace(LOCALE_SUFFIX, "");
                    String parentFile = "";

                    try {
                        String filePath = resource.getURL().getPath();
                        filePath = filePath.substring(filePath.lastIndexOf("/" + folderPrefix.replace("classpath*:", "")) + 6);
                        filePath = filePath.replace(resource.getFilename(), "");
                        parentFile = filePath;
                    } catch (IOException var15) {
                        this.log.error(MessageFormat.format("初始化多语言的配置文件出错：[baseName={0},fileName={1}]", baseName, resource.getFilename()), var15);
                        continue;
                    }

                    this.getBasenameSet().add(("classpath:/i18n/" + parentFile + fileName).trim());
                }
            }
        }

        this.log.info("多语言配置文件加载完成");
    }
}
