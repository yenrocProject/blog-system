package com.yenroc.ho.blogic.java.common;

import com.yenroc.ho.common.bean.SystemMessage;
import com.yenroc.ho.common.exception.BizLogicException;
import com.yenroc.ho.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 统一开发查询的接口Service 抽象实现
 */
public class AbstractCommonService implements CommonService {

    private static final Logger log = LoggerFactory.getLogger(AbstractCommonService.class);


    /**
     * 对象实体类的包路径
     */
    private final String ENTITY_PACKAGE_PATH = "com.yenroc.ho.mapper.entity";

    private static Map<String, String> tableNameMap = new HashMap<>();

    /**
     * 验证表名是否存在,如果存在返回表名对应的Table 类实体的路径
     * @param tableName
     * @return
     * @throws BizLogicException
     */
    protected String checkTableName(String tableName) throws BizLogicException {
        if (StringUtils.isBlank(tableName)) {
            throw new BizLogicException(new SystemMessage("ERROR", String.format("调用接口=[%s]异常", tableName)));
        }
        // 解析tableName, 将首字母转大写
        tableName = StringUtil.firstCharToUpperCase(tableName);

        if (tableNameMap.containsKey(tableName)) {
            return tableNameMap.get(tableName);
        }
        String classPath = String.format("%s.%s", ENTITY_PACKAGE_PATH, tableName);
        try {
            Class.forName(classPath);
        } catch (ClassNotFoundException ex) {
            log.error("表名=[{}]对应的实体类路径不能找到Class.", tableName, ex);
            throw new BizLogicException(new SystemMessage("ERROR", String.format("调用接口=[%s]异常", tableName)));
        }
        tableNameMap.put(tableName, classPath);
        return classPath;
    }

    @Override
    public <T> List<T> select(String tableName, Map<String, Object> params) throws Exception {
        return null;
    }

    @Override
    public String insert(String tableName, Map<String, Object> params) throws Exception {
        return null;
    }

    @Override
    public int update(String tableName, Map<String, Object> params) throws Exception {
        return 0;
    }

    @Override
    public int delete(String tableName, Map<String, Object> params) throws Exception {
        return 0;
    }


}
