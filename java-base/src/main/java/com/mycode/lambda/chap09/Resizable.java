package com.mycode.lambda.chap09;

/**
 * jf
 * 2018/10/14 15:06
 */
public interface Resizable  extends  Drawable{
    int getWight();
    int getHeight();
    void setWidth(int width);
    void setHeight(int height);
    void setAbsoluteSize(int width, int height);
}
