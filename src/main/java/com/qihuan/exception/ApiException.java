package com.qihuan.exception;

import com.qihuan.tools.ResultEnum;

/**
 * ApiException
 * Created by Qi on 2017/3/16.
 */
public class ApiException extends RuntimeException {

    private Integer code;

    public ApiException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
