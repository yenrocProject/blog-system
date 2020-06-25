package com.yenroc.ho.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReceivePeopleVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7970304188084946475L;

    /**
     * 收件人邮箱
     */
    private String receiveMailAccount;
    
    /**
     * 收件人名称
     */
    private String receivePeople;
}
