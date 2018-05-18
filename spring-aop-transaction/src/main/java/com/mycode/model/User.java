package com.mycode.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 蛮小江
 * 2018/5/17 10:37
 */
@Data
public class User {
    private String id;
    private  String naem;
    private String email;
    @Getter
    @Setter
    private int age = 10;
}
