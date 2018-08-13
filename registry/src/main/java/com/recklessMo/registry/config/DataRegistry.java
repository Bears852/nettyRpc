package com.recklessMo.registry.config;

public interface DataRegistry<T> {

    /**
     * 注册server或者client
     */
    void registerData(T t) throws Exception;

    /**
     * 取消注册server或者client
     */
    void unRegisterData(String path) throws Exception;

}
