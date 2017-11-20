package com.recklessMo.rpc.model;

import java.util.List;

/**
 * Created by hpf on 11/17/17.
 */
public class RequestWrapper {

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
    private List<Class<?>> paramTypes;
    /**
     * 参数值
     */
    private List<Object> parameters;

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

    public List<Class<?>> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(List<Class<?>> paramTypes) {
        this.paramTypes = paramTypes;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }
}
