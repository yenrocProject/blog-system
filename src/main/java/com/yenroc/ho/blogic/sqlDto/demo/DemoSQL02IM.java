package com.yenroc.ho.blogic.sqlDto.demo;

import com.yenroc.ho.blogic.sqlDto.SqlInputBaseDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class DemoSQL02IM extends SqlInputBaseDto implements Serializable {
    
    private static final long serialVersionUID = -2241966520310392010L;

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
