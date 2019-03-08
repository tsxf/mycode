package com.mycode.user.exception;

import com.mycode.user.ResponseCodeEnum;

/**
 * 蛮小江
 * 2018/7/2 14:06
 */
//参数校验不合法，抛出的异常
public class ValidateException extends Exception {

    private static final long serialVersionUID = -3375541841582331511L;
    private String errorCode;
    private String erroorMessage;

    public ValidateException() {
        super();
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

    public ValidateException(String errorCode) {
        super(errorCode);
        this.errorCode = ResponseCodeEnum.SYS_PARAM_NOT_RIGHT.getCode();
        this.erroorMessage = ResponseCodeEnum.SYS_PARAM_NOT_RIGHT.getMsg();
    }

    public ValidateException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ValidateException(String errorCode, String erroorMessage) {
        super();
        this.errorCode = errorCode;
        this.erroorMessage = erroorMessage;
    }


    public ValidateException(Throwable cause, String errorCode, String erroorMessage) {
        super(cause);
        this.errorCode = errorCode;
        this.erroorMessage = erroorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErroorMessage() {
        return erroorMessage;
    }

    public void setErroorMessage(String erroorMessage) {
        this.erroorMessage = erroorMessage;
    }

    @Override
    public String toString() {
        return "ValidateException{" +
                "errorCode='" + errorCode + '\'' +
                ", erroorMessage='" + erroorMessage + '\'' +
                '}';
    }
}
