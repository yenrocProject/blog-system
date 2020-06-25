package com.yenroc.ho.common.bean.enums;

import java.text.MessageFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多语言枚举
 * @author 和彦鹏
 * @date 2018年7月22日22:21:35
 * @version v0.1
 */
public enum LangEnum {
    //中文简体
    ZH_CN("zh-CN", Locale.SIMPLIFIED_CHINESE),
    //中文繁体
    ZH_TW("zh-TW", Locale.TRADITIONAL_CHINESE),
    //英语
    EN("en",Locale.ENGLISH),
    //韩语
    KO("ko",Locale.KOREAN),
    //日语
    JA("ja", Locale.JAPAN);

    //日志
    private final static Logger log = LoggerFactory.getLogger(LangEnum.class);
    
    private String lang;
    private Locale locale;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    private LangEnum(String lang, Locale locale) {
        this.lang = lang;
        this.locale = locale;
    }

    /**
     * 根据ID获取枚举值
     * @param lang ID
     * @return ID对应的枚举值
     * @author 和彦鹏
     * @date 2018年7月22日22:21:56
     * @version v0.1
     */
    public static Locale getLocale(String lang) {
        for (LangEnum enumLang : LangEnum.values()) {
            if (enumLang.getLang().equals(lang)) {
                return enumLang.getLocale();
            }
        }
        log.warn(MessageFormat.format("未能在多语言中找到对应的配置，[lang={0}]，将使用默认的中文简体语言配置[locale={1}]", lang, Locale.SIMPLIFIED_CHINESE.getLanguage()));
        return Locale.SIMPLIFIED_CHINESE;
    }
}