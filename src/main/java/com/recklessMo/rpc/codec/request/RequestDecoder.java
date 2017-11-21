package com.recklessMo.rpc.codec.request;

import com.recklessMo.rpc.serializer.ISerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by hpf on 11/18/17.
 */
public class RequestDecoder<T extends ISerializer> extends ByteToMessageDecoder{

    //指定
    private T serializer;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

    }
}
