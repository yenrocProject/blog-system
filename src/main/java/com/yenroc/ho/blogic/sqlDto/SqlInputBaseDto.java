package com.yenroc.ho.blogic.sqlDto;

import com.yenroc.ho.utils.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
public class SqlInputBaseDto implements Serializable {

    private static final long serialVersionUID = -6012929095434378823L;
    private String createdBy;// SecurityUtils.getCurrentUserLogin();
    private Date createdDate;
    private String lastModifiedBy;//SecurityUtils.getCurrentUserLogin();
    private Date lastModifiedDate;
    private Byte isDel = 0;

    public SqlInputBaseDto() {
    }

    public void initInsert(){
        this.createdBy = "default";// SecurityUtils.getCurrentUserLogin();
        createdDate = DateUtil.getCurDate();
        lastModifiedBy = "default";//SecurityUtils.getCurrentUserLogin();
        lastModifiedDate = DateUtil.getCurDate();
    }

    public void initUpdate(){
        lastModifiedBy = "default";//SecurityUtils.getCurrentUserLogin();
        lastModifiedDate = DateUtil.getCurDate();
    }

    public void initDelete(){
        lastModifiedBy = "default";//SecurityUtils.getCurrentUserLogin();
        lastModifiedDate = DateUtil.getCurDate();
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Byte getIsDel() {
        return this.isDel;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlInputBaseDto)) return false;
        SqlInputBaseDto that = (SqlInputBaseDto) o;
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

    public String toString() {
        return "SqlInputBaseDto(createdBy=" + this.getCreatedBy() + ", createdDate=" + this.getCreatedDate() + ", lastModifiedBy=" + this.getLastModifiedBy() + ", lastModifiedDate=" + this.getLastModifiedDate() + ", isDel=" + this.getIsDel() + ")";
    }
}
