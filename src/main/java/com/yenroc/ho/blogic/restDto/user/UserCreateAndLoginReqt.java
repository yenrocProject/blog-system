package com.yenroc.ho.blogic.restDto.user;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserCreateAndLoginReqt implements Serializable {

    private static final long serialVersionUID = -3425847102168704004L;

    private String userName;

    private String password;

}
