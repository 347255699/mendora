# 说明文档

## 技术方案
核心体系为Vertx Java、Guice和Lombok。详情如下列表所示：

1. guice：ioc容器，依赖注入
2. lombok：简化代码
3. slf4j-log4j12：日志记录  
	a. log4j：日志记录实现
4. commons-lang3：第三方工具包
5. Vertx Java  
	a. vertx-web：创建http服务器，http请求路由  
	b. vertx-hazelcase：集群管理器  
	&nbsp;&nbsp;&nbsp;&nbsp;1) hazelcast：集群管理器实现  
	c. vertx-auth-jwt：http认证授权服务  
	d. vertx-rx-java: rx化api实现  
	e. vertx-codegen：服务代理代码生成器，与vertx-service-proxy一起使用  
	f. vertx-service-proxy：服务代理实现  
	g. vertx-mysql-postgresql-client：postgre/mysql客户端  
	h. vertx-mongo-client：mongo客户端
> note：集成版本可查看项目.pom文件properties节点

## 体系结构
共划分为七个模块。分别为四个服务包，aider、web、data和service-rear。三个基础包，guice、util和service-facade。各包的详细作用可参见下表：

	-root
		-guice 集成了guice ioc容器的集群服务启动器
			-binder ioc容器绑定类，默认提供Vertx, ConfigHolder实现
			-cluster 集群启动器
			-properties 配置文件加载组件
			-scanner 扫描器，内置服务提供者(ServiceProvider)扫描器和路由(Route)扫描器
			-verticles verticle工具包，内置verticle默认扫描器
			-GuiceLauncher.java 启动入口
	
		-web 提供web服务，系统controller层载体
			-binder ioc容器绑定类，默认提供Router实现
			-constant web服务的相关常量
			-launcher web服务启动器
			-route route包，controller层主要业务包，注意模块划分
			-verticles verticle包
			-ApplicationMain.java 服务入口
	
		-service-facade rpc服务接口
			-generated 服务代理代码生成置放目录
			-java
			    -aop 切面编程相关类和注解
			    -scanner 扫描器，内置服务代理扫描器，外部使用需要注意扫描范围
	
		-service-rear rpc服务实现载体
			-ApplicationMain.java 服务入口
	
		-util 全局工具包，严格划分系统工具作用域
			-constant 通用常量
			-result 通用结果包
			-scanner 通用扫描器
	
		-aider 系统辅助服务载体
			-binder ioc容器绑定类，默认提供Router实现
			-constant aider服务的相关常量
			-launcher aider服务启动器
			-route route包，controller层主要业务包，注意模块划分
			-verticles verticle包
			-ApplicationMain.java 服务入口
	
		-data 系统数据访问层基础服务载体，注意该模块不提供业务服务
			-binder ioc容器绑定类，默认提供数据库客户端实现(postgre/mysql&mongo)
			-client 客户端加载器
			-constant 数据访问相关常量
			-launcher 数据访问服务启动器
			-service 数据访问实现载体
			-verticles verticle包
			-ApplicationMain.java 服务入口
	    
	    -generate 代码生成辅助模块，将facade模块的代码生成功能转移到该包下，跟业务功能剥离开
	        -generated 代码生成输出目录
	        -java 接口置放处
	        
### 基础结构图
![基础结构图](doc/draft/structure.png "png")
## 架构思想
### 微服务
系统采用微服务形式搭建，系统由多个微服务构成，微服务可部署到一至多台服务器上且可部署一到多个实例。微服务之间可互相感知存在和通信。微服务之间每`15s`发送一次存在感知请求。若某个服务断开可以通过日志中显眼的服务断开通知知晓。
### 微服务种类
服务种类可划分为aider、web、data和service-rear。
#### 1. aider服务
aider服务在系统中主要起到辅助作用，不具备系统业务功能，面向开发或维护人员，主要提供对其他服务的重启、日志查询、测试和应用检查等功能。aider服务拥有自己的http环境和接口，与系统业务完全隔离。仅需要部署单例。且需要做严格的权限策略，需要部署在内网以提高安全性。
#### 2. web服务
系统业务web服务载体。Controller层载体，面向前端。Controller层中的所有http接口都应该建立标准文档。默认提供认证、授权和用户操作日志记录等基础功能。其他服务接口需要置放在该服务的route包中，且需要严格划分业务模块。可部署一至多个实例。
#### 3. data服务
系统数据访问服务载体，可以理解为dao层。提供基础的无业务的数据访问服务。如分页数据访问，可在该层实现物理分页。数据库客户端由该层维护。可部署一至多个实例。
#### 4. service-rear服务
系统业务服务载体，可理解为service层。提供具体的系统业务服务。服务需要严格划分业务模块。可部署一至多个实例。服务实例承载的业务可以相同和不同。承载不同业务的实例可以通过接口互相调用，但要注意依赖关系。该服务中的业务可与web服务呈现一对多或一对一的关系。
### 基础包
基础包可划分为guice、util和service-facade。
#### 1. guice包
其他服务都需要依赖该基础包，可理解为其他服务的内核，服务中的配置参数加载、集群启动、verticle扫描都由该基础包完成。该包下还提供了服务提供者扫描器，可在任何一个服务中启用。
#### 2. util包
提供通用的工具包，如常量、包扫描器和结果集等。
#### 3. service-facade包
rpc服务接口，可在该包中定义服务接口，通常暴露的是服务代理接口。由`service-proxy`组件生成。该包中内置了服务接口扫描器，可通过该扫描器返回一个`Binder`(guice中的module)，然后注入到ioc容器中。需要注意提供给扫描器不同的扫描范围(包路径)。服务接口需要严格按照模块放置在不同的包结构中。不同的服务内可以仅扫描需要的包路径，以此来划分范围和模块。
## Verticle实例
服务可由一个或多个Verticle组成，Verticle拥有一些有用的特性，如增加多个实例、高可用等。系统中的所有Verticle均可自定义这些配置。Verticle可以理解为一个粒度较小的服务。guice包中的Verticle扫描器会将一个基础的ioc容器即Injector注入到每一个Verticle中，即每一个Verticle中都可以拿到injector实例，可以往该injector实例(ioc容器)中注入一组bean实例，以此来定制verticle内部的ioc容器。
## 最佳实践
### 编写http接口
http接口主要置放在web模块中的route包，严格按照业务模块划分。  
使用方式，创建route类，在类上通过@Route和继承AbstractRoute的方式声明Route。通常在Route中有两种方式来绑定请求地址和请求处理器。通过在方法上添加注解(@RequestRouting)和覆盖route方法并在route方法中通过调用`Router.route()`的方式。
> note：详情可查看web模块`org.mendora.web.route.aider.DemoRoute`。
#### @RequestRouting
```java
@Route("/root")
public class DemoRoute extends AbstractRoute{
    @RequestRouting(path = "/demo", method = HttpMethod.GET)
    public void demo(RoutingContext rc) {
        rc.response().end("<h1>Just a test demo.</h1>");
    }
}
```
通过路径`/root/demo`发起`get`请求即可访问。习惯`springmvc`的同学可以采用该方式。
#### 覆盖route方法
```java
@Route("/root")
public class DemoRoute extends AbstractRoute{
    @Override
    public void route(String prefix) {
        router.route(prefix + "/demo2").handler(rc -> {
            rc.response().end("<h1>Just a test demo2.</h1>");
        });
    }
}
```
通过路径`/root/demo2`发起`get`请求即可访问。
### 编写Service
Service主要采用异步rpc方式，即服务接口与实现分离，无论服务实现置放在哪个服务内(服务可以身处任何多台服务器上)，只要通过相关的注册规则，即可让拥有接口的任何一方异步调用rpc服务。
#### Service接口
编写服务的第一步是设计接口，设计接口需要满足几个规则。我们编写的异步Service实现实际上是通过Vertx EventBus通讯实现，因此需要让Vertx相关组件帮我们生成相应的Service Proxy。详细实现将不在这里赘述。可查看[Vertx Java Service Proxy章节](https://vertxchina.github.io/vertx-translation-chinese/services/ServiceProxies.html)。  
首先需要在接口上加上`@ProxyGen`、`@VertxGen`和`@ServiceFacade`，`ProxyGen`用于生成服务代理类，`VertxGen`用于生成Rx风格的服务代理类，`ServiceFacade`用于实现aop切面编程。我们一般会选择使用Rx风格的代理类。然后需要在接口中定义一个EventBus地址常量。定义好的接口大概是这样的。
```java
@ProxyGen
@VertxGen
@ServiceFacade(proxy = PostgreAccesserVertxEBProxy.class, rxProxy = org.mendora.service.facade.dataAccesser.rxjava.PostgreAccesser.class)
public interface PostgreAccesser {

    String EB_ADDRESS = "eb.data.postgre.accesser";

    @Fluent
    PostgreAccesser unRegister(Handler<AsyncResult<Void>> handler);
   
    @Fluent
    PostgreAccesser pause(Handler<AsyncResult<Void>> handler);
   
    @Fluent
    PostgreAccesser resume(Handler<AsyncResult<Void>> handler);
   
    @Fluent
    PostgreAccesser isRegistered(Handler<AsyncResult<Boolean>> handler);
    
    @Fluent
    PostgreAccesser query(String sql, Handler<AsyncResult<JsonObject>> handler);
   
    void update(String sql);
}
```
实例中`Handler<AsyncResult<JsonObject>> handler`参数用于异步回调。若无需回调可不携带该参数。
> note：@Fluent用于返回当前实例实现链式调用。接口必须定义在service-facade中，调用一方需要依赖该基础包。
> 建议添加`unRegister`、`pause`、`isRegistered`和`resume`等方法用于实现对服务的操作。

service-facade中内置了一个服务代理扫描器`ServiceRxProxyScanner`；用于扫描指定包中的接口，返回一个`ServiceRxProxyBinder`实例；而后在调用方注入guice的ioc容器，最后通过依赖注入的方式来调用服务。在调用方扫描接口看起来是这样的：
```java
    // default service proxy package path.
    String proxyIntoPackage = configHolder.property(WebConst.WEB_SERVICE_PROXY_INTO_PACKAGE);
    // scanning
    ServiceRxProxyBinder serviceRxProxyBinder = new ServiceRxProxyScanner.scan(proxyIntoPackage);
    // injecting ioc container.
    injector = injector.createChildInjector(serviceProxyBinder);
```
扫描完成后即可通过@Inject的方式注入服务，在Route中使用看起来是这样的：
```java
@Route("/root")
public class DemoRoute extends AbstractRoute {
    @Inject
    PostgreAccesser service;

    @RequestRouting(path = "/sqlStatement/query", method = HttpMethod.POST)
    public void query(RoutingContext rc) {
        service.rxQuery(rc.getBodyAsJson().getString(SqlReferences.STATEMENT.val()))
                .subscribe(replyJson -> WebResult.consume(replyJson, rc));
    }
}
```
> note: 在调用服务接口前需要确保服务实现已经在EventBus上注册。上文中缺失了如何生成服务代理类细节，详情同样可以查看[Vertx Java Service Proxy章节](https://vertxchina.github.io/vertx-translation-chinese/services/ServiceProxies.html)。此处仅需要知道执行mvn:compiler生命周期即可生成服务代理类。
##### 迎合aop
需要注意Service Proxy组件生成的代码并不能兼容aop，因此需要做几点改动以适应aop编程。
1. 在接口上添加`@ServiceFacade`注解。并绑定该接口相关的代理类和rx版代理类。如下所示：
```java
@ServiceFacade(proxy = PostgreAccesserVertxEBProxy.class, rxProxy = org.mendora.service.facade.dataAccesser.rxjava.PostgreAccesser.class)
public interface PostgreAccesser{}
```
2. 在接口代理类第一个构造函数上添加`@Inject`注解。并删除构造函数上的`address`，替换成接口定义的默认EventBus地址。如下所示：
```java
public class PostgreAccesserVertxEBProxy implements PostgreAccesser {

    private Vertx _vertx;
    private String _address;
    private DeliveryOptions _options;
    private boolean closed;

    @Inject
    public PostgreAccesserVertxEBProxy(Vertx vertx) {
        this(vertx, EB_ADDRESS, null);
    }

    public PostgreAccesserVertxEBProxy(Vertx vertx, String address, DeliveryOptions options) {
        this._vertx = vertx;
        this._address = address;
        this._options = options;
        try {
            this._vertx.eventBus().registerDefaultCodec(ServiceException.class, new ServiceExceptionMessageCodec());
        } catch (IllegalStateException ex) {}
    }
}
```
3. 同样在接口rx版代理类的构造函数添加`@Inject`。如下所示：
```java
@io.vertx.lang.rxjava.RxGen(org.mendora.service.facade.dataAccesser.PostgreAccesser.class)
public class PostgreAccesser {

    private final org.mendora.service.facade.dataAccesser.PostgreAccesser delegate;

    @Inject
    public PostgreAccesser(org.mendora.service.facade.dataAccesser.PostgreAccesser delegate) {
        this.delegate = delegate;
    }
}
```
##### aop实践
可在service-facade模块的aop包中编写拦截器，在ioc容器中的bean实例方法执行前后做相应的处理。  
编写步骤大概是这样的：
1. 编写注解，添加至需要执行拦截的方法上。一般是添加到接口的rx版代理类上。如下所示：
```java
@io.vertx.lang.rxjava.RxGen(org.mendora.service.facade.dataAccesser.PostgreAccesser.class)
public class PostgreAccesser {
    @Monitor
    public PostgreAccesser query(String sql, Handler<AsyncResult<JsonObject>> handler) { 
        delegate.query(sql, handler);
        return this;
    }
}
```
2. 编写MethodInterceptor，如下所示：
```java
public class MonitorMethodInterceptor implements org.aopalliance.intercept.MethodInterceptor {
    private Logger log = LoggerFactory.getLogger(MonitorMethodInterceptor.class);
    
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        before(methodInvocation);
        Object object = methodInvocation.proceed();
        after(methodInvocation);
        return object;
    }

   /**
    * before service method execute.
    *
    * @param methodInvocation
    */
    private void before(MethodInvocation methodInvocation) {
        // before service method execute.
        String name = methodInvocation.getMethod().getName();
        log.info(name);
    }

   /**
    * after service method execute.
    *
    * @param methodInvocation
    */
    private void after(MethodInvocation methodInvocation) {
        // after service method execute.
        String name = methodInvocation.getMethod().getName();
        log.info(name);
    }
}
```
3. 将MethodInterceptor绑定到Binder中。如下所示：
```java
    injector = injector.createChildInjector(binder -> {
        binder.bindInterceptor(Matchers.any(), Matchers.annotatedWith(Monitor.class), new MonitorMethodInterceptor());
        // binding instances
    });
```
> note：以上所有操作必须在Service Proxy组件生成代码后操作，且一定要在facade模块内定义和编写。详情可参考[Guice AOP](https://github.com/google/guice/wiki/AOP)。
#### Service实现
Service实现可以在任何一个服务内编写和注册。在编写服务实现类时需要添加`@ServiceProvider`和实现`register`方法已供扫描器完成服务注册。一个服务实现看起来是这样的：
```java
@ServiceProvider
public class PostgreAccesserImpl implements PostgreAccesser {
     @Inject
     private Vertx vertx;
     
    /**
     * register service
     */
     public void register() {
        ProxyHelper.registerService(PostgreAccesser.class, vertx.getDelegate(), this, EB_ADDRESS);
     }
     
     /**
      * execute find sql statement without params
      *
      * @param sql
      * @param handler
      */
      @Override
      public PostgreAccesser query(String sql, Handler<AsyncResult<JsonObject>> handler) {
        // execute sql find data.
        // return query result.
        handler.handle(Future.succeededFuture(JsonResult.succWithRows(rows)));
      }
}
```
> note：需要注意注册服务时需要四个参数，分别是服务类型、Vertx实例、服务实现实例和EventBus地址。一般EventBus地址由实现的接口提供。
通过服务提供者扫描器(`ServiceProviderScanner`)来注册服务。register由服务实现类自行提供，服务接口中无需定义。

注册服务看起来是这样的：
```java
    ServiceProviderScanner scanner = new ServiceProviderScanner();
    scanner.scan(configHolder.property(DataConst.DATA_SERVICE_INTO_PACKAGE), DataVerticle.class.getClassLoader(), injector);
```
## 其他
### 扫描器
系统提供了四种扫描器用于批量操作类。分别是VerticleScanner、RouteScanner、ServiceProviderScanner和ServiceProxyScanner。扫描单位为类包。
### Util包
提供了一些特别优秀的工具包，如`result`，顾名思义用于快速生成常用结果集，如Http请求结果集等。
### doc文件夹
架构中所用到的设计文档草稿都在该目录下，其他说明文档可以copy一份置放在该目录下，让相关开放人员可以快速浏览。
### generate包
该包主要用于协助service-facade包生成代码，不属于系统功能模块或基础模块。generate包承当了原来service-facade包的服务代理类代码生成功能。让service-facade包能专注定义接口。让代码生成辅助特性与实际业务功能接口分离。






