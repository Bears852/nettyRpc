package com.recklessMo.rpc.bootstrap.client;

import com.recklessMo.rpc.bootstrap.protocol.IRobotProtocol;
import com.recklessMo.rpc.transport.connection.ClientConnectionPool;
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
        return null;
    }

    /**
     * 创建eventExecutor用于执行回调
     *
     * @return
     */
    public static EventExecutor createEventExecutor() {
        return null;
    }

    /**
     * 获取服务
     *
     * @param sync
     * @param <T>
     * @return
     */
    public static <T> T createService(Class<T> interfaceClass, ClientConnectionPool pool, EventExecutor eventExecutor, boolean sync) {
        return (T) Proxy.newProxyInstance(RpcClient.class.getClassLoader(),
                new Class<?>[]{interfaceClass}, new ClientInvocationHandler(eventExecutor, pool, sync));
    }


    public static void main(String[] args) {
        IRobotProtocol robotProtocol = RpcClient.createService(IRobotProtocol.class, createConnectionPool(), createEventExecutor(), true);
        robotProtocol.sendMsg("hello world!");
    }

}
