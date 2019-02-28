# eladmin

项目基于 Spring Boot 2.1.0 、 Spring boot Jpa、 Spring Security、redis、Vue的前后端分离的权限管理系统，项目采用分模块开发方式， 权限控制采用 RBAC（Role-Based Access Control，基于角色的访问控制），前端菜单支持动态路由

#### 前端源码
eladmin-qt和eladmin-qd只是命名方式的区别，无其他区别
- 码云：[https://gitee.com/elunez/eladmin-qt](https://gitee.com/elunez/eladmin-qt)
- github：[https://github.com/elunez/eladmin-qd](https://github.com/elunez/eladmin-qd)

#### eladmin开发文档
[http://docs.auauz.net/#/](http://docs.auauz.net/#/)

#### 预览地址
[http://auauz.net](http://auauz.net)

##### 用户账号密码

- 管理员： admin
- 测试用户： test
- 密码： 123456

#### 开发环境

- JDK：8
- IDE：IntelliJ IDEA （后端）
- IDE：JetBrains WebStorm  （前端）
- 依赖管理：Maven
- 数据库：MySQL 5.5.59

#### 功能模块
```
- 系统管理
    - 用户管理 提供用户的相关配置
    - 角色管理 角色菜单进行权限的分配
    - Swagger文档 localhost:8000/swagger-ui.html
    - 权限管理 权限细化到接口
    - 菜单管理 已实现菜单动态路由，后端可配置化，支持多级菜单
    - 定时任务 整合Quartz做定时任务，加入任务日志，任务运行情况一目了然
    - 代码生成 高灵活度一键生成前后端代码，减少百分之80左右的工作任务
- 系统监控
    - 操作日志 使用apo记录用户操作日志
    - 异常日志 记录操作过程中的异常，并且提供查看异常的堆栈信息
    - 系统缓存 使用jedis将缓存操作可视化，并提供对redis的基本操作，可根据需求自行扩展
    - 实时控制台 实时打印logback日志，来自微强迫症患者的精心配色，更好的监控系统的运行状态
    - SQL监控 采用druid 监控数据库访问性能，默认用户名admin，密码123456
- 三方工具
    - 邮件工具 配合富文本，发送html格式的邮件
    - SM.MS免费图床 挺好用的一个图床，作为公共图片上传使用
    - 七牛云存储 这个就不多说了
    - 支付宝支付 提供了测试账号，可自行测试
- 组件管理
    - 图标库 系统图标来自 https://www.iconfont.cn/
    - 富文本 集成wangEditor富文本
```
#### 项目结构
```
# 项目模块如下
- eladmin-common 公共模块
    - aop.limit 接口限流自定义注解
    - exception 项目统一异常的处理
    - mapper mapstruct的通用mapper
    - redis redis缓存相关配置
    - swagger2 接口文档配置
    - utils 通用工具
- eladmin-system 系统核心模块
	- config 配置跨域与静态资源
	- modules 系统相关模块
		- monitor 系统监控
		    - config 配置日志拦截器与WebSocket等
		    - domain 实体类
		    - repository 数据库操作
		    - rest 前端控制器
		    - service 业务接口
		        - impl 业务接口实现
		        - query 业务查询
        - quartz 定时任务
        - security 系统安全
	        - config  JWT的安全过滤器配置
		    - rest 用户登录授权的接口
		    - security 配置spring security
		    - service 用户登录与权限的处理
		    - utils JWT工具
    	- system 系统管理
- eladmin-logging 系统日志模块
- eladmin-tools 系统第三方工具模块
- eladmin-generator 系统代码生成模块
```
#### 后端技术栈

- 基础框架：Spring Boot 2.1.0.RELEASE
- 持久层框架：Spring boot Jpa
- 安全框架：Spring Security
- 缓存框架：Redis
- 日志打印：logback+log4jdbc
- 接口文档 swagger2
- 其他：fastjson、aop、MapStruct等

#### 前端技术栈
- node
- vue
- vue-router
- axios
- element ui

#### 系统预览
<table>
    <tr>
        <td><img src="https://i.loli.net/2018/12/22/5c1e10c781eec.png"/></td>
        <td><img src="https://i.loli.net/2018/12/22/5c1e10c7890ab.png"/></td>
    </tr>
    <tr>
        <td><img src="https://i.loli.net/2019/02/28/5c7795b707347.png"/></td>
        <td><img src="https://i.loli.net/2018/12/22/5c1e10c7b089b.png"/></td>
    </tr>
    <tr>
        <td><img src="https://i.loli.net/2018/12/22/5c1e10c7b9c30.png"/></td>
        <td><img src="https://i.loli.net/2018/12/22/5c1e10c7b7504.png"/></td>
    </tr>
    <tr>
        <td><img src="https://i.loli.net/2018/12/22/5c1e10c7a9f7d.png"/></td>
		<td><img src="https://i.imgur.com/FzVaAlS.png"/></td>
    </tr>
    <tr>
        <td><img src="https://i.imgur.com/ah3X2HG.png"/></td>
    </tr>
</table>

#### 反馈交流

- QQ交流群：<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=90830191a40600e3a07acdcc4864890fca50c8e3ca1772e7e288a561d576f6c4"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="Quella/el-admin" title="Quella/el-admin"></a>

- 作者邮箱：elunez@qq.com
