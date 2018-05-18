package com.mycode.reflect.proxy.custom;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018/5/8 14:45
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class MyProxy {
    public static Object newProxyInstance(MyClassLoader loader,
                                          Class<?>[] interfaces,
                                          MyInvocationHandler h) throws Exception {

        //生成字符串型，代理类代码.java
        String sourceConde = generateSourceConde(interfaces);

        //把字符串值写入到磁盘
        String path = MyProxy.class.getResource("").getPath();
        System.out.println(path);

        File file =    new File(path + "$Proxy0.java");

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(sourceConde);
        fileWriter.flush();
        fileWriter.close();

        //调用java编译器编译.java文件为.class文件
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = javaCompiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> iterable = manager.getJavaFileObjects(file);
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, manager, null, null, null, iterable);
        task.call();
        manager.close();


        //jvm加载class文件
        Class<?> proxy = loader.findClass("$Proxy0");

        //通过反射拿到Class对象,通过指定类型的构造器，构造返回一个代理对象
        Constructor<?> constructor = proxy.getConstructor(MyInvocationHandler.class);
        //删除.java文件
        file.delete();
        return constructor.newInstance(h);


    }

    private  final static String CR = "\r\n";
    private static String generateSourceConde( Class<?>[] interfaces) {
        StringBuilder sb = new StringBuilder();
        sb.append("package com.mycode.reflect.proxy.custom;");
        sb.append(CR);
        sb.append("import com.mycode.reflect.proxy.custom.TargetService;");
        sb.append(CR);
        sb.append("import com.mycode.reflect.proxy.custom.MyInvocationHandler;");
        sb.append(CR);
        sb.append("import java.lang.reflect.Method;");
        sb.append(CR);
        //导包

        //构造器
        sb.append("public class $Proxy0 implements "+interfaces[0].getName()+"{");
        sb.append(CR);
        sb.append("private MyInvocationHandler h;");
        sb.append(CR);
        sb.append("public $Proxy0(MyInvocationHandler h){");
        sb.append(CR);
        sb.append("this.h = h;");
        sb.append(CR);
        sb.append("}");
        sb.append(CR);

        //公共方法
        for (Method m : interfaces[0].getMethods()) {
            sb.append("@Override");
            sb.append(CR);
            sb.append("public "+m.getReturnType().getName()+"  "+m.getName()+"( "+m.getParameters()[0].getType().getName()+"  "+m.getParameters()[0].getName()+"){");
            sb.append(CR);
            sb.append("String result = null;");
            sb.append("try{");
            sb.append(CR);
            sb.append("Method method = "+interfaces[0].getName()+".class.getMethod(\""+m.getName()+"\",new Class[]{"+m.getParameters()[0].getType().getName()+".class});");
            sb.append(CR);
            sb.append(" result =  (String)");
            sb.append("h.invoke(this,method,new Object [] {"+m.getParameters()[0].getName()+"});");
            sb.append(CR);
            sb.append("}catch(Throwable e){");
            sb.append(CR);
            sb.append("e.printStackTrace();");
            sb.append(CR);
            sb.append("}");
            sb.append("return result;");
            sb.append(CR);
        }

        sb.append("}");
        sb.append("}");
        return sb.toString();
    }
}
