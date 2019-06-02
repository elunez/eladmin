package me.zhengjie.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wuyang
 * @version 1.0.0
 * @date 2019/5/27
 */
@Configuration
public class MyWebMvcConfigurationAdapter implements WebMvcConfigurer {
    
    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.static.path}")
    private String staticPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticPath).addResourceLocations("file:" + uploadPath);
    }
}

