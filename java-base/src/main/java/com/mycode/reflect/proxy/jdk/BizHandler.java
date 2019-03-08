package com.mycode.reflect.proxy.jdk;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018/5/7 8:40
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class BizHandler implements InvocationHandler {
    private Target target;
    public BizHandler(Target target) {
        this.target = target;
    }

    public void generateProxyClass() throws  Exception{
     /*   byte[] classFile = ProxyGenerator.generateProxyClass("com.sun.proxy.$Proxy.1", target.getClass().getInterfaces());
        FileOutputStream out = new FileOutputStream("com.sun.proxy.$Proxy.1.class");
        out.write(classFile);
        out.flush();*/


    }

    public TargetService getProxy(){
        return  (TargetService)Proxy.newProxyInstance(Client.class.getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("=====A======");
        Method proxyMethod = proxy.getClass().getMethod("doBiz", String.class);
        Object invoke = method.invoke(target, args);
        System.out.println("=====B======");
        return invoke;
    }
}
