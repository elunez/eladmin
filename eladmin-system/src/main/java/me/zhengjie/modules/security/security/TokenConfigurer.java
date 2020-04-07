package me.zhengjie.modules.security.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author /
 */
public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    public TokenConfigurer(TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) {
        TokenFilter customFilter = new TokenFilter(tokenProvider);
        /**
         * 在验证用户名和密码之前，添加一个自定义的filter,用来提供token的验证
         * 每次访问方法的时候,先验证token是否有效,token有效再放开filter链,判断对应用户是否有权限访问受保护的资源
         */
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
