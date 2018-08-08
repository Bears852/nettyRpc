package com.recklessMo.registry.config.provider;

import com.recklessMo.registry.config.ServerListConfig;

/**
 *
 * 类似于spi接口，
 *
 * Created by hpf on 11/20/17.
 */
public interface IRegistryProvider<T> {

    /**
     * 获取服务器列表
     * @return
     */
    ServerListConfig getServerList();

    /**
     * 注册
     * @param t
     */
    void registerData(T t);
}
