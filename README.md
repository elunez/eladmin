[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/elunez/eladmin)

<h1 style="text-align: center">el-admin Backend</h1>
<div style="text-align: center">

[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/elunez/eladmin/blob/master/LICENSE)
[![star](https://gitee.com/elunez/eladmin/badge/star.svg?theme=white)](https://gitee.com/elunez/eladmin)
[![GitHub stars](https://img.shields.io/github/stars/elunez/eladmin.svg?style=social&label=Stars)](https://github.com/elunez/eladmin)
[![GitHub forks](https://img.shields.io/github/forks/elunez/eladmin.svg?style=social&label=Fork)](https://github.com/elunez/eladmin)

</div>

#### Project description 
eladmin-en Using , Spring Boot 2.1.0 、 Jpa、 Spring Security、redis、Vue back-end management system， Permissions using RBAC，The project supports data dictionary and data permission management, supports one-click generation of front-end and end-end code, and supports front-end menu dynamic routing.

**Development Documentation**  [https://docs.auauz.net/](https://docs.auauz.net)

**Demo**  [https://auauz.net/](https://auauz.net/)

**login details** ```admin/123456```(default password 123456)

#### Project source code

|     |   Backend source  |   Front end source  |
|---  |--- | --- |
|  github   |  https://github.com/elunez/eladmin   |  https://github.com/elunez/eladmin-qd   |
|  Cloud code   |  https://gitee.com/elunez/eladmin   |  https://gitee.com/elunez/eladmin-qt   |

####  System functions
- User Management: Provides the user's related configuration. After adding a new user, the default password is 123456.
- Role management: Assign permissions and menus, set data permissions for roles based on department
- Rights management: permissions are refined to interfaces, which can be understood as button permissions
- Menu management: Dynamic menu routing has been implemented, backend configurable, multi-level menu support
- Department management: configurable system organization structure, tree table display
- Position management: Configuring positions in various departments
- Dictionary management: It should be added to the dictionary management at the request of the majority of code friends, and can maintain some fixed data commonly used, such as: status, gender, etc.
- Operation log: log of user operations
- Exception log: record exception logs for developers to locate errors
- System Cache: Visualize caching operations with jedis and provide basic operations on redis, which can be extended as needed
- SQL monitoring: use druid to monitor database access performance, default username admin, password 123456
- Timed tasks: Integrate Quartz to do scheduled tasks, join the task log, and see the task running at a glance
- Code generation: High flexibility, one-click generation of front and rear code, reducing work tasks by 80% or so
- Mail tool: send text in html format with rich text
- Free map bed: use sm.ms map bed for public image upload
- Seven cattle cloud storage: can synchronize the data stored in the seven cattle cloud to the system, without having to log in to the seven cattle cloud to directly operate the cloud data
- Alipay payment: integrated Alipay payment and provided test account, you can test it yourself

#### Project structure
The project adopts the sub-module development method, and the general configuration is placed in the common module. 
The ```system`` module is the system core module and the project entry module. The ```logging``` module is the system log module, ``` Tools``` is a third-party tool module that contains the map bed, mail, seven cattle cloud, Alipay, ```generator``` for the system code generation module
- eladmin-common public module
     - exception handling of uniform exceptions
     - generic mapper for mapper mapstruct
     - redis redis cache related configuration
     - swagger2 interface document configuration
     - utils system generic tool class
- eladmin-system system core module (system boot entry)
     - config configure cross-domain and static resources, with data permissions
     - modules system related modules (login authorization, scheduled tasks, etc.)
- eladmin-logging system log module
- eladmin-tools system third-party tool module
- eladmin-generator system code generation module
#### System preview
<table>
    <tr>
        <td><img src="https://i.loli.net/2019/05/18/5cdf77fa8144d68788.png"/></td>
        <td><img src="https://i.loli.net/2019/05/18/5cdf7763993e361778.png"/></td>
    </tr>
    <tr>
        <td><img src="https://i.loli.net/2019/05/18/5cdf7763971d453615.png"/></td>
        <td><img src="https://i.loli.net/2019/05/18/5cdf77632e85a60423.png"/></td>
    </tr>
    <tr>
        <td><img src="https://i.loli.net/2019/05/18/5cdf77632b4b090165.png"/></td>
        <td><img src="https://i.loli.net/2019/05/18/5cdf77639929277783.png"/></td>
    </tr>
    <tr>   
 <td><img src="https://i.loli.net/2019/05/18/5cdf78969adc389599.png"/></td>
    </tr>
</table>

#### Project donation
If you are cool, you can ask the author to have a cup of coffee to express support. ☕️！ [Donate](https://docs.auauz.net/#/jz)
#### Feedback 
- QQ Chat group：891137268
