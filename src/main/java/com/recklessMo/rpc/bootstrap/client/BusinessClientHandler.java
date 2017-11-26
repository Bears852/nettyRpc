package com.recklessMo.rpc.bootstrap.client;

import com.recklessMo.rpc.model.RequestWrapper;
import com.recklessMo.rpc.model.ResponseWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by hpf on 11/20/17.
 */
public class BusinessClientHandler extends SimpleChannelInboundHandler<ResponseWrapper> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseWrapper msg) throws Exception {
        //找到对应的client请求, 给请求设置promise为true,然后给请求设置值
        RequestWrapper requestWrapper = RpcClient.getRequestWrapperMap().get(msg.getRequestId());
        if (requestWrapper != null) {
            if (msg.getStatus() == 200) {
                requestWrapper.getPromise().setSuccess(msg);
            } else {
                System.out.println("error: " + msg.getStatus());
                //设置错误!
            }
            //设置完毕之后就可以移出map了
            RpcClient.getRequestWrapperMap().remove(msg.getRequestId());
        } else {
            System.out.println("requestWrapper not found");
        }
    }


}
