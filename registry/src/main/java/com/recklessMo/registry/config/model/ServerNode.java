package com.recklessMo.registry.config.model;

import com.recklessMo.common.Node;
import com.recklessMo.common.Weight;

/**
 * 服务端在zookeeper上注册的对象
 */
public class ServerNode extends Node implements Weight {

    /**
     * 是否可用
     */
    private boolean isOnline;
    /**
     * 权重。可以基于权重进行负载均衡
     */
    private int weight;
    /**
     * 服务名字
     */
    private String serviceName;
    /**
     * 端口所在的端口
     */
    private int port;

    @Override
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
