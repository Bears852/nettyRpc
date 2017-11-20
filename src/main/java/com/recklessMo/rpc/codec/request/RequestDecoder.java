package com.recklessMo.rpc.codec.request;

import com.recklessMo.rpc.model.RequestWrapper;
import com.recklessMo.rpc.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * Created by hpf on 11/18/17.
 */
public class RequestDecoder<T extends Serializer> extends ByteToMessageDecoder{

    //指定
    private T serializer;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

    }
}
