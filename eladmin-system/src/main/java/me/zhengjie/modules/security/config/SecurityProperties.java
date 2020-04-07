package me.zhengjie.modules.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;

/**
 * Jwt参数配置
 * @author Zheng Jie
 * @date 2019年11月28日
 * ConfigurationProperties可以将外部配置文件（比如applicaition.properties）加载进来，填充对象的对应字段的数据，然后供其他Bean使用
 * Configuration配置类注解，被自动扫描发现,不然这个类无法被Spring容器管理,会导致ConfigurationProperties失效
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class SecurityProperties {

    /** Request Headers ： Authorization */
    private String header;

    /** 令牌前缀，最后留个空格 Bearer */
    private String tokenStartWith;

    /** 必须使用最少88位的Base64对该令牌进行编码 */
    private String base64Secret;

    /** 令牌过期时间 此处单位/毫秒 */
    private Long tokenValidityInSeconds;

    /** 在线用户 key，根据 key 查询 redis 中在线用户的数据 */
    private String onlineKey;

    /** 验证码 key */
    private String codeKey;

    public String getTokenStartWith() {
        return tokenStartWith + " ";
    }
}
