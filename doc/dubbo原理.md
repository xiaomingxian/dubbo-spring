#### 一次完整的RPC调用流程(同步调用)

1. 消费者调用以本地调用方式调用服务
2. client stub 接收到调用后负责将方法，参数等组装成能够进行网络传输的消息体
3. client stub 找到服务地址，并将消息发送到服务端
4. server stub 收到消息后进行解码
5. server stub 根据解码结果调用本地服务
6. 本地服务执行并将结果返回给 server stub
7. server stud 将结果打包成消息并发送至消费方
8. client stub 收到消息，并进行解码
9. 消费者得到最终结果
   2-9为RPC调用过程

#### NETTY 模型

服务器启动

1. 初始化channel
2. 注册channel到selector
3. 轮询accept事件
4. 处理accept建立连接 channel
5. 注册channel到selector
6. 轮询读写事件
7. 处理读写事件

#### 各个层

exchange层 建立连接
Transport 数据传输 Netty连接

#### 标签解析

```
spring的 BeanDefinitionParser
 DubboBeanDefinitionParser  implements  BeanDefinitionParser
    
    service 解析完成后 封装成ServiceBean
    referace 解析完成后 封装成ReferaceBean
 ...
 其他框架BeanDefinitionParser implements  BeanDefinitionParser

```

#### 服务暴露过程

1. dubbo底层会启动netty服务器来监听20880：dubboExporter(dubbo暴露器) 会 openServer()
2. RegistryExporter(Registry暴露器) 会 注册url与执行器的关系 ProviderConsumerRegTable.registerProvider

```
//String 是url eg:zk:localhost:20880/dubbo/provider/com.xxm.AService ,ProviderInvokerWrapper 里有具体的实现类
public static ConcurrentHashMap<String, Set<ProviderInvokerWrapper>> providerInvokers = new ConcurrentHashMap();

```

```
//1 标签解析器 ServiceBean
// 如果是provider类型 用 ServiceBean 去处理
DubboBeanDefinitionParser {
        ... parse{
                ...
                    else if (ProviderConfig.class.equals(beanClass)) {
                                parseNested(element, parserContext, ServiceBean.class, true, "service", "provider", id, beanDefinition);
                            } 
                ...
        }
}


        
        
//2. 暴露服务     
 public class ServiceBean<T> extends ServiceConfig<T> implements InitializingBean, DisposableBean, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent>, BeanNameAware {
 {
     //不是延迟的 并且 不是暴露过的 并且不是不暴露的 就进行暴露
     //ApplicationListener<ContextRefreshedEvent> 实现他的以下方法 在容器刷新时进行暴露
     public void onApplicationEvent(ContextRefreshedEvent event) {
        if (this.isDelay() && !this.isExported() && !this.isUnexported()) {
            if (logger.isInfoEnabled()) {
                logger.info("The service ready on spring started. service: " + this.getInterface());
            }

            //底层netty启动监听20880
            this.export();
        }
    }
    
    
    //InitializingBean 的  afterPropertiesSet
 }
```

#### 服务引用对象获取过程

1. dubboExpoter 建立netty客户端与netty建立连接
2. 从spring的FactoryBean中getObject() ... 获取代理对象

```
   ReferenceConfig中的
      ref=createProxy(map)
      invoker=refprotocol.refer(interfaceClass,urls.get(0)) //接口,远程服务地址    
   RegistryProtocol 中的doRefer(...)
   DubboProtocol 中的
      public <T> Invoker<T> refer(Class<T> serviceType, URL url) throws RpcException {
         this.optimizeSerialization(url);
         //this.getClients(url) 连接netty客户端，invoker是返回的执行者
         DubboInvoker<T> invoker = new DubboInvoker(serviceType, url, this.getClients(url), this.invokers);
         this.invokers.add(invoker);
         return invoker;
      }

```

3. RegistryExporter(Registry暴露器) 会 注册url与执行器的关系 ProviderConsumerRegTable.registerConsumer

```
   public static ConcurrentHashMap<String, Set<ConsumerInvokerWrapper>> consumerInvokers = new ConcurrentHashMap();
```

