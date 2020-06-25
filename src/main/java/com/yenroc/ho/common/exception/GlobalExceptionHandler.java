package com.yenroc.ho.common.exception;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletResponse;

import com.yenroc.ho.common.consts.CommonConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.yenroc.ho.common.bean.ResponseResult;
import com.yenroc.ho.common.bean.SystemMessage;

/**
 * 描述:全局异常处理
 * 参考地址：http://www.cnblogs.com/java-zhao/archive/2016/08/13/5769018.html
 * 
 * @ControllerAdvice是controller的一个辅助类，最常用的就是作为全局异常处理的切面类
 * @ControllerAdvice可以指定扫描范围
 * @ControllerAdvice约定了几种可行的返回值，如果是直接返回model类的话，
 * 需要使用@ResponseBody进行json转换 返回String，表示跳到某个view 返回modelAndView 返回model + @ResponseBody
 * @author: 和彦鹏
 * @date: 和2018年7月22日22:24:53
 * @version:v0.1
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * 日志
     */
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler//处理所有异常
    @ResponseBody // 在返回自定义相应类的情况下必须有，这是@ControllerAdvice注解的规定
    public ResponseResult exceptionHandler(Exception e, HttpServletResponse response) {
        ResponseResult resp = new ResponseResult();
        
        if(e instanceof BizLogicException) {
            BizLogicException bizLogicException = (BizLogicException)e;
            resp.setResponseCode(CommonConsts.RESPONSE_CODE_WARNING);
            resp.setMessageList(bizLogicException.getMessageList());
        }
        // 验证出错
        else if(e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException)e;
            BindingResult result = (BindingResult) methodArgumentNotValidException.getBindingResult();
            if (result.hasErrors()) {
                ResponseResult responseResult = new ResponseResult();
                for (ObjectError error : result.getAllErrors()) {
                    responseResult.addMessage(new SystemMessage(error.getCodes()[0], error.getDefaultMessage()));
                }
                resp.setResponseCode(CommonConsts.RESPONSE_CODE_WARNING);
                resp.setMessageList(responseResult.getMessageList());
            }
        }
        
      //jacksonMapper转换出错
        else if(e.getCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException)e.getCause();
            ResponseResult responseResult = new ResponseResult();
            // 字段[{0}]的值[{1}]不能转换成[{2}]类型
            responseResult.addMessage(new SystemMessage("ClassCaseException",
                    MessageFormat.format("字段[{0}]的值[{1}]不能转换成[{2}]类型", new Object[]{
                            invalidFormatException.getPathReference(),//字段
                            invalidFormatException.getValue(),//值
                            invalidFormatException.getTargetType().getName()})));
            resp.setResponseCode(CommonConsts.RESPONSE_CODE_WARNING);
            resp.setMessageList(responseResult.getMessageList());
        }
        else if(e instanceof HttpRequestMethodNotSupportedException) {
            resp.setResponseCode(CommonConsts.RESPONSE_CODE_WARNING);
            resp.addMessage(new SystemMessage("SERVER_ERROR", "访问资源有误，请确认资源路径。"));
        }
        else {
            resp.setResponseCode(CommonConsts.RESPONSE_CODE_WARNING);
            resp.addMessage(new SystemMessage("SERVER_ERROR_INFO", e.toString()));
        }
        log.error(e.getMessage(), e);
        return resp;
    }
    
}
