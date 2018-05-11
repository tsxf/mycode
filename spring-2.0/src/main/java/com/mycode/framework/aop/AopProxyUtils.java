package com.mycode.framework.aop;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * 蛮小江
 * 2018/5/10 10:02
 */
public class AopProxyUtils {

    /**
     * 获取到原始对象
     * @param proxy
     * @return
     * @throws Exception
     */
    public  static  Object getTargetObject(Object proxy) throws  Exception{
        if(!isAopProxy(proxy)){
            return  proxy;
        }
            return  getProxyTargetObject( proxy);
    }

    private static Object getProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        //取消h访问控制检查
        h.setAccessible(true);
        //获取到proxy字段的值，这是一个InvocationHandler子类
        AopProxy handler =(AopProxy)h.get(proxy);
        Field target = handler.getClass().getDeclaredField("target");
        //取消target访问控制检查
        target.setAccessible(true);
        return target.get(handler);
    }

    private  static boolean  isAopProxy(Object proxy){
        return Proxy.isProxyClass(proxy.getClass());
    }
}
