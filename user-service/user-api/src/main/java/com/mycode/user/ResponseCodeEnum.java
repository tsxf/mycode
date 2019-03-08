package com.mycode.user;

/**
 * 蛮小江
 * 2018/7/2 11:26
 */
//响应参数校验
public enum ResponseCodeEnum {
    USERORPASSWORD_ERRROR("001001", "用户名或密码不存在"),
    SUCCESS("000000", "成功"),
    SYS_PARAM_NOT_RIGHT("001002", "请求参数错误"),
    SYSTEM_BUSY("001099", "系统繁忙，请稍后尝试");

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private final String code;
    private final String msg;

    ResponseCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
