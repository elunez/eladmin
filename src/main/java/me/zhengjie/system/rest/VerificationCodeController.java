package me.zhengjie.system.rest;

import me.zhengjie.common.utils.ElAdminConstant;
import me.zhengjie.common.utils.RequestHolder;
import me.zhengjie.core.security.JwtUser;
import me.zhengjie.core.utils.JwtTokenUtil;
import me.zhengjie.system.domain.VerificationCode;
import me.zhengjie.system.service.VerificationCodeService;
import me.zhengjie.tools.domain.vo.EmailVo;
import me.zhengjie.tools.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * @author jie
 * @date 2018-12-26
 */
@RestController
@RequestMapping("api")
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/code/resetEmail")
    public ResponseEntity resetEmail(@RequestBody VerificationCode code) throws Exception {
        code.setScenes(ElAdminConstant.RESET_MAIL);
        EmailVo emailVo = verificationCodeService.sendEmail(code);
        emailService.send(emailVo,emailService.find());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/code/email/resetPass")
    public ResponseEntity resetPass() throws Exception {
        JwtUser jwtUser = (JwtUser)userDetailsService.loadUserByUsername(jwtTokenUtil.getUserName(RequestHolder.getHttpServletRequest()));
        VerificationCode code = new VerificationCode();
        code.setType("email");
        code.setValue(jwtUser.getEmail());
        code.setScenes(ElAdminConstant.RESET_MAIL);
        EmailVo emailVo = verificationCodeService.sendEmail(code);
        emailService.send(emailVo,emailService.find());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/code/validated")
    public ResponseEntity validated(VerificationCode code){
        verificationCodeService.validated(code);
        return new ResponseEntity(HttpStatus.OK);
    }
}
