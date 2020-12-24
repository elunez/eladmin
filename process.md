<h1 style="text-align: center">EL-ADMIN 后台管理系统学习流程记录</h1>

【EL-Admin】[EL-Admin手册](https://el-admin.vip/guide/ksks.html#%E5%BC%80%E5%8F%91%E5%87%86%E5%A4%87)

# 1. 项目依赖

## 环境配置

    1、JDK：1.8+ 
    安装教程：https://www.runoob.com/java/java-environment-setup.html
    2、Redis 3.0+
    安装教程：https://www.runoob.com/redis/redis-install.html
    3、Maven 3.0+
    安装教程：https://www.runoob.com/maven/maven-setup.html
    4、MYSQL 5.5.0+
    安装教程：https://www.runoob.com/mysql/mysql-install.html
    5、Node v10+
    安装教程：https://www.runoob.com/nodejs/nodejs-install-setup.html

> 前端安装完 node 后，最好设置下淘宝的镜像源，不建议使用 cnpm（可能会出现奇怪的问题）

```bash
npm config set registry https://registry.npm.taobao.org
配置后可通过下面方式来验证是否成功
npm config get registry

在 ~/.npmrc 加入下面内容，可以避免安装 node-sass 失败
sass_binary_site=https://npm.taobao.org/mirrors/node-sass/

.npmrc 文件位于
win：C:\Users\[你的账户名称]\.npmrc
linux：直接使用 vi ~/.npmrc
```


## 开发准备
1. idea安装lombok插件
2. 使用MapStruct映射实体：[熟悉MapStruct](https://www.jianshu.com/p/3f20ca1a93b0) 
3. Spring boot 的基础：[Spring Boot 2.0 学习](https://github.com/ityouknow/spring-boot-examples)
4. Vue基础，，推荐入门视屏教程 [网易云课堂](https://study.163.com/course/courseMain.htm?courseId=1004711010)


## 调试后端项目
    1. 依赖不能导入
        重新下载依赖，先删除.idea文件，再重新import项目后，在pom.xml中，点右键run maven install 即可解决
    2. Access denied for user 'admin'@'localhost' (using password: YES)
        检查 1.账号密码问题    2. 远程登录被拒绝，需要use mysql; select host ,user from user; //查看结果是不是root用户仅允许本地 localhost 登录，是的话，就要修改它的host为%，表示任意IP地址都可以登录
        update user set host =’%’ where user=‘root’;   flushprivileges    刷新缓存
    3. 启动后，使用webstrom运行web项目。



## 后端项目结构

- eladmin-common 为系统的公共模块，各种工具类，公共配置存在该模块
- eladmin-system 为系统核心模块也是项目入口模块，也是最终需要打包部署的模块
- eladmin-logging 为系统的日志模块，其他模块如果需要记录日志需要引入该模块
- eladmin-tools 为第三方工具模块，包含：图床、邮件、云存储、本地存储、支付宝
- eladmin-generator 为系统的代码生成模块，代码生成的模板在 system 模块中

## 详细结构

```text
- eladmin-common 公共模块
    - annotation 为系统自定义注解
    - aspect 自定义注解的切面
    - base 提供了Entity、DTO基类和mapstruct的通用mapper
    - config 自定义权限实现、redis配置、swagger配置、Rsa配置等
    - exception 项目统一异常的处理
    - utils 系统通用工具类
- eladmin-system 系统核心模块（系统启动入口）
	- config 配置跨域与静态资源，与数据权限
	    - thread 线程池相关
	- modules 系统相关模块(登录授权、系统监控、定时任务、运维管理等)
- eladmin-logging 系统日志模块
- eladmin-tools 系统第三方工具模块
- eladmin-generator 系统代码生成模块
```



# 2. 后端手册

## 2.1 新建模块



## 2.2 权限控制

> 权限控制：
>
> ​	