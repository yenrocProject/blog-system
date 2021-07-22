package com.yenroc.ho.mapper.entity;

import com.yenroc.ho.mapper.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Table(name="demo")
public class Demo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7245386871063697240L;

    /**
     * 主键id
     */
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 年龄
     */
    @Column(name = "age")
    private Integer age;

    /**
     * 工资
     */
    @Column(name = "salary")
    private BigDecimal salary;

    /**
     * 出生日期
     */
    @Column(name = "birthDay")
    private Date birthDay;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }


}
