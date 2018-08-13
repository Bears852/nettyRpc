package com.recklessMo.registry.config.zookeeper;

import com.recklessMo.registry.config.model.Node;
import org.apache.curator.framework.CuratorFramework;

public class ServerZkDataManager extends AbstractZkDataManager {


    public ServerZkDataManager(CuratorFramework client, Node self, String connectionString, int connectionTimeoutMs, int sessionTimeoutMs){
        super(client, self, connectionString, connectionTimeoutMs, sessionTimeoutMs);
    }

}
