package com.recklessMo.registry.config.provider;

import com.recklessMo.registry.config.ServerListConfig;

/**
 * Created by hpf on 11/22/17.
 */
public class FixedServerListConfigProvider implements IServerListConfigProvider {

    @Override
    public ServerListConfig getServerListConfig() {
        ServerListConfig serverListConfig = new ServerListConfig();
        serverListConfig.addServer("127.0.0.1", 18888);
        return serverListConfig;
    }
}
