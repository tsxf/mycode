package com.mycode.user.dto;

import com.mycode.user.abs.AbstractResponse;

/**
 * 蛮小江
 * 2018/7/2 11:20
 */
//用户登录响应内容
public class UserLoginResponse extends AbstractResponse {
    private static final long serialVersionUID = 9212283224683239679L;
    private String userName;
    private Integer uid;
    private  String avatar;
    private String mobile;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
