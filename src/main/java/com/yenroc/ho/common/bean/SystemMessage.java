package com.yenroc.ho.common.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SystemMessage implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2025489854493054746L;

    /**
     * 消息key
     */
    @ApiModelProperty(value="消息key")
    private String key;
    
    /**
     * 消息内容
     */
    @ApiModelProperty(value="消息内容")
    private String message;
    
    public SystemMessage() {
    }
    
    public SystemMessage(String key, String message) {
        this.key = key;
        this.message = message;
    }
}
