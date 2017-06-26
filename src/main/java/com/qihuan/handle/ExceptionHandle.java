package com.qihuan.handle;

import com.qihuan.aspect.HttpAspect;
import com.qihuan.exception.ApiException;
import com.qihuan.tools.Result;
import com.qihuan.tools.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ExceptionHandle
 * Created by Qi on 2017/3/16.
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result errResult(Exception e) {
        LOGGER.warn("[Exception]--->", e);
        if (e instanceof ApiException) {
            return Result.create(((ApiException) e).getCode(), e.getMessage(), null);
        }
        return Result.create(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg(), null);
    }
}
