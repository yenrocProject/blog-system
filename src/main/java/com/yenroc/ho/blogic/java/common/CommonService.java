package com.yenroc.ho.blogic.java.common;

import java.util.List;
import java.util.Map;

/**
 * 统一开发查询的接口Service接口
 */
public interface CommonService {

    /**
     * 进行统一查询的方法
     * @param tableName 要查询的表名
     * @param params 查询的参数信息
     * @param <T> 返回的对象信息
     * @return 返回的类型
     * @throws Exception 异常
     */
    <T> List<T> select(String tableName, Map<String, Object> params) throws Exception;

    /**
     * 进行统一创建表数据
     * @param tableName 要添加数据的表名
     * @param params 添加的数据参数
     * @return 返回的主键id
     * @throws Exception 异常
     */
    String insert(String tableName, Map<String, Object> params) throws Exception;

    /**
     * 进行统一更新表数据:根据id 进行更新
     * @param tableName 要添加数据的表名
     * @param params 添加的数据参数
     * @return 返回的主键id
     * @throws Exception 异常
     */
    int update(String tableName, Map<String, Object> params) throws Exception;

    /**
     * 进行统一删除表数据:根据id 进行删除
     * @param tableName 要添加数据的表名
     * @param params 添加的数据参数
     * @return 返回的主键id
     * @throws Exception 异常
     */
    int delete(String tableName, Map<String, Object> params) throws Exception;

}
