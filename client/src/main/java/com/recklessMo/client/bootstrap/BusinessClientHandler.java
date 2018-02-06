package com.recklessMo.client.bootstrap;

import com.recklessMo.rpc.model.ResponseWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;

/**
 * Created by hpf on 11/20/17.
 */
public class BusinessClientHandler extends SimpleChannelInboundHandler<ResponseWrapper> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseWrapper msg) throws Exception {
        //找到对应的client请求, 给请求设置promise为true,然后给请求设置值
        Promise<ResponseWrapper> responsePromise = RpcClient.getRequestWrapperMap().get(msg.getRequestId());
        if (responsePromise != null) {
            if (msg.getStatus() == 200) {
                responsePromise.setSuccess(msg);
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
