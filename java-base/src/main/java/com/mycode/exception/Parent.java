package com.mycode.exception;

/**
 * 蛮小江
 * 2018/8/15 11:22
 */
public class Parent {
    public static void main(String[] args) {
        String a = "";
        Parent person =new Son();
        try {
            person.sayHello("");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
    protected  String sayHello(String message) throws  Throwable{
        if(null == message || message.trim().length()==0){
            throw new Throwable("parent 参数为空");
        }
             return  null;
    }
}
class Son extends Parent{
    @Override
    protected String sayHello(String message) throws Exception {
           if(null == message || message.trim().length()==0){
               try {
                   super.sayHello(message);
               }catch (Throwable throwable){
                   System.out.println(throwable.getMessage()+",cause="+throwable.getCause());
                   throw new Exception("参数为空");
               }
           }
        return "";
    }
}
