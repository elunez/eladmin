package me.zhengjie.system.service.impl;

import cn.hutool.core.util.RandomUtil;
import me.zhengjie.common.exception.BadRequestException;
import me.zhengjie.common.utils.ElAdminConstant;
import me.zhengjie.system.domain.VerificationCode;
import me.zhengjie.system.repository.VerificationCodeRepository;
import me.zhengjie.system.service.VerificationCodeService;
import me.zhengjie.tools.domain.vo.EmailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author jie
 * @date 2018-12-26
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Value("${code.expiration}")
    private Integer expiration;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmailVo sendEmail(VerificationCode code) {
        EmailVo emailVo = null;
        String content = "";
        VerificationCode verificationCode = verificationCodeRepository.findByScenesAndTypeAndValueAndStatusIsTrue(code.getScenes(),code.getType(),code.getValue());
        // 如果不存在有效的验证码，就创建一个新的
        if(verificationCode == null){
            code.setCode(RandomUtil.randomNumbers (6));
            content = ElAdminConstant.EMAIL_CODE + code.getCode() + "</p>";
            emailVo = new EmailVo(Arrays.asList(code.getValue()),"eladmin后台管理系统",content);
            timedDestruction(verificationCodeRepository.save(code));
        // 存在就再次发送原来的验证码
        } else {
            content = ElAdminConstant.EMAIL_CODE + verificationCode.getCode() + "</p>";
            emailVo = new EmailVo(Arrays.asList(verificationCode.getValue()),"eladmin后台管理系统",content);
        }
        return emailVo;
    }

    @Override
    public void validated(VerificationCode code) {
        VerificationCode verificationCode = verificationCodeRepository.findByScenesAndTypeAndValueAndStatusIsTrue(code.getScenes(),code.getType(),code.getValue());
        if(verificationCode == null || !verificationCode.getCode().equals(code.getCode())){
            throw new BadRequestException("无效验证码");
        } else {
            verificationCode.setStatus(false);
            verificationCodeRepository.save(verificationCode);
        }
    }

    /**
     * 定时任务，指定分钟后改变验证码状态
     * @param verifyCode
     */
    private void timedDestruction(VerificationCode verifyCode) {
        //以下示例为程序调用结束继续运行
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        try {
            executorService.schedule(() -> {
                verifyCode.setStatus(false);
                verificationCodeRepository.save(verifyCode);
            }, expiration * 60 * 1000L, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
