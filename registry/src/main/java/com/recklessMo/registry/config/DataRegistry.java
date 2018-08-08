package com.recklessMo.registry.config;

public interface DataRegistry<T> {

    /**
     * 注册server或者client
     */
    void register(T t, String path) throws Exception;

    /**
     * 取消注册server或者client
     */
    void unRegister(String path) throws Exception;

}
