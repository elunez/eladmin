# eladmin

项目基于 Spring Boot 2.1.0 、 Spring boot Jpa、 Spring Security、redis、Vue的前后端分离的权限管理系统， 权限控制采用 RBAC 思想，支持 动态路由、项目1.0版本提供一个纯净的后台管理，第三方工具将在后面的版本中添加

#### 前端源码
- 码云：[https://gitee.com/elunez/eladmin-qt](https://gitee.com/elunez/eladmin-qt)
- github：[https://github.com/elunez/eladmin-qd](https://github.com/elunez/eladmin-qd)

#### 前端初始模板
基于PanJiaChen的：[https://github.com/PanJiaChen/vue-admin-template](https://github.com/PanJiaChen/vue-admin-template)

#### 前端参考文档
[https://panjiachen.github.io/vue-element-admin-site/zh/guide/](https://panjiachen.github.io/vue-element-admin-site/zh/guide/)

#### 预览地址
[http://auauz.net](http://auauz.net)
- 用户名： admin
- 密码： 123456

#### 系统功能模块

- 用户管理 提供用户的相关配置
- 角色管理 角色菜单分配权限
- 权限管理 权限细化到接口
- 菜单管理 已实现动态路由，后端可配置化
- 系统日志 记录用户访问监控异常信息
- 实时控制台 显示logback实时日志
- 系统缓存管理 将redis的操作可视化，提供对redis的基本操作
- Sql监控 采用 druid 监控数据库访问性能

#### 后端技术栈

- 基础框架：Spring Boot 2.1.0.RELEASE
- 持久层框架：Spring boot Jpa
- 安全框架：Spring Security
- 缓存框架：Redis
- 日志打印：logback+log4jdbc
- 接口文档 swagger2
- 其他：fastjson，aop，MapStruct等。

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
        <td><img src="https://i.loli.net/2018/12/22/5c1e10c782a05.png"/></td>
        <td><img src="https://i.loli.net/2018/12/22/5c1e10c7b089b.png"/></td>
    </tr>
    <tr>
        <td><img src="https://i.loli.net/2018/12/22/5c1e10c7b9c30.png"/></td>
        <td><img src="https://i.loli.net/2018/12/22/5c1e10c7b7504.png"/></td>
    </tr>
    <tr>
        <td><img src="https://i.loli.net/2018/12/22/5c1e10c7a9f7d.png"/></td>
    </tr>
</table>

#### 反馈交流

- QQ交流群：891137268

- 作者邮箱：zhengjie@tom.com
