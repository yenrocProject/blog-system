package com.yenroc.ho.common.dao.impl;

import com.github.pagehelper.PageHelper;
import com.yenroc.ho.common.bean.PageInfo;
import com.yenroc.ho.common.dao.IllegalClassTypeException;
import com.yenroc.ho.common.dao.QueryDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.StopWatch;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class QueryDaoImpl extends SqlSessionDaoSupport implements QueryDao {
    private static Log log = LogFactory.getLog(QueryDaoImpl.class);
    
    /**
     * 执行sql语句返回一条记录，如果返回多条记录，报ToManyResults异常
     * @param sqlID 执行数据库的sqlId
     * @param bindParams 执行sql语句的传入参数
     * @param clazz 需要返回的对象的类型
     * @return E 返回的对象
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <E> E executeForObject(String sqlID, Object bindParams, Class clazz) {
        if (log.isDebugEnabled()) {
            log.debug("executeForObject Start.");
        }
        
        log.info(MessageFormat.format("sqlId[{0}]，输入参数：[{1}]", sqlID, bindParams));
        StopWatch stopWatch = new StopWatch(sqlID);
        stopWatch.start();
        
        SqlSession sqlMapTemp = this.getSqlSession();
        Object obj = sqlMapTemp.selectOne(sqlID, bindParams);
        if (log.isDebugEnabled() && obj != null) {
            log.debug("Return type:" + obj.getClass().getName());
        }

        Object rObj = null;

        try {
            if (clazz != null && obj != null) {
                rObj = clazz.cast(obj);
            }
        } catch (ClassCastException arg7) {
            log.error("The illegal Class Type of the argument.");
            throw new IllegalClassTypeException(arg7);
        }

        if (log.isDebugEnabled()) {
            log.debug("executeForObject End.");
            log.debug(MessageFormat.format("sqlId[{0}]，返回值：[{1}]", sqlID, rObj));
        }
        stopWatch.stop();
        log.info("----------sqlId：" + sqlID + "执行完毕，耗时" + stopWatch.getTotalTimeMillis() + "毫秒----------");
        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format("sqlId[{0}]，返回值：[{1}]", sqlID, rObj));
        }
        return (E)rObj;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, Object> executeForMap(String sqlID, Object bindParams) {
        if (log.isDebugEnabled()) {
            log.debug("executeForMap Start.");
        }

        Map rObj = (Map) this.executeForObject(sqlID, bindParams, Map.class);
        if (log.isDebugEnabled()) {
            log.debug("executeForMap End.");
        }

        return rObj;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <E> E[] executeForObjectArray(String sqlID, Object bindParams, Class clazz) {
        if (log.isDebugEnabled()) {
            log.debug("executeForObjectArray Start.");
        }
        log.info(MessageFormat.format("sqlId[{0}]，输入参数：[{1}]", sqlID, bindParams));

        if (clazz == null) {
            log.error("The illegal Class Type of the argument.");
            throw new IllegalClassTypeException();
        } else {
            SqlSession sqlMapTemp = this.getSqlSession();
            List list = sqlMapTemp.selectList(sqlID, bindParams);
            Object[] retArray = (Object[]) ((Object[]) Array.newInstance(clazz, list.size()));

            try {
                list.toArray(retArray);
            } catch (ArrayStoreException arg7) {
                log.error("The illegal Class Type of the argument.");
                throw new IllegalClassTypeException(arg7);
            }

            if (log.isDebugEnabled()) {
                log.debug("executeForObjectArray End.");
            }
            if (log.isDebugEnabled()) {
                log.debug(MessageFormat.format("sqlId[{0}]，返回值：[{1}]", sqlID, retArray));
            }
            return (E[])retArray;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <E> E[] executeForObjectArray(String sqlID, Object bindParams, Class clazz, int beginIndex, int maxCount) {
        if (log.isDebugEnabled()) {
            log.debug("executeForObjectArray Start.");
        }
        log.info(MessageFormat.format("sqlId[{0}]，输入参数：[{1}]", sqlID, bindParams));

        if (clazz == null) {
            log.error("The illegal Class Type of the argument.");
            throw new IllegalClassTypeException();
        } else {
            SqlSession sqlMapTemp = this.getSqlSession();
            List list = sqlMapTemp.selectList(sqlID, bindParams); //TODO
            Object[] retArray = (Object[]) ((Object[]) Array.newInstance(clazz, list.size()));

            try {
                list.toArray(retArray);
            } catch (ArrayStoreException arg9) {
                log.error("The illegal Class Type of the argument.");
                throw new IllegalClassTypeException(arg9);
            }

            if (log.isDebugEnabled()) {
                log.debug("executeForObjectArray End.");
            }

            if (log.isDebugEnabled()) {
                log.debug(MessageFormat.format("sqlId[{0}]，返回值：[{1}]", sqlID, retArray));
            }
            return (E[]) retArray;
        }
    }
    
    /**
     * 执行sql语句返回多条记录
     * @param sqlID 执行数据库的sqlId
     * @param bindParams 执行sql语句的传入参数
     * @return List<E> 返回的对象List
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <E> List<E> executeForObjectList(String sqlID, Object bindParams) {
        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList Start.");
        }
        log.info(MessageFormat.format("sqlId[{0}]，输入参数：[{1}]", sqlID, bindParams));
        
        StopWatch stopWatch = new StopWatch(sqlID);
        stopWatch.start();

        SqlSession sqlMapTemp = this.getSqlSession();
        List<E> list = sqlMapTemp.selectList(sqlID, bindParams);
        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList End.");
        }

        stopWatch.stop();
        log.info("----------sqlId：" + sqlID + "执行完毕，耗时" + stopWatch.getTotalTimeMillis() + "毫秒----------");

        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format("sqlId[{0}]，返回值：[{1}]", sqlID, list));
        }
        return list;
    }

    /**
     * 执行sql语句返回多条记录，如果有排序条件，按照指定的排序条件排序
     * @param sqlID 执行数据库的sqlId
     * @param bindParams 执行sql语句的传入参数
     * @param orderBy 数据库的排序条件，排序字段是传入参数的dto字段，
     * 不是数据库的真实字段，比如t1.USER_ID AS userID,那么应该传入userId,而不是USER_ID
     * @return List<E> 返回的对象List
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <E> List<E> executeForObjectList(String sqlID, Object bindParams, String orderBy) {
        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList Start.");
        }
        log.info(MessageFormat.format("sqlId[{0}]，输入参数：[{1}]", sqlID, bindParams));
        
        StopWatch stopWatch = new StopWatch(sqlID);
        stopWatch.start();

        SqlSession sqlMapTemp = this.getSqlSession();
        if(orderBy != null && !"".equals(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<E> list = sqlMapTemp.selectList(sqlID, bindParams);
        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList End.");
        }

        stopWatch.stop();
        log.info("----------sqlId：" + sqlID + "执行完毕，耗时" + stopWatch.getTotalTimeMillis() + "毫秒----------");

        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format("sqlId[{0}]，返回值：[{1}]", sqlID, list));
        }
        return list;
    }
    
    /**
     * 描述:根据排序条件获取List对象
     * @param： sqlID 数据库的sqlID
     * @param： bindParams sql语句的查询条件
     * @param： pageInfo 分页条件和排序条件，如果为空或者分页条件不足，则默认使用不分页的查询
     * @author: gc
     * @创建时间: 2017-05-20 11:13:23
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <E> List<E> executeForObjectList(String sqlID, Object bindParams, PageInfo pageInfo) {
        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList Start.");
        }
        log.info(MessageFormat.format("sqlId[{0}]，输入参数：[{1}]", sqlID, bindParams));
        
        StopWatch stopWatch = new StopWatch(sqlID);
        stopWatch.start();

        SqlSession sqlMapTemp = this.getSqlSession();
        if(pageInfo != null) {
            if(pageInfo.getOrderBy() != null && !"".equals(pageInfo.getOrderBy())) {
                PageHelper.orderBy(pageInfo.getOrderBy());
            }
        }
        List<E> list = sqlMapTemp.selectList(sqlID, bindParams);
        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList End.");
        }

        stopWatch.stop();
        log.info("----------sqlId：" + sqlID + "执行完毕，耗时" + stopWatch.getTotalTimeMillis() + "毫秒----------");

        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format("sqlId[{0}]，返回值：[{1}]", sqlID, list));
        }
        return list;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<Map<String, Object>> executeForMapList(String sqlID, Object bindParams) {
        if (log.isDebugEnabled()) {
            log.debug("executeForMapList Start.");
        }
        log.info(MessageFormat.format("sqlId[{0}]，输入参数：[{1}]", sqlID, bindParams));

        List mapList = this.executeForObjectList(sqlID, bindParams);
        if (log.isDebugEnabled()) {
            log.debug("executeForMapList End.");
        }

        return mapList;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<Map<String, Object>> executeForMapList(String sqlID, Object bindParams, PageInfo pageInfo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("executeForMapList Start.");
        }
        //如果分页参数为空或者与分页相关的参数不传，则不调用分页方法
        //2017-12-5 19:57:16-刘运兵-修改：为自定义查询调用的mapList添加分页，之前的都调用不分页的方法！
        if(pageInfo == null
                || pageInfo.getPageSize() == null
                || pageInfo.getPageNum() == null) {
            return this.executeForObjectList(sqlID, bindParams, pageInfo);
        }
        //2017-12-6 10:49:43-刘运兵-修改：为map分页的方法添加排序功能，之前的仅仅能分页不能排序
        //修改参数列表  pageInfo.getPageNum(), pageInfo.getPageSize() 变为：PageInfo pageInfo
        List mapList = this.executeForMapListByPage(sqlID, bindParams, pageInfo);
        if (log.isDebugEnabled()) {
            log.debug("executeForMapList End.");
        }

        return mapList;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <E> List<E> executeForObjectListByPage(String sqlID, Object bindParams, Integer pageNum, Integer pageSize) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("executeForObjectListByPage Start.");
        }

        log.info(MessageFormat.format("sqlId[{0}]，输入参数：[{1}]", sqlID, bindParams));
        StopWatch stopWatch = new StopWatch(sqlID);
        stopWatch.start();
        SqlSession sqlMapTemp = this.getSqlSession();
        
        if (pageSize == null || pageSize < 0) {
            throw new Exception("sqlId：" + sqlID + "的参数的pageNum属性不能为空，并且必须大于0!");
        }
        
        if (pageSize == null || pageSize < 0) {
            throw new Exception("sqlId：" + sqlID + "的参数的pageSize属性不能为空，并且必须大于0!");
        }
        
        PageHelper.startPage(pageNum, pageSize);
        List list = sqlMapTemp.selectList(sqlID, bindParams);
        if (log.isDebugEnabled()) {
            log.debug("executeForObjectListByPage End.");
        }

        stopWatch.stop();
        log.info("----------sqlId：" + sqlID + "执行完毕，耗时" + stopWatch.getTotalTimeMillis() + "毫秒----------");

        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format("sqlId[{0}]，返回值：[{1}]", sqlID, list));
        }
        return list;
    }

    /**
     * 描述:根据查询条件、分页条件、排序条件对数据进行分页操作
     * @param： sqlID 数据库的sqlID
     * @param： bindParams sql语句的查询条件
     * @param： pageInfo 分页条件和排序条件，如果为空或者分页条件不足，则默认使用不分页的查询
     * @author: gc
     * @创建时间: 2017-05-20 11:13:23
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <E> List<E> executeForObjectListByPage(String sqlID, Object bindParams, PageInfo pageInfo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("executeForObjectListByPage Start.");
        }
        
        //如果分页参数为空或者与分页相关的参数不传，则不调用分页方法
        if(pageInfo == null
                || pageInfo.getPageSize() == null
                || pageInfo.getPageNum() == null) {
            return executeForObjectList(sqlID, bindParams, pageInfo);
        }

        log.info(MessageFormat.format("sqlId[{0}]，输入参数：[{1}]", sqlID, bindParams));
        StopWatch stopWatch = new StopWatch(sqlID);
        stopWatch.start();
        SqlSession sqlMapTemp = this.getSqlSession();
        Integer pageSize = pageInfo.getPageSize();
        Integer pageNum = pageInfo.getPageNum();
        if (pageNum == null || pageNum < 0) {
            throw new Exception("sqlId：" + sqlID + "的参数的pageNum属性不能为空，并且必须大于0!");
        }
        
        if (pageSize == null || pageSize < 0) {
            throw new Exception("sqlId：" + sqlID + "的参数的pageSize属性不能为空，并且必须大于0!");
        }
        
        PageHelper.startPage(pageNum, pageSize);
        String orderBy = pageInfo.getOrderBy();
        if(orderBy != null && !"".equals(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List list = sqlMapTemp.selectList(sqlID, bindParams);
        if (log.isDebugEnabled()) {
            log.debug("executeForObjectListByOrderPage End.");
        }

        stopWatch.stop();
        log.info("----------sqlId：" + sqlID + "执行完毕，耗时" + stopWatch.getTotalTimeMillis() + "毫秒----------");

        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format("sqlId[{0}]，返回值：[{1}]", sqlID, list));
        }
        return list;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<Map<String, Object>> executeForMapListByPage(String sqlID, Object bindParams, PageInfo pageInfo) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("executeForMapListByPage Start.");
        }
        log.info(MessageFormat.format("sqlId[{0}]，输入参数：[{1}]", sqlID, bindParams));
        StopWatch stopWatch = new StopWatch(sqlID);
        stopWatch.start();
        Integer pageSize = pageInfo.getPageSize();
        Integer pageNum = pageInfo.getPageNum();
        if (pageNum == null || pageNum < 0) {
            throw new Exception("sqlId：" + sqlID + "的参数的pageNum属性不能为空，并且必须大于0!");
        }
        
        if (pageSize == null || pageSize < 0) {
            throw new Exception("sqlId：" + sqlID + "的参数的pageSize属性不能为空，并且必须大于0!");
        }
        
        PageHelper.startPage(pageNum, pageSize);
        
        List mapList = this.executeForObjectList(sqlID, bindParams, pageInfo);
        if (log.isDebugEnabled()) {
            log.debug("executeForMapListByPage End.");
        }

        stopWatch.stop();
        log.info("----------sqlId：" + sqlID + "执行完毕，耗时" + stopWatch.getTotalTimeMillis() + "毫秒----------");

        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format("sqlId[{0}]，返回值：[{1}]", sqlID, mapList));
        }
        return mapList;
    }
}
