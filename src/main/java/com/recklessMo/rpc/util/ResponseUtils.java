package com.recklessMo.rpc.util;

import com.recklessMo.rpc.model.ResponseWrapper;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by hpf on 11/21/17.
 */
public class ResponseUtils {

    /**
     *
     * 构建response
     *
     * @param requestId
     * @param status
     * @param result
     * @return
     */
    public static ResponseWrapper createResponse(String requestId, int status, Object result){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setStatus(status);
        responseWrapper.setRequestId(requestId);
        responseWrapper.setResult(result);
        return responseWrapper;
    }

    /**
     *
     * 给客户端发送404
     *
     * @param requestId
     */
    public static void send404(ChannelHandlerContext ctx, String requestId){
        ResponseWrapper responseWrapper = createResponse(requestId, 404, "method not found !");
        ctx.writeAndFlush(responseWrapper);
    }

    /**
     *
     * 给客户端发送500
     *
     * @param ctx
     * @param requestId
     */
    public static void send500(ChannelHandlerContext ctx, String requestId){
        ResponseWrapper responseWrapper = createResponse(requestId, 500, "internal error !");
        ctx.writeAndFlush(responseWrapper);
    }

    /**
     *
     * 发送正常的结果到客户端
     *
     * @param ctx
     * @param requestId
     * @param result
     */
    public static void send200(ChannelHandlerContext ctx, String requestId, Object result){
        ResponseWrapper responseWrapper = createResponse(requestId, 200, result);
        ctx.writeAndFlush(responseWrapper);
    }


}
