package com.yenroc.ho.blogic.restDto.demo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class DemoRespS01 implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3631604453770834056L;
    
    private String id;
    
    private String name;
    
    private Integer age;

    private BigDecimal salary;

    private Date birthDay;

}
