package com.recklessMo.common;

import java.util.Date;

/**
 * 代表一个节点
 */
public class Node {

    /**
     * 节点的ip
     */
    private String ip;
    /**
     * 节点注册的时间
     */
    private Date registerTime;
    /**
     * 节点的类型，客户端节点还是服务端节点
     */
    private Enum registerType;

    public Enum getRegisterType() {
        return registerType;
    }

    public void setRegisterType(Enum registerType) {
        this.registerType = registerType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }
}
