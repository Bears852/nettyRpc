package com.recklessMo.registry.config.zookeeper;

import com.recklessMo.registry.config.model.ServerNode;

public class ClientZkDataManager extends AbstractZkDataManager {

    private ZkDiscovery<ServerNode> zkDiscovery = null;

    public ClientZkDataManager(String connectionString) throws Exception{
        super(connectionString);
    }

    public ClientZkDataManager(String connectionString, int connectionTimeoutMs, int sessionTimeoutMs) throws Exception{
        super(connectionString, connectionTimeoutMs, sessionTimeoutMs);
    }

    @Override
    void initInner() throws Exception{
        zkDiscovery = new ZkDiscovery<ServerNode>(client, ServerNode.class);
    }



}
