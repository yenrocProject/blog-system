package com.yenroc.ho.mapper.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Table(name="del_data")
public class DelData extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7245386871063697240L;

    /**
     * 主键id
     */
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "data_info", columnDefinition = "json")
    private String dataInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DelData delData = (DelData) o;
        return Objects.equals(id, delData.id) &&
                Objects.equals(tableName, delData.tableName) &&
                Objects.equals(dataInfo, delData.dataInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, tableName, dataInfo);
    }

    @Override
    public String toString() {
        return "DelData{" +
                "id='" + id + '\'' +
                ", tableName='" + tableName + '\'' +
                ", dataInfo='" + dataInfo + '\'' +
                '}';
    }
}
