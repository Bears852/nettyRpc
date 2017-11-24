package com.recklessMo.rpc.bootstrap.client;

import com.recklessMo.rpc.bootstrap.protocol.IRobotProtocol;
import com.recklessMo.rpc.config.provider.FixedServerListConfigProvider;
import com.recklessMo.rpc.config.provider.IServerListConfigProvider;
import com.recklessMo.rpc.model.RequestWrapper;
import com.recklessMo.rpc.transport.connection.ClientConnectionPool;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.EventExecutor;

import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

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
     * 请求map
     */
    private static ConcurrentHashMap<String, RequestWrapper> requestWrapperMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, RequestWrapper> getRequestWrapperMap(){
        return requestWrapperMap;
    }



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
        //创建异步调用回调执行器
        EventExecutor eventExecutor = createEventExecutor();
        //创建同步客户端
        IRobotProtocol robotProtocol = RpcClient.createService("RobotService", IRobotProtocol.class, clientConnectionPool, eventExecutor, true);
        for(int i = 0; i < 10; i++) {
            String msg = robotProtocol.sendMsg("hello world!");
            System.out.println("get response: " + msg);
        }


        //TODO 创建异步客户端,添加回调!

    }

}
