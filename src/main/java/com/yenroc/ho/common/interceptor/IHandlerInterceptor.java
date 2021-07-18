package com.yenroc.ho.common.interceptor;

import com.yenroc.ho.common.bean.PageInfo;
import com.yenroc.ho.common.consts.CommonConsts;
import com.yenroc.ho.common.context.BLogContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * preHandle：在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理；
 * postHandle：在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染），有机会修改ModelAndView （这个博主就基本不怎么用了）；
 * afterCompletion：在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面）；
 */
public class IHandlerInterceptor implements HandlerInterceptor {
    private final static Logger log = LoggerFactory.getLogger(IHandlerInterceptor.class);
    public IHandlerInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!StringUtils.isEmpty(request.getHeader(CommonConsts.THREAD_ID))) {
            BLogContext.setValue(CommonConsts.THREAD_ID, request.getHeader(CommonConsts.THREAD_ID));
            Thread.currentThread().setName(request.getHeader(CommonConsts.THREAD_ID));
        } else {
            String logId = (String) BLogContext.getValue(CommonConsts.THREAD_ID);
            if (StringUtils.isEmpty(logId)) {
                logId = System.currentTimeMillis() + UUID.randomUUID().toString().toUpperCase().substring(0, 5);
                BLogContext.setValue(CommonConsts.THREAD_ID, logId);
                Thread.currentThread().setName(logId);
            }
        }
        log.info("request请求地址path=[{}],uri=[{}]", request.getServletPath(), request.getRequestURI());
        // 从request里获取分页信息与order信息, 获取的顺序为header,parameter
        // 只要从header或parameter里面获取到pageIndex，pageSize，orderBy任意一个值，则创建PageControllerInfo，否则不设定值
        if(StringUtils.isEmpty(request.getHeader(CommonConsts.PAGE_INDEX))
                && StringUtils.isEmpty(request.getHeader(CommonConsts.PAGE_SIZE))
                && StringUtils.isEmpty(request.getHeader(CommonConsts.ORDER_BY))
                && StringUtils.isEmpty(request.getParameter(CommonConsts.PAGE_INDEX))
                && StringUtils.isEmpty(request.getParameter(CommonConsts.PAGE_SIZE))
                && StringUtils.isEmpty(request.getParameter(CommonConsts.ORDER_BY))) {
            return true;
        }
        // 创建PageControllerInfo
        PageInfo pageInfo = new PageInfo();
        if(!StringUtils.isEmpty(request.getHeader(CommonConsts.PAGE_INDEX))) {
            pageInfo.setPageNum(Integer.valueOf(request.getHeader(CommonConsts.PAGE_INDEX)) + 1);
        }
        if(!StringUtils.isEmpty(request.getHeader(CommonConsts.PAGE_SIZE))) {
            pageInfo.setPageSize(Integer.valueOf(request.getHeader(CommonConsts.PAGE_SIZE)));
        }
        ifOrderBy(request.getHeader(CommonConsts.ORDER_BY), pageInfo);

        if(pageInfo.getPageNum() == null
                && !StringUtils.isEmpty(request.getParameter(CommonConsts.PAGE_INDEX))) {
            pageInfo.setPageNum(Integer.valueOf(request.getParameter(CommonConsts.PAGE_INDEX)) + 1);
        }
        if(pageInfo.getPageSize() == null
                && !StringUtils.isEmpty(request.getParameter(CommonConsts.PAGE_SIZE))) {
            pageInfo.setPageSize(Integer.valueOf(request.getParameter(CommonConsts.PAGE_SIZE)));
        }
        if (StringUtils.isBlank(pageInfo.getOrderBy())) {
            ifOrderBy(request.getParameter(CommonConsts.ORDER_BY), pageInfo);
        }
        //将数据设置到BLContext里
        BLogContext.setValue(CommonConsts.PAGE_INFO, pageInfo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private static String MATCHER = "^(\\w+\\s*\\.\\s*)*\\w+(\\s+(desc|asc|DESC|ASC))?(\\s*,(\\s*\\w+\\s*\\.)*\\s*\\w+(\\s+(asc|desc|DESC|ASC))?)*";
    private static void ifOrderBy(String OrderByVal, PageInfo pageInfo) {
        if(StringUtils.isEmpty(OrderByVal)) {
            return;
        }
        Pattern pattern = Pattern.compile(MATCHER);
        Matcher matcher = pattern.matcher(OrderByVal);
        //先判断是否匹配正则 在判断匹配后正则跟orderBy是否相等
        if(matcher.matches()){
            if((matcher.find() && OrderByVal.equals(matcher.group()))){
                pageInfo.setOrderBy(OrderByVal);
            } else {
                log.error("orderBy 存在注入风险，舍去orderBy！");
            }
        } else {
            log.error("orderBy 存在注入风险，舍去orderBy！");
        }
    }

}
