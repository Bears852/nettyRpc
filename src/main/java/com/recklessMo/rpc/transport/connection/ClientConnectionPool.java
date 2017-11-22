package com.recklessMo.rpc.transport.connection;

import com.recklessMo.rpc.config.ServerListConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPool;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO 并发控制分析
 *
 * Created by hpf on 11/20/17.
 */
public class ClientConnectionPool implements ChannelPool {

    /**
     * 服务的连接配置, 一般每个服务都会配置多个实例, 每个实例都有一个地址
     */
    private ServerListConfig serverListConfig;
    /**
     * 服务对应的连接池列表, key为address, value为channel pool
     */
    private ChannelPoolMap poolMap;

    /**
     * for rr
     * 定义轮询策略,比如round-robin, 可以抽象出一个chooser
     */
    private AtomicInteger counter = new AtomicInteger(0);


    /**
     * 构造函数
     *
     * @param serverListConfig 服务配置列表, 暂时只支持服务地址配置!
     */
    public ClientConnectionPool(ServerListConfig serverListConfig) {
        this.serverListConfig = serverListConfig;
        init();
    }

    /**
     * 初始化连接池
     */
    public void init() {
        final Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new LoggingHandler());
        //所有的公用一个eventloopgroup, 对于客户端来说应该问题不大!
        poolMap = new AbstractChannelPoolMap<InetSocketAddress, FixedChannelPool>() {
            @Override
            protected FixedChannelPool newPool(InetSocketAddress key) {
                return new FixedChannelPool(bootstrap.remoteAddress(key), new FixedChannelPoolHandler(), 2);
            }
        };
        //预先建立好链接
        serverListConfig.getAddressList().stream().forEach(address -> {
            poolMap.get(address);
        });
    }


    /**
     * @return
     */
    private FixedChannelPool choose() {
        counter.incrementAndGet();
        AbstractChannelPoolMap temp = ((AbstractChannelPoolMap) poolMap);
        int size = temp.size();
        int index = counter.get() % size;
        Iterator<Map.Entry<InetSocketAddress, FixedChannelPool>> it = temp.iterator();
        int i = 0;
        while (it.hasNext()) {
            i++;
            if (i == index) {
                return it.next().getValue();
            }
        }
        return null;
    }


    public Future<Channel> acquire() {
        return choose().acquire();
    }

    public Future<Channel> acquire(Promise<Channel> promise) {
        return choose().acquire(promise);
    }

    public Future<Void> release(Channel channel) {
        return choose().release(channel);
    }

    public Future<Void> release(Channel channel, Promise<Void> promise) {
        return choose().release(channel, promise);
    }

    @SuppressWarnings("unchecked")
    public void close() {
        Iterator<Map.Entry<InetSocketAddress, FixedChannelPool>> it = ((AbstractChannelPoolMap) poolMap).iterator();
        while (it.hasNext()) {
            it.next().getValue().close();
        }
    }
}
