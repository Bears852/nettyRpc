package com.recklessMo.registry.config.zookeeper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.google.common.base.Preconditions;
import com.recklessMo.registry.config.DataRegistry;
import com.recklessMo.registry.config.RegistryType;
import com.recklessMo.registry.config.model.Node;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 用于对数据进行注册，客户端和服务端都需要使用
 *
 * @param <T>
 */
public class ZkDataRegistry<T extends Node> implements DataRegistry<T> {

    private static final Logger logger = LoggerFactory.getLogger(ZkDataRegistry.class);

    private CuratorFramework client;
    private String basePath;

    public ZkDataRegistry(CuratorFramework client, String basePath) {
        this.client = Preconditions.checkNotNull(client, "client 不能为null");
        this.basePath = Preconditions.checkNotNull(basePath, "basePath 不能为null");
        logger.info("init zkDataRegistry success");
    }

    @Override
    public void registerData(T t) throws Exception {
        //对其进行序列化
        SerializeConfig config = new SerializeConfig();
        config.configEnumAsJavaBean(RegistryType.class);
        byte[] data = JSON.toJSONString(t, config).getBytes();
        try {
            String path = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(basePath, data);
            t.setPath(path);
        } catch (Exception e) {
            logger.error("register data failed", e);
            throw e;
        }
    }

    @Override
    public void unRegisterData(String path) throws Exception {
        try {
            if(path != null) {
                client.delete().guaranteed().forPath(path);
            }
        }catch(Exception e){
            logger.error("unRegister data failed", e);
            throw e;
        }
    }

}
