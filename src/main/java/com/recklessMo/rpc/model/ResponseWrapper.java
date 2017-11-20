package com.recklessMo.rpc.model;

/**
 * Created by hpf on 11/17/17.
 */
public class ResponseWrapper {

    /**
     * reqeustId
     */
    private String requestId;
    /**
     * 状态
     */
    private int status;
    /**
     * 如果成功,对应结果,否则对应exception或者errorMsg
     */
    private Object result;


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
