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
     * Method name: removeDruidAdFilterRegistrationBean
     * Method description: Remove the advertisement at the bottom of the page
     * @param properties com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties
     * @return org.springframework.boot.web.servlet.FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean removeDruidAdFilterRegistrationBean(DruidStatProperties properties) {

        // Get the parameters of the web monitoring page
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
        // Extract the configuration path of common.js
        String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
        String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");

        final String filePath = "support/http/resources/js/common.js";

        // Create filter for filtering
        Filter filter = new Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {}

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                if (httpRequest.getRequestURI().endsWith("js/common.js")) {
                    // Get common.js
                    String text = Utils.readFromResource(filePath);
                    // Use regex to replace banner, remove advertisement information at the bottom
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
