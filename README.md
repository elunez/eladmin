# ELADMIN Backend Management System

## Project Introduction

A front-end and back-end separated management system based on Spring Boot 2.7.18, Spring Boot JPA, JWT, Spring Security, Redis, and Vue.

A MyBatis-Plus version has also been released:
- [GitHub](https://github.com/elunez/eladmin-mp)
- [Gitee](https://gitee.com/elunez/eladmin-mp)

**Documentation:** [https://eladmin.vip](https://eladmin.vip)

**Demo:** [https://eladmin.vip/demo](https://eladmin.vip/demo)

**Demo Account:** `admin / 123456`

## Source Code

|        | Backend Source | Frontend Source |
|--------|---------------|----------------|
| GitHub | https://github.com/elunez/eladmin | https://github.com/elunez/eladmin-web |
| Gitee  | https://gitee.com/elunez/eladmin  | https://gitee.com/elunez/eladmin-web  |

## Main Features

- Uses the latest tech stack with rich community resources.
- High development efficiency with code generator for one-click front-end and back-end code generation.
- Supports data dictionaries for easy status management.
- API rate limiting to prevent service overload from malicious requests.
- Supports function-level and data-level permissions, with customizable operations.
- Custom permission and anonymous API annotations for quick API interception and release.
- Encapsulates common front-end components: table data requests, data dictionaries, etc.
- Unified exception handling for both front and back ends, avoiding repetitive checks.
- Online user management and server performance monitoring, with single-user login restriction.
- Operations management for easy deployment and management of remote server applications.

## System Functions

- User Management: Configure users; new users default to password `123456`.
- Role Management: Assign permissions and menus, set data permissions by department.
- Menu Management: Dynamic routing, back-end configurable, supports multi-level menus.
- Department Management: Configure system organization structure, tree-shaped table display.
- Position Management: Configure positions for each department.
- Dictionary Management: Maintain common fixed data, such as status and gender.
- System Log: Record user operation logs and exception logs for easy error tracking.
- SQL Monitoring: Use Druid to monitor database access performance, default username `admin`, password `123456`.
- Scheduled Tasks: Integrate Quartz for scheduled tasks, with task logs and task execution status.
- Code Generation: High flexibility code generation for front-end and back-end, reducing repetitive work.
- Email Tool: Send HTML format emails with rich text.
- AWS Cloud Storage: Synchronize AWS cloud storage data to the system, no need to log in to AWS cloud to operate cloud data.
- Alipay Payment: Integrate Alipay payment and provide a test account for self-testing.
- Server Monitoring: Monitor server load status.
- Operations Management: One-click deployment of your application.

## Project Structure

The project uses a modular development approach, with the following structure:

- `eladmin-common`: System public module, including various utility classes and public configurations.
- `eladmin-system`: System core module and project entry module, also the final module to be packaged and deployed.
- `eladmin-logging`: System log module, other modules need to import this module to record logs.
- `eladmin-tools`: Third-party tool module, including email, AWS cloud storage, local storage
- `eladmin-generator`: System code generation module, supporting front-end and back-end CRUD code generation.

## Detailed Structure

```
- eladmin-common Public Module
    - annotation System custom annotations
    - aspect Custom annotation aspects
    - base Entity, DTO base classes, and MapStruct common mapper
    - config Project public configurations
        - Web configuration: cross-domain, static resource mapping, Swagger configuration, file upload temporary path configuration
        - Redis configuration, Redission configuration, asynchronous thread pool configuration
        - Permission interception configuration, AuthorityConfig, Druid delete advertisement configuration
    - exception Project unified exception handling
    - utils System utility classes, including:
        - BigDecimaUtils Amount calculation utility class
        - RequestHolder Request utility class
        - SecurityUtils Security utility class
        - StringUtils String utility class
        - SpringBeanHolder Spring Bean utility class
        - RedisUtils Redis utility class
        - EncryptUtils Encryption utility class
        - FileUtil File utility class
- eladmin-system System Core Module (Project Entry Module)
    - sysrunner Program startup data processing
    - modules System-related modules (login authorization, system monitoring, scheduled tasks, system modules, operations management)
- eladmin-logging System Log Module
- eladmin-tools Third-party Tool Module
    - email Email tool
    - AWS S3 cloud storage tool
    - alipay Alipay payment tool
    - local-storage Local storage tool
- eladmin-generator System Code Generation Module
```

## Special Thanks

- Thanks to [PanJiaChen](https://github.com/PanJiaChen/vue-element-admin) for providing the front-end template.
- Thanks to [Moxun](https://github.com/moxun1639) for providing the front-end Curd common components.
- Thanks to [zhy6599](https://gitee.com/zhy6599) for providing the back-end operations management related functions.
- Thanks to [j.yao.SUSE](https://github.com/everhopingandwaiting) for providing the anonymous API and Redis rate limiting functions.
- Thanks to [d15801543974](https://github.com/d15801543974) for providing the annotation-based common query method.

## Project Donation

The project's development relies on your support, please buy the author a cup of coffee ☕ [Donate](https://eladmin.vip/pages/030101/)

## Feedback and Discussion

- QQ discussion group: 891137268, 947578238, 659622532