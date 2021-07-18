package com.yenroc.ho.mapper.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Table(name="test_demo")
public class TestDemo extends BaseEntity{

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TestDemo testDemo = (TestDemo) o;
        return Objects.equals(id, testDemo.id) &&
                Objects.equals(name, testDemo.name) &&
                Objects.equals(age, testDemo.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, age);
    }

    @Override
    public String toString() {
        return "TestDemo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
