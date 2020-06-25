package com.yenroc.ho.common.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.yenroc.ho.common.consts.CommonConsts;

import com.yenroc.ho.common.context.BLogContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ResponseResult implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -5245734143804524050L;
    /**
     * 请求的线程Id
     */
    @ApiModelProperty(value="请求的线程Id")
    private String threadId;

    /**
     * 返回的消息code，200--正常，300--异常
     */
    @ApiModelProperty(value="返回的消息code，200--正常，300--异常")
    private String responseCode = CommonConsts.RESPONSE_CODE_SUCCESS;

    /**
     * 返回的消息list
     */
    @ApiModelProperty(value="返回的消息list")
    private ArrayList<SystemMessage> messageList = new ArrayList<SystemMessage>();

    /**
     * 分页DTO
     */
    private PageInfo pageInfo;

    /**
     * 返回结果实体数据载体
     */
    @ApiModelProperty(value="返回结果实体数据载体")
    private Object dataResult;


    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public ArrayList<SystemMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(ArrayList<SystemMessage> messageList) {
        this.messageList = messageList;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public Object getDataResult() {
        return dataResult;
    }

    public void setDataResult(Object dataResult) {
        this.dataResult = dataResult;
    }

    public ResponseResult() {
        this.threadId = BLogContext.getValue(CommonConsts.THREAD_ID).toString();
    }

    public ResponseResult(String responseCode, Object dataResult) {
        this.threadId = BLogContext.getValue(CommonConsts.THREAD_ID).toString();
        this.pageInfo = (PageInfo) BLogContext.getValue(CommonConsts.PAGE_INFO);
        this.responseCode = responseCode;
        this.dataResult = dataResult;
    }

    public ResponseResult(String responseCode, ArrayList<SystemMessage> messageList, Object dataResult) {
        this.threadId = BLogContext.getValue(CommonConsts.THREAD_ID).toString();
        this.pageInfo = (PageInfo) BLogContext.getValue(CommonConsts.PAGE_INFO);
        this.responseCode = responseCode;
        this.messageList = messageList;
        this.dataResult = dataResult;
    }

    /**
     * 返回执行成功信息结果
     * @param dataResult
     * @return
     */
    public static ResponseResult success(Object dataResult) {
        return new ResponseResult(CommonConsts.RESPONSE_CODE_SUCCESS, dataResult);
    }

    /**
     * 返回异常信息结果
     * @param dataResult
     * @return
     */
    public static ResponseResult fail(Object dataResult) {
        return new ResponseResult(CommonConsts.RESPONSE_CODE_SUCCESS, dataResult);
    }

    /**
     * 返回错误信息结果
     * @param dataResult
     * @return
     */
    public static ResponseResult error(Object dataResult) {
        return new ResponseResult(CommonConsts.RESPONSE_CODE_ERROR, dataResult);
    }

    /**
     * 添加Message
     * @param messageList
     */
    public void addMessage(ArrayList<SystemMessage> messageList){
        messageList.addAll(messageList);
    }

    /**
     * 添加Message
     * @param message
     */
    public void addMessage(SystemMessage message){
        messageList.add(message);
    }
}
