package com.yenroc.ho.blogic.restDto.demo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class DemoReqt implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3425847102168704004L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 工资
     */
    private BigDecimal salary;

    /**
     * 出生日期
     */
    private Date birthDay;

}
