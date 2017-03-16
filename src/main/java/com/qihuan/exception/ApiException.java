package com.qihuan.exception;

/**
 * ApiException
 * Created by Qi on 2017/3/16.
 */
public class ApiException extends RuntimeException {

    private Integer code;

    public ApiException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
