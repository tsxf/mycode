package com.mycode.user.abs;

import java.io.Serializable;

/**
 * 蛮小江
 * 2018/7/2 11:18
 */
//定义公共的响应参数
public class AbstractResponse implements Serializable {
    private static final long serialVersionUID = -1459361810462953639L;
    private String code;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
