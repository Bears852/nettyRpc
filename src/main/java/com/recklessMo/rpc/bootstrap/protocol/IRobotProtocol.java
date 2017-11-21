package com.recklessMo.rpc.bootstrap.protocol;

/**
 * 1. 暂时先采用基本类型
 * 2. TODO 复杂类型
 *
 *
 * Created by hpf on 11/17/17.
 */
public interface IRobotProtocol {

    /**
     * 给机器人发消息, 返回机器人的回复
     *
     * @param msg
     * @return
     */
    String sendMsg(String msg);


}
