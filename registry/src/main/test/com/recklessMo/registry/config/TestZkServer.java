package com.recklessMo.registry.config;

import com.alibaba.fastjson.JSON;
import com.recklessMo.registry.config.model.ClientNode;
import com.recklessMo.registry.config.model.ServerNode;
import com.recklessMo.registry.config.zookeeper.ClientZkDataManager;
import com.recklessMo.registry.config.zookeeper.ServerZkDataManager;
import com.recklessMo.registry.config.zookeeper.ZkDataManagerBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class TestZkServer {

    private static ServerZkDataManager serverZkDataManager = null;
    private static String connectionString = "127.0.0.1:2181";


    @BeforeClass
    public static void init() {
        //代表server节点的信息
        ServerNode serverNode = new ServerNode();
        serverNode.setIp("10.10.10.10");
        serverNode.setPort(2222);
        serverNode.setServiceName("cache");
        serverNode.setWeight(10);
        serverNode.setRegisterType(RegistryType.serverNode);
        serverZkDataManager = (ServerZkDataManager) ZkDataManagerBuilder.builder().connectionString(connectionString).type(RegistryType.serverNode).node(serverNode).build();

    }


    @Test
    public void testRecovery() throws Exception {
        int cnt = 0;
        while (cnt++ < 50) {
            Thread.sleep(1000);
        }
    }


    @AfterClass
    public static void close() {
        serverZkDataManager.close();
    }


}
