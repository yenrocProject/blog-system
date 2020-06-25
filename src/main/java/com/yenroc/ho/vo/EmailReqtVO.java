package com.yenroc.ho.vo;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

@Data
public class EmailReqtVO implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2068784055355645547L;
    
    /**
     * 发件人的邮箱的 SMTP 服务器地址
     */
    private String sendEmailSMTPHost;
    
    /**
     * 发件人的账号
     */
    private String sendEmailAccount;
    
    /**
     * 发件人的密码
     */
    private String sendEmailPassword;
    
    /**
     * 发件人昵称
     */
    private String sendPeople;
    
    /**
     * 邮件主题
     */
    private String emailTheme;
    
    /**
     * 邮件正文
     */
    private String emailContent;
    
    /**
     * 收件人集合
     */
    private ArrayList<ReceivePeopleVO> sendPeoples;
}
