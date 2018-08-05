package com.recklessMo.serializer.java;

import com.recklessMo.common.model.RequestWrapper;
import com.recklessMo.common.util.SerializeUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * Created by hpf on 11/22/17.
 */
public class RequestWrapperCodec extends ByteToMessageCodec<RequestWrapper>{

    @Override
    protected void encode(ChannelHandlerContext ctx, RequestWrapper msg, ByteBuf out) throws Exception {
        SerializeUtils.writeObject(out, msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        SerializeUtils.getObject(in, out);
    }
}
