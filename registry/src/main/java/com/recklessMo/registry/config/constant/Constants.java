package com.recklessMo.registry.config.constant;

public class Constants {

    /**
     * nettyRpc在zk上的前缀
     */
    private static final String PREFIX = "/nettyRpc";
    /**
     * 路径分隔符
     */
    private static final String SEP = "/";


    /**
     * 客户端注册的目录
     * TODO 临时顺序节点？
     */
    public static final String CLIENT = PREFIX + SEP + "client";
    /**
     * 服务端注册的目录
     * TODO 临时顺序节点？
     */
    public static final String SERVER = PREFIX + SEP + "server";



    public static final int SESSION_TIMEOUT_DEFAULT_MS = 30000;
    public static final int SESSION_CONNECT_DEFAULT_MSG = 10000;
}


