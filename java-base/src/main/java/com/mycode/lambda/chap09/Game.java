package com.mycode.lambda.chap09;


import java.util.Arrays;
import java.util.List;

/**
 * jf
 * 2018/10/14 15:10
 */
public class Game {
    public static void main(String [] args){
        List<Resizable> resizableShapes = Arrays.asList(new Square(), new Triangle(),new com.mycode.lambda.chap09.Ellipse());
        Utils.painit(resizableShapes);
    }
}
