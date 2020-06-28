package me.zhengjie.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("me.zhengjie.repository.mp")
public class MybatisPlusConfig {

}
