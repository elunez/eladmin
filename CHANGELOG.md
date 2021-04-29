# EL-ADMIN 2.7

- 升级 JDK 11。
- 升级 Spring boot 2.4.4 。
- 升级 Swagger 3.0.0 。
- 升级 commons-pool2 2.9.0。
- 升级 mapstruct 1.4.2.Final 。
- 升级 druid 1.2.6 。
- 升级 fastjson 1.2.76 。
- 优化数据库脚本。
- 替换容器，tomcat -> undertow 。

## 升级过程中遇到的问题及解决方法

### 执行数据库脚本报异常

MySQL 中 int，bigint 等类型不需要写长度数字。将 int(8) 这类内容改为 int。

### 跨域配置问题

> When allowCredentials is true, allowedOrigins cannot contain the special value "*“since that cannot be set on the “Access-Control-Allow-Origin” response header. To allow credentials to a set of origins, list them explicitly or consider using"allowedOriginPatterns” instead.

修改如下代码：

```java
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
      // 增加这一行代码
        config.addAllowedOriginPattern("*");
      // 注释掉这一行代码
//        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
```

