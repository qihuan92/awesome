package com.qihuan.aspect;

import com.qihuan.exception.ApiException;
import com.qihuan.tools.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAOP {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAOP.class);

    @Pointcut("execution(public * com.qihuan.controller.*.*(..))")
    public void controllerMethodPointcut() {

    }

    @Around("controllerMethodPointcut()")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();
        Result<?> result;
        try {
            result = (Result<?>) pjp.proceed();
            LOGGER.info("[Request ]--->");
            LOGGER.info("[method  ] = {}", pjp.getSignature());
            LOGGER.info("[use time] = {}", System.currentTimeMillis() - startTime);
        } catch (Throwable e) {
            result = handlerException(pjp, e);
        }
        return result;
    }

    private Result<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        Result<?> result = new Result();
        if (e instanceof ApiException) {
            // 已知异常
            result.setMsg(e.getMessage());
            result.setCode(((ApiException) e).getCode());
        } else {
            // 未知异常
            LOGGER.error("[Exception]--->");
            LOGGER.error("[method   ]--->{}", pjp.getSignature());
            LOGGER.error("[error    ]--->{}", e);
            result.setMsg(e.toString());
            result.setCode(1);
        }
        return result;
    }
}