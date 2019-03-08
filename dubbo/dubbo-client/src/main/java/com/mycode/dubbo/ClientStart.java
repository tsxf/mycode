package com.mycode.dubbo;

import com.alibaba.dubbo.common.Extension;
import com.alibaba.dubbo.common.extension.ExtensionFactory;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;
import com.mycode.dubbo.api.IDemoService;
import com.mycode.dubbo.api.IHello;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Hello world!
 */
public class ClientStart {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-client.xml");
        IHello hello = (IHello) context.getBean("helloService");
        System.out.println("================================");
        System.out.println(hello.sayHelo("neo"));
        System.out.println("================================");
        IDemoService demoService = (IDemoService) context.getBean("demoService");
        System.out.println(demoService.protocolDemo("chenchao"));

    }

    @BeforeEach
    public  void before(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-client.xml");
        context.start();
    }
    @Test
    public  void testExtensionFactoryLoader() throws IOException {
        ExtensionLoader<ExtensionFactory> loader = ExtensionLoader.getExtensionLoader(ExtensionFactory.class);
        System.out.println(loader.getAdaptiveExtension());
        System.in.read();
    }

    @Test
    public  void testProtocolLoader() throws IOException {
        ExtensionLoader<Protocol> loader = ExtensionLoader.getExtensionLoader(Protocol.class);
        Protocol protocol = loader.getAdaptiveExtension();
        System.out.println(protocol);
        System.out.println(protocol.getDefaultPort());
        System.in.read();

    }
}
