package com.yenroc.ho.utils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class DateUtil {
    
    private static Logger log = LoggerFactory.getLogger(DateUtil.class);
    
    private static String DATE_PATTERN_YYYYMMDD = "yyyy-MM-dd";
    private static String DATE_PATTERN_YYYYMMDDHHmmSS = "yyyy-MM-dd HH:mm:ss";

    public static Date getCurDate() {
        Calendar cd = Calendar.getInstance();
        cd.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return cd.getTime();
    }

    public static String toString(Date date, String format) {
        if (StringUtils.hasText(format) && date != null) {
            SimpleDateFormat simpleDateFormat = null;

            try {
                simpleDateFormat = new SimpleDateFormat(format);
                return simpleDateFormat.format(date);
            } catch (IllegalArgumentException arg3) {
                log.error(MessageFormat.format("日期转换时格式不符合要求：[format={0}]", new Object[]{format}));
                return null;
            }
        } else {
            log.error("日期转换时参数不能为空！");
            return null;
        }
    }

    public static String[] getDatePattern() {
        ArrayList<String> patternStrList = new ArrayList<String>();
        patternStrList.add(DATE_PATTERN_YYYYMMDD);
        patternStrList.add(DATE_PATTERN_YYYYMMDDHHmmSS);
        String[] dateFormatList = new String[0];
        return (String[]) patternStrList.toArray(dateFormatList);
    }
}
