package com.recklessMo.registry.config.zookeeper;

import com.recklessMo.registry.config.constant.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * 控制与zk的一系列交互
 *
 * 与zk的交互逻辑主要是包括下面几个部分：
 * 初始化init
 * 注册（dataRegistry）
 * 监听（dataDiscovery）
 * 恢复（dataRecovery）
 *
 *
 * abstract抽象出几个公用的部分，包括初始化client，注册，监听，恢复机制等。
 *
 *
 */
public abstract class AbstractZkDataManager{

    private static final Logger logger = LoggerFactory.getLogger(AbstractZkDataManager.class);

    protected CuratorFramework client = null;
    private ZkRecovery zkRecovery = null;

    private String connectionString = null;
    private int connectionTimeoutMs = 0;
    private int sessionTimeoutMs = 0;

    public AbstractZkDataManager(String connectionString) throws Exception{
        this(connectionString, Constants.SESSION_CONNECT_DEFAULT_MSG, Constants.SESSION_TIMEOUT_DEFAULT_MS);
    }

    public AbstractZkDataManager(String connectionString, int connectionTimeoutMs, int sessionTimeoutMs) throws Exception{
        this.connectionString = connectionString;
        this.connectionTimeoutMs = connectionTimeoutMs;
        this.sessionTimeoutMs = sessionTimeoutMs;
        init();
    }

    @PostConstruct
    public void init() throws Exception{
        //初始化client
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(10000, 3);
        client = CuratorFrameworkFactory.builder()
                .connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .build();
        client.start();
        //初始化恢复机制
        zkRecovery = new ZkRecovery();
        //初始化连接监听
        client.getConnectionStateListenable().addListener(new ZkSessionStateListener(zkRecovery));
        initInner();
        logger.info("ZkDataManager init success");
    }

    abstract void initInner() throws Exception;




}


