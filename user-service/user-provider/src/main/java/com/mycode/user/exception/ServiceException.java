package com.mycode.user.exception;

/**
 * 蛮小江
 * 2018/7/2 14:14
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -7524840018774113788L;
    /**返回码*/
    private String errorCode;
    /**信息*/
    private String errorMessage;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String errorCode,String errorMessage) {
       super();
       this.errorCode = errorCode;
       this.errorMessage = errorMessage;
    }

    public ServiceException(String errorCode,String errorMessage,Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }




    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
