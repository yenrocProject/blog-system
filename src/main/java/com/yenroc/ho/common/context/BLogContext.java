package com.yenroc.ho.common.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BLogContext {
    
    //日志
    private static Logger log = LoggerFactory.getLogger(BLogContext.class);
    
    private static ThreadLocal<Map<String, Object>> threadLocalMap = new ThreadLocal<Map<String,Object>>();

    public static void setValue(String key, Object value) {
        Map<String, Object> map = threadLocalMap.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            // 第一次往map添加的时候需要把map放到线程变量里
            threadLocalMap.set(map);
        }
        map.put(key, value);
    }

    public static Object getValue(String key) {
        Map<String, Object> map = threadLocalMap.get();
        if (map == null) {
            //调用可能不是从web端接入，不抛异常，记录警告日志
            log.debug("key:" + key + "的值未设置或未初始化");
            return null;
        }
        return map.get(key);
    }

    public static void setMap(Map<String, Object> map) {
        threadLocalMap.set(map);
    }

    public static Map<String, Object> getMap() {
        return threadLocalMap.get();
    }

    public static void clear() {
        threadLocalMap.remove();
    }
}
