package com.qihuan.tools;

/**
 * ResultEnum
 * Created by Qi on 2017/3/16.
 */
public enum ResultEnum {
    SUCCESS(0, "成功"),
    UNKNOWN_ERROR(1, "未知错误"),
    USER_EXISTED(2, "用户已存在"),
    USER_NO_EXIST(3, "用户不存在");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
