package com.yenroc.ho.blogic.sqlDto.demo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class DemoSQL01IM implements Serializable {
    
    private static final long serialVersionUID = -2319665209202431010L;

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
