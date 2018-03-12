# Mendora智能中控中心
> 项目目标为智能家居中控系统。  

项目目前处在概念起步阶段。第一步为通用后台系统的开发。通用后台系统核心业务为3A模型。即登录、授权和认证。
## 通用系统
### 系统结构

    -mendora
        -mendora-base
            -java 
                -properties 配置文件、日志功能初始化
                -scanner 包扫描器
                -utils 基础工具类
                -verticles Verticle基类
                BaseLauncher 模块启动器
  
        -mendora-aaa
            -java
                -constant 常量
                -launcher 模块启动器
                -route Route类包
                -verticles Verticle类包
                    -WebVerticle web服务器
                -ApplicationMain 入口
            -resources
                -config
                    config.properties 核心配置文件
                    log4j.properties 日志配置
                -script
                    -launch.sh linux下jar启动脚本
                    
### base
base模块提供模块初始化服务，内置配置文件加载、日志功能初始化和类包扫描器。作为其他业务模块的基础依赖包。

### aaa
目标为提供3a服务，现已内置web服务器和HTTP请求路由以及集群服务。

### 实例
`Route`需要实现`org.mendora.web.Route`接口，实现`route`方法，才能被包扫描器扫描到且需要置放在配置文件指定的类包下。  
同样的，`Verticle`需要继承`SimpleVerticle`，覆盖`options`方法返回一个`DeploymentOptions`即可定制部署参数。且需要置放在配置文件指定的类包下。


