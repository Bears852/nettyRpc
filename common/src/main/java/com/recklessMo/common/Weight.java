package com.recklessMo.common;

/**
 *
 * server需要根据weight值进行负载均衡设置
 *
 * 获取weight值
 *
 */
public interface Weight {

    int DEFAULT_WEIGHT = 10;

    int getWeight();
}
