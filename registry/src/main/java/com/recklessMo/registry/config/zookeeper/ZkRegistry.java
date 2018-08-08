package com.recklessMo.registry.config.zookeeper;

import com.google.common.base.Preconditions;
import com.recklessMo.registry.config.DataRegistry;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.io.Serializable;


/**
 *
 * 用于对数据进行注册，客户端和服务端都需要使用
 *
 * @param <T>
 */
public class ZkRegistry<T extends Serializable> implements DataRegistry<T> {

    private CuratorFramework client = null;

    public ZkRegistry(){

    }

    public ZkRegistry(CuratorFramework client){
        Preconditions.checkNotNull(client);
        this.client = client;
    }

    @Override
    public void register(T t, String path) throws Exception {
        //t实现了serializable接口，所以对其进行序列化
        byte[] data = new byte[100];
        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, data);
    }

    @Override
    public void unRegister(String path) throws Exception {
        client.delete().guaranteed().forPath(path);
    }

}
