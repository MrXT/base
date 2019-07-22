# Base

#### 项目介绍
Base后台
#### 软件架构
软件架构说明  
1.本项目采用springboot 1.5.7,mybatis,beetl搭建  
2.有关springboo教程（https://spring.io/projects/spring-boot#learn）  
3.项目代码架构  
    （1）common
            config  项目启动springboot会自动扫描的配置文件(效果相当与spring.xml，都是为了spring容器的初始化)
            controller
                LoginController  登录相关的接口与页面跳转
                SystemController 系统设置接口，修改
                vo
                    上面的controller所需要的值传递对象
            spring.inteceptor
                ProjectInterceptor:拦截器,主要做访问权限验证
                ControllerAop:返回数据统一封装
                ApiOperationAop:app接口访问参数验证
            util
               工具类
    (2) entity  所有的实体类（都加上了注释），对象关系映射到数据库表，继承BaseEntity,所有的实体包含公共字段utime(操作时间)，ctime(创建时间)，uid(最后操作人),valid(数据是否删除)
    (3) module
        sys (system)
            系统模块
            controller
                系统模块相关控制器与值传递对象
            dao
                数据处理层（继承Mapper插件（https://gitee.com/free/Mapper））
            service
                业务处理层（请看注释）
        bs (bussiness)
            业务处理相关模块
            controller
                   系统模块相关控制器与值传递对象
             dao
                   数据处理层（继承Mapper插件（https://gitee.com/free/Mapper））
             service
                   业务处理层（请看注释）
        cf (config)
            系统配置相关模块
                系统业务配置
        api (前端接口模块)
            *Controller : 前端所有接口的入口类

#### IDE运行教程

1. 准备jdk1.8，mysql5.7(将doc/base.sql导入数据库),maven环境，redis 3.2.5
2. 安装libs/common-1.0.jar,mvn install命令安装到本地仓库，成功后将common-1.0.pom替换
3. 修改application.properties里数据库的配置(账号与密码),与redis的配置(host,端口与密码)
5. ProjectApplication debug 运行即可
6. http://localhost
帐号admin  密码：123456
7.接口文档 ：http://localhost/api.html

#### MVN 打包
1、mvn install -Dmaven.test.skip=true -f pom.xml
2、在生成的target/base执行start.window.bat即可，linux:执行start.linux.sh 启动程序
3. http://localhost
  帐号admin  密码：123456
4.接口文档 ：http://localhost/api.html
