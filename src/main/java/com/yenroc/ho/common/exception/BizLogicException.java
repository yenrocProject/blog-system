package com.yenroc.ho.common.exception;

import com.yenroc.ho.common.bean.SystemMessage;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * 描述: 异常处理
 * @author hyp
 * @date 2018年7月22日21:36:34
 * @version v0.1
 */
public class BizLogicException extends Exception implements Serializable {

    private static final long serialVersionUID = -5458946175270060619L;
    
    private ArrayList<SystemMessage> messageList = new ArrayList<SystemMessage>();
    
    public ArrayList<SystemMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(ArrayList<SystemMessage> messageList) {
        this.messageList = messageList;
    }

    public BizLogicException(ArrayList<SystemMessage> messageList) {
        super(messageList.get(0).getMessage());
        this.messageList = messageList;
    }
    
    public BizLogicException(SystemMessage systemMessage) {
        super(systemMessage.getMessage());
        if(systemMessage != null) {
            this.messageList.add(systemMessage);
        }
    }
}
