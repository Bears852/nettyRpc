package com.recklessMo.rpc.bootstrap.client;

import com.recklessMo.rpc.model.RequestWrapper;
import com.recklessMo.rpc.transport.connection.ClientConnectionPool;
import com.recklessMo.rpc.util.UUIDUtils;
import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by hpf on 11/20/17.
 */
public class ClientInvocationHandler implements InvocationHandler {

    /**
     * 同步调用还是异步调用呢?
     */
    private boolean sync;
    /**
     * 连接池
     */
    private ClientConnectionPool connectionPool;

    public ClientInvocationHandler() {

    }

    public ClientInvocationHandler(ClientConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
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



        return null;
    }
}
