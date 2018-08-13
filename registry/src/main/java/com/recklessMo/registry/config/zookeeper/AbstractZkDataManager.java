package com.recklessMo.registry.config.zookeeper;

import com.google.common.base.Preconditions;
import com.recklessMo.common.Closeable;
import com.recklessMo.registry.config.RegistryType;
import com.recklessMo.registry.config.constant.Constants;
import com.recklessMo.registry.config.model.Node;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 控制与zk的一系列交互
 * <p>
 * 与zk的交互逻辑主要是包括下面几个部分：
 * 初始化init
 * 注册（dataRegistry）
 * 监听（dataDiscovery）
 * 恢复（dataRecovery）（恢复主要就是注册）
 * <p>
 * <p>
 * abstract抽象出几个公用的部分，包括初始化client，注册，监听，恢复机制等。
 */
public abstract class AbstractZkDataManager implements Closeable{

    private static final Logger logger = LoggerFactory.getLogger(AbstractZkDataManager.class);

    /**
     * 操控zk的curator客户端
     */
    protected CuratorFramework client;
    /**
     * 封装了注册方法，用于将节点注册到指定的路径上
     */
    private ZkDataRegistry<Node> zkDataRegistry;
    /**
     * 代表当前执行的节点
     */
    private Node self;
    /**
     * zk连接字符串
     */
    private String connectionString;
    /**
     * 连接超时
     */
    private int connectionTimeoutMs;
    /**
     * session超时
     */
    private int sessionTimeoutMs;

    public AbstractZkDataManager(CuratorFramework client, Node self, String connectionString, int connectionTimeoutMs, int sessionTimeoutMs) {
        this.connectionString = Preconditions.checkNotNull(connectionString, "zk连接地址不能为空");
        Preconditions.checkState(connectionTimeoutMs > 0, "连接超时时间必须大于0");
        this.connectionTimeoutMs = connectionTimeoutMs;
        Preconditions.checkState(sessionTimeoutMs > 0, "session超时时间必须大于0");
        this.sessionTimeoutMs = sessionTimeoutMs;
        this.self = Preconditions.checkNotNull(self, "注册信息不能为空");
        //初始化client
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(10000, 3);
        if (client == null) {
            client = CuratorFrameworkFactory.builder()
                    .connectString(connectionString)
                    .retryPolicy(retryPolicy)
                    .connectionTimeoutMs(connectionTimeoutMs)
                    .sessionTimeoutMs(sessionTimeoutMs)
                    .build();
            client.start();
        }
        this.client = client;
        this.zkDataRegistry = new ZkDataRegistry<>(this.client,
                self.getRegisterType().equals(RegistryType.clientNode) ? Constants.CLIENT : Constants.SERVER);
        this.registerData();
        //初始化恢复机制,连接监听
        client.getConnectionStateListenable().addListener((c, state) -> {
            if (state == ConnectionState.CONNECTED || state == ConnectionState.RECONNECTED) {
                this.registerData();
            }
        });
        logger.info("init zkDataManager success");
    }


    public void registerData() {
        try {
            logger.info("register instance due to connection !");
            zkDataRegistry.registerData(self);
            logger.info("register instance success after connection !");
        }catch(Exception e){
            logger.error("register instance failed after connection !", e);
        }
    }

    public void unRegisterData() throws Exception {
        zkDataRegistry.unRegisterData(self.getPath());
    }

    @Override
    public void close() {
        try {
            this.unRegisterData();
            logger.info("exit unregister data");
            this.client.close();
            logger.info("exit client");
        }catch (Exception e){
            logger.error("close error occur", e);
        }
    }

    public CuratorFramework getClient() {
        return client;
    }

    public void setClient(CuratorFramework client) {
        this.client = client;
    }

    public Node getSelf() {
        return self;
    }

    public void setSelf(Node self) {
        this.self = self;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public int getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public void setSessionTimeoutMs(int sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }
}


