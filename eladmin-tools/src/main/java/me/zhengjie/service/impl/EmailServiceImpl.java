package me.zhengjie.service.impl;

import me.zhengjie.domain.EmailConfig;
import me.zhengjie.domain.vo.EmailVo;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.repository.EmailRepository;
import me.zhengjie.service.EmailService;
import me.zhengjie.utils.EmailUtils;
import me.zhengjie.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author jie
 * @date 2018-12-26
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailRepository emailRepository;
    @Resource
    EmailUtils emailUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmailConfig update(EmailConfig emailConfig, EmailConfig old) {
        try {
            if (!emailConfig.getPass().equals(old.getPass())) {
                // 对称加密
                emailConfig.setPass(EncryptUtils.desEncrypt(emailConfig.getPass()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        emailConfig.setId(1L);
        return emailRepository.save(emailConfig);
    }

    @Override
    public EmailConfig find() {
        Optional<EmailConfig> emailConfig = emailRepository.findById(1L);
        if (emailConfig.isPresent()) {
            return emailConfig.get();
        } else {
            return new EmailConfig();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(EmailVo emailVo, EmailConfig emailConfig) {
        if (emailConfig == null) {
            throw new BadRequestException("请先配置，再操作");
        }
        /**
         * 封装
         */
//        MailAccount account = new MailAccount();
//        account.setHost(emailConfig.getHost());
//        account.setPort(Integer.parseInt(emailConfig.getPort()));
//        account.setAuth(true);
//        try {
//            // 对称解密
//            account.setPass(EncryptUtils.desDecrypt(emailConfig.getPass()));
//        } catch (Exception e) {
//            throw new BadRequestException(e.getMessage());
//        }
//        account.setFrom(emailConfig.getUser() + "<" + emailConfig.getFromUser() + ">");
//        //ssl方式发送
//        account.setStartttlsEnable(true);
        String content = emailVo.getContent();
        /**
         * 发送
         */
        try {
            emailUtils.sendTemplateMail(emailConfig, emailVo.getTos().get(0), emailVo.getSubject(), content);
//            Mail.create(account)
//                    .setTos(emailVo.getTos().toArray(new String[emailVo.getTos().size()]))
//                    .setTitle(emailVo.getSubject())
//                    .setContent(content)
//                    .setHtml(true)
//                    .setUseGlobalSession(false)//关闭session
//                    .send();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
