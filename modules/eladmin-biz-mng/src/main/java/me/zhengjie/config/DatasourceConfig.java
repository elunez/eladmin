package me.zhengjie.config;

import com.alipay.sofa.serverless.common.api.SpringBeanFinder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.activation.DataSource;

@Configuration
public class DatasourceConfig {

    @Bean(name = "dataSource")
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        return (DataSource) SpringBeanFinder.getBaseBean("dataSource");
    }
}
