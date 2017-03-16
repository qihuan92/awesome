package com.qihuan.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpAspect
 * Created by Qi on 2017/3/16.
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.qihuan.controller.*.*(..))")
    public void log() {

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        LOGGER.info("Request----->");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // url
        LOGGER.info("url={}", request.getRequestURL());
        // method
        LOGGER.info("method={}", request.getMethod());
        // ip
        LOGGER.info("ip={}", request.getRemoteAddr());
        // class_method
        LOGGER.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        // args
        LOGGER.info("args={}", joinPoint.getArgs());
    }

    @After("log()")
    public void doAfter() {

    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        LOGGER.info("response={}", object.toString());
        LOGGER.info("----->Finish");
    }
}
