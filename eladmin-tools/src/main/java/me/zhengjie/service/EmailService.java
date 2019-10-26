package me.zhengjie.service;

import me.zhengjie.domain.EmailConfig;
import me.zhengjie.domain.vo.EmailVo;
import org.springframework.scheduling.annotation.Async;

/**
 * @author Zheng Jie
 * @date 2018-12-26
 */
public interface EmailService {

    /**
     * 更新邮件配置
     * @param emailConfig 邮件配置
     * @param old 旧的配置
     * @return EmailConfig
     */
    EmailConfig update(EmailConfig emailConfig, EmailConfig old);

    /**
     * 查询配置
     * @return EmailConfig 邮件配置
     */
    EmailConfig find();

    /**
     * 发送邮件
     * @param emailVo 邮件发送的内容
     * @param emailConfig 邮件配置
     * @throws Exception /
     */
    @Async
    void send(EmailVo emailVo, EmailConfig emailConfig) throws Exception;
}
