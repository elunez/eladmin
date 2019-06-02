package me.zhengjie.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @author https://blog.csdn.net/llibin1024530411/article/details/79474953
 * @date 2018-12-28
 */
@Configuration
public class MultipartConfig {

    /**
     * 文件上传临时路径
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("1MB"));
        factory.setMaxRequestSize(DataSize.parse("10MB"));
//        String location = System.getProperty("user.home") + "/.eladmin/file/tmp";
//        File tmpFile = new File(location);
//        if (!tmpFile.exists()) {
//            tmpFile.mkdirs();
//        }
//        factory.setLocation(location);
        return factory.createMultipartConfig();
    }
}