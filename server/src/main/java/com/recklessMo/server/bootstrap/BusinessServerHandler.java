package com.recklessMo.server.bootstrap;

import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hpf on 11/21/17.
 */
public class BusinessServerHandler extends SimpleChannelInboundHandler<RequestWrapper> {

    /**
     * 业务线程池,用cached
     */
    private static ExecutorService workPool = Executors.newCachedThreadPool();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();
    }

    /**
     * 父类已经做了消息类型的转换!
     * <p>
     * TODO 重名的方法是怎么处理的
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestWrapper msg) throws Exception {
        //做业务逻辑
        //先在业务线程里面做吧.
        final ChannelHandlerContext channelHandlerContext = ctx;
        final RequestWrapper requestMsg = msg;
        workPool.submit(() -> {
            run(channelHandlerContext, requestMsg);
        });
    }

    /**
     * 具体的处理逻辑
     * <p>
     * TODO 目前没有批量处理request, 后续添加, 应该增加吞吐量!
     *
     * @param channelHandlerContext
     * @param requestMsg
     */
    private void run(ChannelHandlerContext channelHandlerContext, RequestWrapper requestMsg) {
        Object service = RpcServer.getServerMap().get(requestMsg.getServiceName());
        if (service == null) {
            ResponseUtils.send404(channelHandlerContext, requestMsg.getRequestId());
            return;
        }
        Method currentMethod = null;
        //暂时顺序查找,后续变成提前放入map
        Method[] methods = service.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(requestMsg.getMethodName())) {
                currentMethod = method;
                break;
            }
        }
        //执行
        if (currentMethod == null) {
            //继续404;
            ResponseUtils.send404(channelHandlerContext, requestMsg.getRequestId());
            return;
        }
        Object result = null;
        try {
            result = currentMethod.invoke(service, requestMsg.getParameters());
        } catch (Exception e) {
            e.printStackTrace();
            //return 500;
            ResponseUtils.send500(channelHandlerContext, requestMsg.getRequestId());
        }
        //回写逻辑
        ResponseUtils.send200(channelHandlerContext, requestMsg.getRequestId(), result);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println(cause);
        ctx.fireExceptionCaught(cause);
    }

}
