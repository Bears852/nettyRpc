package com.recklessMo.client.bootstrap;

import com.recklessMo.common.model.RequestWrapper;
import com.recklessMo.common.model.ResponseWrapper;
import com.recklessMo.common.util.UUIDUtils;
import com.recklessMo.transport.netty.ClientConnectionPool;
import io.netty.channel.Channel;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Created by hpf on 11/20/17.
 */
public class ClientInvocationHandler implements InvocationHandler {

    /**
     * 调用服务的名称
     */
    private String name;
    /**
     * 用于执行异步调用回调.
     */
    private EventExecutor eventExecutor;
    /**
     * 同步调用还是异步调用呢?
     */
    private boolean sync;
    /**
     * 连接池
     */
    private ClientConnectionPool connectionPool;

    public ClientInvocationHandler(String name, EventExecutor eventExecutor, ClientConnectionPool connectionPool, boolean sync) {
        this.eventExecutor = eventExecutor;
        this.connectionPool = connectionPool;
        this.sync = sync;
        this.name = name;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args){
        Future<Channel> channelFuture = null;
        try {
            RequestWrapper requestWrapper = new RequestWrapper();
            requestWrapper.setServiceName(name);
            requestWrapper.setMethodName(method.getName());
            requestWrapper.setParameters(args);
            requestWrapper.setParamTypes(method.getParameterTypes());
            requestWrapper.setRequestId(UUIDUtils.getRandomId());
            //获取一个可用的channel
            channelFuture = connectionPool.acquire().sync();
            if (!channelFuture.isSuccess()) {
                throw new Exception("获取链接失败!");
            }
            Promise<ResponseWrapper> promise = eventExecutor.newPromise();
            requestWrapper.setPromise(promise);
            RpcClient.getRequestWrapperMap().put(requestWrapper.getRequestId(), promise);
            //注意channel.write和context.write的区别
            channelFuture.getNow().writeAndFlush(requestWrapper);
            //如果是同步模式,return结果
            return promise.get(5000L, TimeUnit.MILLISECONDS).getResult();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (channelFuture != null && channelFuture.isSuccess()) {
                connectionPool.release(channelFuture.getNow());
            }
        }
        return null;
    }
}
