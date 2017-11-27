# nettyRpc

version 1.0.0:
1. 基于接口进行交互, 客户端动态代理
2. 序列化方式采用java自带
3. 底层通信基于netty4+
4. server配置采用内存写死的方式
5. 服务端采用继承指定service的方式注册服务
6. 不支持超时

##### version 1.0.0 主要问题:
1. 如何实现一个稳定的客户端连接池! 打算看看okHttpClient的源码,从成熟的框架中吸取一下经验




version 2.0.0: TODO
1. 泛化调用?
2. 序列化方式扩展?
3. server配置引入zookeeper?
4. 接入spring?