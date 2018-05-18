package com.mycode.reflect.proxy.custom;

import java.io.*;
import java.net.URLClassLoader;

/**
 * @author Soul
 * @version V1.0
 * @Project: awesome
 * @Description: TODO
 * @Date 2018/5/8 14:43
 * @Modify Who          When                        What
 * --------   -----------  ---------------------------------------------------
 */
public class MyClassLoader extends ClassLoader {
    private File classPathFile;

    //拿到class文件存放的路径目录文件对象
    public MyClassLoader() {
        String classPath = MyClassLoader.class.getResource("").getPath();
        classPathFile = new File(classPath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //包的全路径
        String className = MyClassLoader.class.getPackage().getName() +"."+ name;
        //判断文件对象是否存在
        if (classPathFile != null) {
            //.class文件对象，从IO流中获取到byte[]数组
            String aa  =  name.replaceAll("\\.", "/").concat(".class");
            File file = new File(classPathFile, name.replaceAll("\\.", "/").concat(".class"));
            FileInputStream ins = null;
            ByteArrayOutputStream bos = null;
            try {
                ins = new FileInputStream(file);
                bos = new ByteArrayOutputStream();
                int length = 0;
                byte[] buffer = new byte[1024];
                while ((length = ins.read(buffer)) != -1) {
                    bos.write(buffer, 0, length);
                }
                return defineClass(className, bos.toByteArray(), 0, bos.size());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return null;

    }
}
