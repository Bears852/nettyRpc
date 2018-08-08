package com.recklessMo.registry.config;

public interface DataRecovery {

    /**
     * 初次链接
     */
    void init();

    /**
     * 进行恢复
     */
    void recover();


}
