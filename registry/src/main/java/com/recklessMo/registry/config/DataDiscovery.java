package com.recklessMo.registry.config;


import java.util.List;

/**
 *
 * 数据发现，自动发现某个path下面所有的node
 * 用于client发现server列表
 *
 * @param <T>
 */
public interface DataDiscovery<T> {

    /**
     * 获取server列表
     * @return
     */
    List<T> getDataList();

}
