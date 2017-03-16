package com.qihuan.handle;

import com.qihuan.exception.ApiException;
import com.qihuan.tools.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ExceptionHandle
 * Created by Qi on 2017/3/16.
 */
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result errResult(Exception e) {
        if (e instanceof ApiException) {
            return Result.create(((ApiException) e).getCode(), e.getMessage(), null);
        }
        return Result.create(1, e.getMessage(), null);
    }
}
