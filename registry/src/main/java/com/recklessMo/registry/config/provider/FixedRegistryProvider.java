package com.recklessMo.registry.config.provider;

import com.recklessMo.registry.config.ServerListConfig;

/**
 * Created by hpf on 11/22/17.
 */
public class FixedRegistryProvider implements IRegistryProvider {

    @Override
    public ServerListConfig getServerList() {
        ServerListConfig serverListConfig = new ServerListConfig();
        serverListConfig.addServer("127.0.0.1", 18888);
        return serverListConfig;
    }
}
