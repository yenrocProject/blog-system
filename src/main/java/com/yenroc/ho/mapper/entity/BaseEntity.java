package com.yenroc.ho.mapper.entity;

import com.yenroc.ho.utils.DateUtil;

import javax.persistence.Column;
import java.util.Date;
import java.util.Objects;

public class BaseEntity {

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @Column(name = "is_del")
    private Byte isDel = 0;

    public BaseEntity() {

    }

    public void initInsert(){
        this.createdBy = "default";// SecurityUtils.getCurrentUserLogin();
        this.createdDate = DateUtil.getCurDate();
        this.lastModifiedBy = "default";//SecurityUtils.getCurrentUserLogin();
        this.lastModifiedDate = DateUtil.getCurDate();
    }

    public void initUpdate(){
        this.lastModifiedBy = "default";//SecurityUtils.getCurrentUserLogin();
        this.lastModifiedDate = DateUtil.getCurDate();
    }

    public void initDelete(){
        this.lastModifiedBy = "default";//SecurityUtils.getCurrentUserLogin();
        this.lastModifiedDate = DateUtil.getCurDate();
        this.isDel = 1;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
                Objects.equals(isDel, that.isDel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdBy, createdDate, lastModifiedBy, lastModifiedDate, isDel);
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", isDel=" + isDel +
                '}';
    }
}
