package com.yenroc.ho.common.consts;

public class CommonConsts {

    public static String SESSION_USER = "SESSION_USER";
    
    public static String SESSION_ROLE_DATA_AUTHORITY = "SESSION_ROLE_DATA_AUTHORITY";
    
    public static String PAGE_INFO = "pageInfo";
    
    public static String SCREEN_ID = "view";
    
    public static String SESSION_SECURITY_CODE = "SESSION_SECURITY_CODE";
    
    public static String REQUEST_PARAMETER = "request_parameter";
    
    public static String RESPONSE_CODE_SUCCESS = "200";
    
    public static String RESPONSE_CODE_WARNING = "300";

    public static String RESPONSE_CODE_ERROR = "500";
    
    public static String PAGE_INDEX = "pageIndex";
    
    public static String PAGE_SIZE = "pageSize";
    
    public static String ORDER_BY = "orderBy";
    
    public static String ACCEPT_LANGUAGE = "Accept_Language";

    public static String THREAD_ID = "THREAD_ID";

    public static class RedisKeyConsts {

        // 1小时,单位s
        public static long expireTime_hours = 60 * 60;

        // [单数据] TableData:表:id
        public static String table_data_key = "TableData:%s:%s";


    }
}

