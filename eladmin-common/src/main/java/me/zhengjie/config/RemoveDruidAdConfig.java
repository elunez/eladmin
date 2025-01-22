package me.zhengjie.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.util.Utils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Zheng Jie
 * @description
 * @date 2025-01-11
 **/
@Configuration
@SuppressWarnings({"unchecked","all"})
@ConditionalOnWebApplication
@AutoConfigureAfter(DruidDataSourceAutoConfigure.class)
@ConditionalOnProperty(name = "spring.datasource.druid.stat-view-servlet.enabled",
        havingValue = "true", matchIfMissing = true)
public class RemoveDruidAdConfig {

    /**
     * 方法名: removeDruidAdFilterRegistrationBean
     * 方法描述 除去页面底部的广告
     * @param properties com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties
     * @return org.springframework.boot.web.servlet.FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean removeDruidAdFilterRegistrationBean(DruidStatProperties properties) {

        // 获取web监控页面的参数
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
        // 提取common.js的配置路径
        String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
        String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");

        final String filePath = "support/http/resources/js/common.js";

        //创建filter进行过滤
        Filter filter = new Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {}

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                if (httpRequest.getRequestURI().endsWith("js/common.js")) {
                    // 获取common.js
                    String text = Utils.readFromResource(filePath);
                    // 正则替换banner, 除去底部的广告信息
                    text = text.replaceAll("<a.*?druid_banner\"></a><br/>", "");
                    text = text.replaceAll("powered by.*?shrek.wang</a>", "");
                    httpResponse.setContentType("application/javascript");
                    httpResponse.setCharacterEncoding("UTF-8");
                    httpResponse.getWriter().write(text);
                } else {
                    chain.doFilter(request, response);
                }
            }
            @Override
            public void destroy() {}
        };
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns(commonJsPattern);
        return registrationBean;
    }
}
