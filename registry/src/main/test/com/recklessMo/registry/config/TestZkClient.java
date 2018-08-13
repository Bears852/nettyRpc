package com.recklessMo.registry.config;

import com.alibaba.fastjson.JSON;
import com.recklessMo.registry.config.constant.Constants;
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

public class TestZkClient {

    private static ClientZkDataManager clientZkDataManager = null;
    private static String connectionString = "127.0.0.1:2181";


    @BeforeClass
    public static void init() {
        //代表client节点的信息
        ClientNode clientNode = new ClientNode();
        clientNode.setIp("192.168.1.1");
        clientNode.setRegisterTime(new Date());
        clientNode.setRegisterType(RegistryType.clientNode);
        clientZkDataManager = (ClientZkDataManager) ZkDataManagerBuilder.builder().client(null).connectionString(connectionString).type(RegistryType.clientNode).node(clientNode).build();

    }


    @Test
    public void testRecovery() throws Exception {
        int cnt = 0;
        while (cnt++ < 120) {
            List<ServerNode> serverNodeList = clientZkDataManager.getDataList();
            if(serverNodeList.size() > 0) {
                for (int i = 0; i < serverNodeList.size(); i++) {
                    System.out.println(">>>>>>>>>>>server " + i + ": " + JSON.toJSONString(serverNodeList.get(i)));
                }
            }else{
                System.out.println(">>>>>>>>>>>>>no active server");
            }
            Thread.sleep(1000);
        }
    }


    @AfterClass
    public static void close() {
        clientZkDataManager.close();
    }


}
