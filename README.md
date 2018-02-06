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
2. commons-object似乎也行

version 2.0.0: TODO
1. 实现分组件打包，分为客户端client，服务端server，公用组件common，注册registry，序列化serializer，底层传输transport。done
2. 实现一个客户端连接池