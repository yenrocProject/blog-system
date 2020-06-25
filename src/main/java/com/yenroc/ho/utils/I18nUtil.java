package com.yenroc.ho.utils;

import java.text.MessageFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.yenroc.ho.common.bean.enums.LangEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 描述:获取多语言util
 * 1、从head的accept-language中获取多语言版本
 * 1.1、如果获取到则根据对应的多语言获取具体的多语言的message
 * 1.2、如果未获取到，则默认返回中文简体的多语言
 * @author:和彦鹏
 * @date:2018年7月22日21:50:18
 * @version:v0.1
 */
public class I18nUtil {
    
    //日志
    private final static Logger log = LoggerFactory.getLogger(I18nUtil.class);
    
    private static MessageSource messageSource;
    
    private static final Object[] objs = null;

    /**
     * 获取国际化信息
     * @param key 对应键
     * @param locale 语言
     * @param objects 参数
     * @return 国际化信息
     * @author 和彦鹏
     * @date 2018年7月22日21:50:51
     * @version v0.1
     */
    public static String getMessage(String key, Locale locale, Object... objects) {
        if (StringUtils.isEmpty(key)) {
            return StringUtils.EMPTY;
        }
        
        HttpServletRequest request = null;
        try {
            ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes(); 
            request = attr.getRequest();
        } catch(Exception e) {
            log.error(MessageFormat.format("获取request失败，可能当前请求不是web请求。将采用默认的zh_CN。message=[{0}]", e.getMessage()));
        }
        if (null == locale) {
            if (null == request) {
                locale = Locale.SIMPLIFIED_CHINESE;
            } else {
                //获取多语言头
                String lang = request.getHeader("Accept-Language");
                if (StringUtils.isEmpty(lang)) {
                    log.warn("从heade中获取Accept-Language属性为空，将使用默认的中文简体语言设置");
                    locale = Locale.SIMPLIFIED_CHINESE;
                } else {
                    locale = LangEnum.getLocale(lang);
                }
            }
        }
        log.debug("Spring I18 ---> key：[" + key + "]，lang：[" + locale.getLanguage() + "]，params：[" + objects + "]");
        // messageSource = SpringContextUtil.getBean("messageSource");
        return messageSource.getMessage(key, objects, locale);
    }

    /**
     * 获取国际化信息
     * @param key 对应键
     * @param locale 语言
     * @return 国际化信息
     * @author 和彦鹏
     * @date 2018年7月22日22:05:36
     * @version v0.1
     */
    public static String getLocaleMessage(String key, Locale locale) {
        return getMessage(key, locale, objs);
    }

    /**
     * 获取国际化信息
     * @param key 对应键
     * @param objects 参数
     * @return 国际化信息
     * @author 和彦鹏
     * @date 2018年7月22日22:05:58
     * @version v0.1
     */
    public static String getMessage(String key, Object... objects) {
        return getMessage(key, null, objects);
    }

    /**
     * 获取国际化信息
     * @param key 对应键
     * @param lang 语言
     * @return 国际化信息
     * @author 和彦鹏
     * @date 2018年7月22日22:06:31
     * @version v0.1
     */
    public static String getLocaleMessage(String key, String lang) {
        return getMessage(key, LangEnum.getLocale(lang));
    }

    /**
     * 获取国际化信息
     * 
     * @param key
     *            对应键
     * @return 国际化信息
     * @author 谷春
     * @date 2017-7-24 14:25:05
     * @version 1.0.0
     */
    public static String getMessage(String key) {
        return getMessage(key, null, objs);
    }
}
