package com.recklessMo.rpc.codec.request;

import com.recklessMo.rpc.model.RequestWrapper;
import com.recklessMo.rpc.serializer.ISerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by hpf on 11/18/17.
 */
public class RequestEncoder<T extends ISerializer> extends MessageToByteEncoder<RequestWrapper>{

    //指定
    private T serializer;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RequestWrapper requestWrapper, ByteBuf byteBuf) throws Exception {


    }
}
