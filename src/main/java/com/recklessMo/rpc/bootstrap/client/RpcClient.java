package com.recklessMo.rpc.bootstrap.client;

import com.recklessMo.rpc.bootstrap.protocol.IRobotProtocol;
import com.recklessMo.rpc.config.provider.FixedServerListConfigProvider;
import com.recklessMo.rpc.config.provider.IServerListConfigProvider;
import com.recklessMo.rpc.transport.connection.ClientConnectionPool;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.EventExecutor;

import java.lang.reflect.Proxy;

/**
 * TODO 后续接入spring
 * <p>
 * 核心动态代理, 采用jdk的动态代理
 * 代理接口使其去访问server端
 * <p>
 * Created by hpf on 11/17/17.
 */
public class RpcClient {


    /**
     * 创建出clientConnectionPool
     *
     * @return
     */
    public static ClientConnectionPool createConnectionPool() {
        //新建server list config;
        IServerListConfigProvider provider = new FixedServerListConfigProvider();
        return new ClientConnectionPool(provider.getServerListConfig());
    }

    /**
     * 创建eventExecutor用于执行回调
     *
     * @return
     */
    public static EventExecutor createEventExecutor() {
        return new DefaultEventExecutor();
    }

    /**
     * 获取服务
     *
     * @param sync
     * @param <T>
     * @return
     */
    public static <T> T createService(String serviceName, Class<T> interfaceClass, ClientConnectionPool pool, EventExecutor eventExecutor, boolean sync) {
        return (T) Proxy.newProxyInstance(RpcClient.class.getClassLoader(),
                new Class<?>[]{interfaceClass}, new ClientInvocationHandler(serviceName, eventExecutor, pool, sync));
    }


    public static void main(String[] args) {
        //创建客户端连接池
        ClientConnectionPool clientConnectionPool = createConnectionPool();
        //创建回调执行器
        EventExecutor eventExecutor = createEventExecutor();
        //创建同步客户端
        IRobotProtocol robotProtocol = RpcClient.createService("RobotService", IRobotProtocol.class, clientConnectionPool, eventExecutor, true);
        robotProtocol.sendMsg("hello world!");


        //TODO 创建异步客户端,添加回调!


    }

}
