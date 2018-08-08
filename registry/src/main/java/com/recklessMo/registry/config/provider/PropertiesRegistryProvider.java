package com.recklessMo.registry.config.provider;

import com.recklessMo.registry.config.ServerListConfig;

/**
 * Created by hpf on 11/20/17.
 */
public class PropertiesRegistryProvider implements IRegistryProvider {

    private String propertiesFileName = "";

    /**
     * 从配置文件中获取服务器的地址
     * @return
     */
    public ServerListConfig getServerList() {
        return null;
    }

}
