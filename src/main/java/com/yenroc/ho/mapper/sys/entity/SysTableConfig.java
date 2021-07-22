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
@Table(name = "sys_table_config")
public class SysTableConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7245386871063697240L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "table_type")
    private String tableType;

    @Column(name = "namespace")
    private String namespace;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "name")
    private String name;

    @Column(name = "name_desc")
    private String nameDesc;

    @Column(name = "ver")
    private Integer ver;

    @Column(name = "desc")
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameDesc() {
        return nameDesc;
    }

    public void setNameDesc(String nameDesc) {
        this.nameDesc = nameDesc;
    }

    public Integer getVer() {
        return ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SysTableConfig{" +
                "id='" + id + '\'' +
                ", tableType='" + tableType + '\'' +
                ", namespace='" + namespace + '\'' +
                ", tableName='" + tableName + '\'' +
                ", name='" + name + '\'' +
                ", nameDesc='" + nameDesc + '\'' +
                ", ver=" + ver +
                ", desc='" + desc + '\'' +
                '}';
    }
}
