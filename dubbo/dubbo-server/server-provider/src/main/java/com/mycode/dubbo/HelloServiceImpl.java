package com.mycode.dubbo;

import com.mycode.dubbo.api.IHello;

/**
 * Hello world!
 *
 */
public class HelloServiceImpl  implements IHello {


    @Override
    public String sayHelo(String name) {
        return "Hello I'm "+name;
    }

}
