package com.recklessMo.rpc.benchmark.client;

import com.recklessMo.rpc.bootstrap.client.RpcClient;
import com.recklessMo.rpc.bootstrap.protocol.IRobotProtocol;
import com.recklessMo.rpc.transport.connection.ClientConnectionPool;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.SystemPropertyUtil;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/**
 * Created by hpf on 11/28/17.
 */
public class BenchmarkClient {


    /**
     * 多大并发量
     */
    private int threadCount;
    /**
     * 请求总数!
     */
    private int requestCount;
    /**
     * 结果
     */
    private final List<List<Long>> result = new Vector<>();

    private CountDownLatch stop = null;

    private IRobotProtocol robotProtocol;
    private long total = 0L;


    public BenchmarkClient(int threadCount, int requestCount){
        this.threadCount = threadCount;
        this.requestCount = requestCount;
        this.stop = new CountDownLatch(threadCount);
        //创建客户端连接池
        ClientConnectionPool clientConnectionPool = RpcClient.createConnectionPool();
        //创建异步调用回调执行器
        EventExecutor eventExecutor = RpcClient.createEventExecutor();
        //创建同步客户端
        robotProtocol = RpcClient.createService("RobotService", IRobotProtocol.class, clientConnectionPool, eventExecutor, true);
    }

    /**
     * 开始
     */
    public void start() throws Exception{
        for(int i = 0; i < threadCount; i++){
            new Thread(() -> {
                List<Long> single = new LinkedList<>();
                for(int j = 0; j < requestCount; j++){
                    long now = System.currentTimeMillis();
                    robotProtocol.sendMsg("test");
                    single.add(System.currentTimeMillis() - now);
                }
                result.add(single);
                System.out.println("threadName: " + Thread.currentThread().getName() + " done!");
                stop.countDown();
            }).start();
        }

        stop.await();
        //print;
        result.stream().forEach(single -> {
            for(long s : single){
                total += s;
            }
        });

        long num = requestCount * threadCount;
        System.out.println("totalCount: " + num + ", totalTime: " + total + ", avg: " + ((double)total)/num + "ms");
    }


    public static void main(String[] args) throws Exception{
        BenchmarkClient client =  new BenchmarkClient(2, 100);
        client.start();
    }


}
