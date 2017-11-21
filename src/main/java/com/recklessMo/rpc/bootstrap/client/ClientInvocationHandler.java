package com.recklessMo.rpc.bootstrap.client;

import com.recklessMo.rpc.model.RequestWrapper;
import com.recklessMo.rpc.model.ResponseWrapper;
import com.recklessMo.rpc.transport.connection.ClientConnectionPool;
import com.recklessMo.rpc.util.UUIDUtils;
import io.netty.channel.Channel;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by hpf on 11/20/17.
 */
public class ClientInvocationHandler implements InvocationHandler {


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

    public ClientInvocationHandler(EventExecutor eventExecutor, ClientConnectionPool connectionPool, boolean sync) {
        this.eventExecutor = eventExecutor;
        this.connectionPool = connectionPool;
        this.sync = sync;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RequestWrapper requestWrapper = new RequestWrapper();
        requestWrapper.setClassName(proxy.getClass().getName());
        requestWrapper.setMethodName(method.getName());
//        requestWrapper.setParameters(args);
//        requestWrapper.setParamTypes();
        requestWrapper.setRequestId(UUIDUtils.getRandomId());

        Future<Channel> channelFuture = connectionPool.acquire().sync();
        if(channelFuture.isSuccess()){
            channelFuture.getNow().writeAndFlush(requestWrapper);
        }

        //如果是同步模式,return结果
        if(sync){

            return null;
        }else {
            //如果是异步模式, return一个promise,可以给promise添加相应的listener,来实现异步操作
            Promise<ResponseWrapper> promise = new DefaultPromise<>(eventExecutor);

            return null;
        }
    }
}
