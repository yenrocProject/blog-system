package com.yenroc.ho.common.dao;

import com.yenroc.ho.common.bean.PageInfo;

import java.util.List;
import java.util.Map;


public interface QueryDao {
    
    <E> E executeForObject(String arg0, Object arg1, Class arg2);
    
    Map<String, Object> executeForMap(String arg0, Object arg1);
    
    <E> E[] executeForObjectArray(String arg0, Object arg1, Class arg2);
    
    <E> List<E> executeForObjectList(String arg0, Object arg1);
    
    <E> List<E> executeForObjectList(String arg0, Object arg1, String orderBy);
    
    <E> List<E> executeForObjectList(String arg0, Object arg1, PageInfo pageInfo);
    
    <E> List<E> executeForObjectListByPage(String arg0, Object arg1, Integer pageNum, Integer pageSize) throws Exception;
    
    <E> List<E> executeForObjectListByPage(String arg0, Object arg1, PageInfo pageInfo) throws Exception;
    
    List<Map<String, Object>> executeForMapList(String arg0, Object arg1);
    
    List<Map<String, Object>> executeForMapList(String arg0, Object arg1, PageInfo pageInfo) throws Exception;
}
