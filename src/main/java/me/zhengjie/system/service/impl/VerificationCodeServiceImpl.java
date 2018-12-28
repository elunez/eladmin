package me.zhengjie.system.service.impl;

import me.zhengjie.system.domain.VerificationCode;
import me.zhengjie.system.service.VerificationCodeService;
import org.springframework.stereotype.Service;

/**
 * @author jie
 * @date 2018-12-26
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Override
    public void sendEmail(VerificationCode code) {

    }

    @Override
    public void validated(VerificationCode code) {

    }
}
