package com.yenroc.ho.blogic.restDto.user;

import lombok.Data;

import java.io.Serializable;


@Data
public class UserCreateAndLoginResp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3631604453770834056L;

    private Integer id;

    private String userName;

}
