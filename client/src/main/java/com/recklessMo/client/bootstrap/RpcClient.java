package com.recklessMo.client.bootstrap;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.recklessMo.common.model.ResponseWrapper;
import com.recklessMo.registry.config.provider.FixedRegistryProvider;
import com.recklessMo.registry.config.provider.IRegistryProvider;
import com.recklessMo.transport.netty.ClientConnectionPool;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private static Map<String, Promise<ResponseWrapper>> requestWrapperMap;

    static {
        requestWrapperMap = CacheBuilder.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(15000, TimeUnit.MILLISECONDS)
                .removalListener(new FutureRemoveListener())
                .build()
                .asMap();
    }

    public static Map<String, Promise<ResponseWrapper>> getRequestWrapperMap() {
        return requestWrapperMap;
    }

    private static class FutureRemoveListener implements RemovalListener<String, Promise<ResponseWrapper>> {

        @Override
        public void onRemoval(RemovalNotification<String, Promise<ResponseWrapper>> removalNotification) {

        }
    }




        /**
     * 创建出clientConnectionPool
     *
     * @return
     */
    public static ClientConnectionPool createConnectionPool() {
        //新建server list config;
        IRegistryProvider provider = new FixedRegistryProvider();
        return new ClientConnectionPool(provider.getServerList());
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


    }

}
