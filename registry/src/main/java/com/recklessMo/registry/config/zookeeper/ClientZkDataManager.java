package com.recklessMo.registry.config.zookeeper;

import com.recklessMo.registry.config.DataDiscovery;
import com.recklessMo.registry.config.constant.Constants;
import com.recklessMo.registry.config.model.Node;
import com.recklessMo.registry.config.model.ServerNode;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;

public class ClientZkDataManager extends AbstractZkDataManager implements DataDiscovery<ServerNode> {

    private ZkDataDiscovery<ServerNode> zkDataDiscovery;

    public ClientZkDataManager(CuratorFramework client, Node self, String connectionString, int connectionTimeoutMs, int sessionTimeoutMs){
        super(client, self, connectionString, connectionTimeoutMs, sessionTimeoutMs);
        zkDataDiscovery = new ZkDataDiscovery<>(this.client, Constants.WATCH_SERVER, ServerNode.class);
    }


    @Override
    public List<ServerNode> getDataList() {
        return zkDataDiscovery.getDataList();
    }

    @Override
    public void close(){
        super.close();
        zkDataDiscovery.close();
    }

}
