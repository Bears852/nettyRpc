package com.recklessMo.rpc.bootstrap.client;

import com.alibaba.fastjson.JSON;
import com.recklessMo.rpc.model.ResponseWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by hpf on 11/20/17.
 */
public class BusinessClientHandler extends SimpleChannelInboundHandler<ResponseWrapper>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseWrapper msg) throws Exception {

        System.out.println("getResponse: " + JSON.toJSONString(msg));

    }


}
