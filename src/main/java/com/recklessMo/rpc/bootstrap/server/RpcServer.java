package com.recklessMo.rpc.bootstrap.server;

import com.recklessMo.rpc.serializer.java.RequestWrapperCodec;
import com.recklessMo.rpc.serializer.java.ResponseWrapperCodec;
import com.recklessMo.rpc.transport.server.AbstractRpcService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * netty server, 启动后注册server
 * <p>
 * Created by hpf on 11/17/17.
 */
public class RpcServer {


    private static Map<String, Object> serverMap = new HashMap<>();

    static {
        findService();
    }

    public static Map<String, Object> getServerMap() {
        return serverMap;
    }

    /**
     * 发现服务, 查找所有扩展了abstractService的类, 这些类保存下来作为service
     */
    private static void findService() {
        try {
            Reflections reflections = new Reflections("com.recklessMo");
            Set<Class<? extends AbstractRpcService>> classSet = reflections.getSubTypesOf(AbstractRpcService.class);
            for (Class<? extends AbstractRpcService> inner : classSet) {
                AbstractRpcService service = inner.newInstance();
                if (!serverMap.containsKey(service.getServiceName())) {
                    serverMap.put(service.getServiceName(), service);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化
     *
     * @throws Exception
     */
    public void startServer(int port) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler())
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new RequestWrapperCodec())
                                    .addLast(new ResponseWrapperCodec())
                                    .addLast(new BusinessServerHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            System.out.println("server listening at port: " + port);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws Exception {
        //启动server;
        new RpcServer().startServer(18888);
    }

}
