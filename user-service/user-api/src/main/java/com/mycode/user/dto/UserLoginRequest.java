package com.mycode.user.dto;

import java.io.Serializable;

/**
 * 蛮小江
 * 2018/7/2 11:22
 */
//用户登录需要的参数
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -4163897222983518565L;
    private String userName;
    private  String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
