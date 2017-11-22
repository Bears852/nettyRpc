package com.recklessMo.rpc.transport.connection;

import com.recklessMo.rpc.bootstrap.client.BusinessClientHandler;
import com.recklessMo.rpc.serializer.java.RequestWrapperCodec;
import com.recklessMo.rpc.serializer.java.ResponseWrapperCodec;
import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by hpf on 11/20/17.
 */
public class FixedChannelPoolHandler implements ChannelPoolHandler {

    @Override
    public void channelReleased(Channel ch) throws Exception {
    }

    @Override
    public void channelAcquired(Channel ch) throws Exception {
    }

    @Override
    public void channelCreated(Channel ch) throws Exception {
        SocketChannel  socketChannel = (SocketChannel)ch;
        socketChannel.pipeline()
                .addLast(new LoggingHandler())
                .addLast(new RequestWrapperCodec())
                .addLast(new ResponseWrapperCodec())
                .addLast(new BusinessClientHandler());
    }

}
