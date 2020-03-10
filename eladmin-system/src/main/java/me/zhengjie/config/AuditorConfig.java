package me.zhengjie.config;

import me.zhengjie.utils.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @描述 : 设置审计
 * @author  : Dong ZhaoYang
 * @日期 : 2019/10/28
 * @时间 : 10:29
 */
@Component("auditorAware")
public class AuditorConfig implements AuditorAware<String> {

    /**
     * 返回操作员标志信息
     *
     * @return /
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        // 这里应根据实际业务情况获取具体信息
        return Optional.of(SecurityUtils.getCurrentUsername());
    }
}
