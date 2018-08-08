package com.recklessMo.registry.config.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于进行zk的session链接管理
 */
public class ZkSessionStateListener implements ConnectionStateListener{

    private ZkRecovery zkRecovery;

    public ZkSessionStateListener(ZkRecovery zkRecovery){
        this.zkRecovery = zkRecovery;
    }

    private static final Logger logger = LoggerFactory.getLogger(ZkSessionStateListener.class);

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        logger.info("zk connection state changed {}", connectionState.name());
        if(connectionState == ConnectionState.LOST){
            //连接丢失的话，就等待重连，等待重连之后进行recovery，主要是重新注册节点。
            while(true) {
                try {
                    if (curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut()) {
                        logger.info("zk reconnect success");
                        zkRecovery.init();
                        break;
                    }
                } catch (Exception e) {
                    logger.error("wait for reconnect error", e);
                }
            }
        }
    }
}
