package com.recklessMo.registry.config.zookeeper;

import com.google.common.base.Preconditions;
import com.recklessMo.registry.config.RegistryType;
import com.recklessMo.registry.config.constant.Constants;
import com.recklessMo.registry.config.model.ClientNode;
import com.recklessMo.registry.config.model.Node;
import com.recklessMo.registry.config.model.ServerNode;
import org.apache.curator.framework.CuratorFramework;

public class ZkDataManagerBuilder {

    private CuratorFramework client = null;
    private RegistryType type = null;
    private Node self = null;
    private String connectionString = null;
    private int connectionTimeoutMs = Constants.SESSION_CONNECT_DEFAULT_MS;
    private int sessionTimeoutMs = Constants.SESSION_TIMEOUT_DEFAULT_MS;

    private ZkDataManagerBuilder(){

    }

    public static ZkDataManagerBuilder builder() {
        return new ZkDataManagerBuilder();
    }

    public AbstractZkDataManager build(){
        if (type == RegistryType.clientNode) {
            Preconditions.checkState(self instanceof ClientNode, "self必须为ClientNode类型！");
            return new ClientZkDataManager(client, self, connectionString, connectionTimeoutMs, sessionTimeoutMs);
        } else {
            Preconditions.checkState(self instanceof ServerNode, "self必须为ServerNode类型！");
            return new ServerZkDataManager(client, self, connectionString, connectionTimeoutMs, sessionTimeoutMs);
        }
    }


    public ZkDataManagerBuilder client(CuratorFramework client) {
        this.client = client;
        return this;
    }

    public ZkDataManagerBuilder type(RegistryType type) {
        this.type = type;
        return this;
    }

    public ZkDataManagerBuilder node(Node self) {
        Preconditions.checkNotNull(self, "节点信息不能为空！");
        Preconditions.checkNotNull(self.getRegisterType(), "节点类型不能为null!");
        this.self = self;
        return this;
    }

    public ZkDataManagerBuilder connectionString(String connectionString) {
        this.connectionString = connectionString;
        return this;
    }

    public ZkDataManagerBuilder connectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
        return this;
    }

    public ZkDataManagerBuilder sessionTimeoutMs(int sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
        return this;
    }

}
