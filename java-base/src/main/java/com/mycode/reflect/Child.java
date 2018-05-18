package com.mycode.reflect;

/**
 * Created by 江富 on 2018/5/2
 */
public class Child extends  Person {
    public  String scholl;
    public String course;
    private String nickName;
    String loveGirl;

    public Child() {

    }

    public Child(String scholl, String course, String nickName, String loveGirl) {
        this.scholl = scholl;
        this.course = course;
        this.nickName = nickName;
        this.loveGirl = loveGirl;
    }

    public String getScholl() {
        return scholl;
    }

    public void setScholl(String scholl) {
        this.scholl = scholl;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLoveGirl() {
        return loveGirl;
    }

    public void setLoveGirl(String loveGirl) {
        this.loveGirl = loveGirl;
    }
}
