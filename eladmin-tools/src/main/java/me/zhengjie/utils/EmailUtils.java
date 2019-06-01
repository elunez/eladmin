package me.zhengjie.utils;

import me.zhengjie.domain.EmailConfig;
import me.zhengjie.utils.thread.LocalExecutorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XPay
 */
@Component
public class EmailUtils {

    private static final Logger log = LoggerFactory.getLogger(EmailUtils.class);

    //    @Resource
//    private JavaMailSender mailSender;
    @Resource
    private JavaMailSenderImpl mailSender;

    @Resource
    private TemplateEngine templateEngine;

    /**
     * 发送模版邮件
     *
     * @param sender
     * @param sendto
     * @param templateName
     * @param o
     */
    public void sendTemplateMail(String sender, String sendto, String title, String templateName, Object o) {

        log.info("开始给" + sendto + "发送邮件");
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message html内容
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(sendto);
            helper.setSubject(title);

            Context context = new Context();
            context.setVariable("title", title);
            context.setVariables(StringUtils.beanToMap(o));
            //获取模板html代码
            String content = templateEngine.process(templateName, context);

            helper.setText(content, true);

            mailSender.send(message);
            log.info("给" + sendto + "发送邮件成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            //e.printStackTrace();
        }
    }

    public void sendTemplateMail(String sender, String sendto, String title, String content) {

        log.info("开始给" + sendto + "发送邮件");
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message html内容
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(sendto);
            helper.setSubject(title);
            helper.setText(content, true);

            mailSender.send(message);
            log.info("给" + sendto + "发送邮件成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            //e.printStackTrace();
        }
    }

    public void sendTemplateMail(EmailConfig emailConfig, String sendto, String title, String content) {
        log.info("开始给" + sendto + "发送邮件");
        ExecutorService executorService = LocalExecutorManager.getExecutorService();
        executorService.execute(() -> {
            try {
                mailSender.setHost(emailConfig.getHost());
                mailSender.setPort(Integer.parseInt(emailConfig.getPort()));
                mailSender.setUsername(emailConfig.getFromUser());
                mailSender.setPassword(EncryptUtils.desDecrypt(emailConfig.getPass()));

                //true表示需要创建一个multipart message html内容
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(emailConfig.getUser() + "<" + emailConfig.getFromUser() + ">");
                helper.setTo(sendto);
                helper.setSubject(title);
                helper.setText(content, true);

                mailSender.send(message);
                log.info("给" + sendto + "发送邮件成功");

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                //e.printStackTrace();
            }
        });

    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
