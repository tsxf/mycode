package com.mycode.lambda.chap09;

import java.util.List;

/**
 * jf
 * 2018/10/14 15:10
 */
public class Utils {
    public static void painit(List<Resizable> list) {
        list.forEach(r->r.setAbsoluteSize(42,42));
    }
}
