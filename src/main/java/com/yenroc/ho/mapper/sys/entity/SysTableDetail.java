package com.yenroc.ho.mapper.sys.entity;

import com.yenroc.ho.mapper.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author： heyanpeng
 * @date： 2021/7/19
 */
@Table(name = "sys_table_detail")
public class SysTableDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7245386871063697240L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "table_config_id")
    private String tableConfigId;

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "column_type")
    private String columnType;

    @Column(name = "column_comment")
    private String columnComment;

    @Column(name = "is_not_null")
    private Integer isNotNull;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field_type")
    private String fieldType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableConfigId() {
        return tableConfigId;
    }

    public void setTableConfigId(String tableConfigId) {
        this.tableConfigId = tableConfigId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public Integer getIsNotNull() {
        return isNotNull;
    }

    public void setIsNotNull(Integer isNotNull) {
        this.isNotNull = isNotNull;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public String toString() {
        return "SysTableDetail{" +
                "id='" + id + '\'' +
                ", tableConfigId='" + tableConfigId + '\'' +
                ", columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", columnComment='" + columnComment + '\'' +
                ", isNotNull=" + isNotNull +
                ", defaultValue='" + defaultValue + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                '}';
    }
}
