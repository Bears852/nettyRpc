package com.recklessMo.server.bootstrap;

import com.recklessMo.rpc.annotation.RpcServiceFlag;
import com.recklessMo.rpc.bootstrap.protocol.IRobotProtocol;

/**
 * TODO 如何让服务自动加载, 可以通过继承, 或者标签
 * <p>
 * 目前通过继承来实现
 * <p>
 * <p>
 * Created by hpf on 11/21/17.
 */
@RpcServiceFlag("RobotService")
public class RobotService implements IRobotProtocol {

    @Override
    public void sendMsg() { }

}
