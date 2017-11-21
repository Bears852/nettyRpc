package com.recklessMo.rpc.model;

import io.netty.util.concurrent.Promise;

import java.io.Serializable;

/**
 *
 * 用于封装RPC客户端的请求
 *
 * Created by hpf on 11/17/17.
 */
public class RequestWrapper implements Serializable{

    /**
     * request的ID, 具体算法待定
     */
    private String requestId;
    /**
     * 调用类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数类型
     */
    private Class<?>[] paramTypes;
    /**
     * 参数值
     */
    private Object[] parameters;
    /**
     * 用于获取回复的promise
     */
    private Promise<ResponseWrapper> promise;



    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Promise<ResponseWrapper> getPromise() {
        return promise;
    }

    public void setPromise(Promise<ResponseWrapper> promise) {
        this.promise = promise;
    }
}
