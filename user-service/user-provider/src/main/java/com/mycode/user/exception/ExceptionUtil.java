package com.mycode.user.exception;


import com.mycode.user.ResponseCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 蛮小江
 * 2018/7/2 14:20
 */
//异常工具类，转化异常为ServiceException
public class ExceptionUtil {
    private static Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

    public  static   Exception handlerException4biz(Exception e) {
        Exception ex = null;
        if (!(e instanceof Exception)) {
            return null;
        }
        if (e instanceof ValidateException) {
            ex = new ServiceException(((ValidateException) e).getErrorCode(), ((ValidateException) e).getErroorMessage());
            ;
        }
        if (e instanceof ServiceException) {
            ex = new ServiceException(ResponseCodeEnum.SYSTEM_BUSY.getCode(), ResponseCodeEnum.SYSTEM_BUSY.getMsg());
        }
        logger.error("ExceptionUtil.handlerException4biz,Exception=" + e.getMessage(), e);
        return ex;
    }
}
