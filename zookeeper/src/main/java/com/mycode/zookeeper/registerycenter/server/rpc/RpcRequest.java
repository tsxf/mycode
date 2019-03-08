package com.mycode.zookeeper.registerycenter.server.rpc;

import java.io.Serializable;

/**
 * 蛮小江
 * 2018/6/19 11:30
 */
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = -4143712391336250108L;
    private String className;
    private  String methodName;
    private Object [] paramters;
    private String versioni;

    public String getVersioni() {
        return versioni;
    }

    public void setVersioni(String versioni) {
        this.versioni = versioni;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParamters() {
        return paramters;
    }

    public void setParamters(Object[] paramters) {
        this.paramters = paramters;
    }
}
