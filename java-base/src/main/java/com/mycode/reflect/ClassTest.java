package com.mycode.reflect;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;


/**
 *
 */
public class ClassTest {

    /**
     *
     */
    @Test
    public void testReflect() {
        // 拿到Class

        //1.获取并输出类的名称
        Class<Child> mClass = Child.class;
        System.out.println("类的名称：" + mClass.getName());

        //2.1 获取所有 public 访问权限的方法
        //包括自己声明和从父类继承的
        Method[] mMethods = mClass.getMethods();

        //2.2 获取所有本类的的方法（不问访问权限）
        //Method[] mMethods = mClass.getDeclaredMethods();

        //3.遍历所有方法
        for (Method method : mMethods) {
            //获取并输出方法的访问权限（Modifiers：修饰符）
            int modifiers = method.getModifiers();
            System.out.print(Modifier.toString(modifiers) + " ");
            //获取并输出方法的返回值类型
            Class returnType = method.getReturnType();
            System.out.print(returnType.getSimpleName() + " "
                    + method.getName() + "( ");
            //获取并输出方法的所有参数
            Parameter[] parameters = method.getParameters();
            String str = "";
            for (Parameter parameter : parameters) {
                str = parameter.getType().getSimpleName()
                        + " " + parameter.getName() + ",";

            }
            if (!str.isEmpty()) {
                System.out.print(str.substring(0, str.lastIndexOf(",")));
            }

           /* Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class clazz : parameterTypes) {
                System.out.println(clazz.getSimpleName() + " " + clazz.getTypeParameters() + ",");
            }*/

            //获取并输出方法抛出的异常
            Class[] exceptionTypes = method.getExceptionTypes();
            if (exceptionTypes.length == 0) {
                System.out.println(" )");
            } else {
                for (Class c : exceptionTypes) {
                    System.out.println(" ) throws "
                            + c.getSimpleName());
                }
            }
        }


    }

    /**
     *  测试Class
     * @throws ClassNotFoundException  Class未找到异常
     * @throws IllegalAccessException 非法访问异常
     * @throws InstantiationException 初始化失败异常
     */
    @Test
    public void testClass() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //Class不能有应用程序直接创建，只能是jvm自己在运行时创建
        //Class是对应用程序自己创建的类在运行时的一个抽象
        //获取Class方式
        //1. 类加载器ClassLoader获取defineClass()方法,这个方法接受一个byte数组，载入这个byte数组否成的class类，同时实例化一个Class对象。
        ClassLoader classLoader = ClassTest.class.getClassLoader();
        java.lang.Class<?> aClass2 = classLoader.loadClass("com.mycode.reflect.ClassTest");
        System.out.println(aClass2);
        //2. 类名字.class 隐含的静态方法
        java.lang.Class<Thread> threadClass = Thread.class;
        //调用Class的toString方法() class + getName()
        System.out.println(threadClass);
        Thread thread = threadClass.newInstance();
        System.out.println(thread.getId());

        java.lang.Class<Void> voidClass = Void.class;
        System.out.println(voidClass);
        //调用接口Interface interface+ getName()
        java.lang.Class<InvocationHandler> invocationHandlerClass = InvocationHandler.class;
        System.out.println(invocationHandlerClass);
        //3. 对象.getClass()和上面的.class是不是get,set方法？
        String str = new String();
        java.lang.Class<? extends String> aClass = str.getClass();
        System.out.println(aClass);
        //4. 通过包名的全路径加载,连接实例对象，返回Class
        java.lang.Class<?> aClass1 = java.lang.Class.forName("com.mycode.reflect.ClassTest");
        System.out.println(aClass1);
        //获取父类Class
        System.out.println(aClass1.getSuperclass());


        //反射在运行时，能够获取这个类的所有属性和方法，可以根据一个class文件，jvm进行加载，拿到Class就可以获取到其完成构造，可以给属性赋值，调用其方法
    }
}
