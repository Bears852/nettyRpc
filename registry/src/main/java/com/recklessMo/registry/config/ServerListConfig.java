package com.recklessMo.registry.config;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hpf on 11/20/17.
 */
public class ServerListConfig {

    /**
     * 服务器地址列表
     */
    private List<InetSocketAddress> addressList;

    public ServerListConfig() {
        addressList = new LinkedList<InetSocketAddress>();
    }

    public synchronized void addServer(String server, int port) {
        InetSocketAddress socketAddress = new InetSocketAddress(server, port);
        addServer(socketAddress);
    }

    public synchronized void addServer(InetSocketAddress socketAddress) {
        addressList.add(socketAddress);
    }

    public List<InetSocketAddress> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<InetSocketAddress> addressList) {
        this.addressList = addressList;
    }
}
