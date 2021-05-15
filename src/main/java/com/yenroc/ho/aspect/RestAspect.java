package com.yenroc.ho.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 接口请求的日志记录
 */
@Aspect
@Component
public class RestAspect {
    
    private final Logger log = LoggerFactory.getLogger(RestAspect.class);

    @Pointcut("execution(* com.yenroc.ho.rest.*.*(..))")
    public void pointCut() {

    }
    
    @Around("pointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Enter: {}.{}() with argument[s] = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
        try {
            long st = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            log.info("Exit: {}.{}() 耗时=[{}ms]with result = {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    (System.currentTimeMillis() - st),
                    result);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", 
                    Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), 
                    joinPoint.getSignature().getName());
            throw e;
        }
    }
    

}
