package me.zhengjie.modules.security.rest;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.aop.log.Log;
import me.zhengjie.domain.VerificationCode;
import me.zhengjie.domain.vo.EmailVo;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.modules.security.security.AuthenticationInfo;
import me.zhengjie.modules.security.security.AuthorizationUser;
import me.zhengjie.modules.security.security.JwtUser;
import me.zhengjie.modules.security.utils.JwtTokenUtil;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.Job;
import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.UserDTO;
import me.zhengjie.service.EmailService;
import me.zhengjie.service.VerificationCodeService;
import me.zhengjie.utils.ElAdminConstant;
import me.zhengjie.utils.EncryptUtils;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jie
 * @date 2018-11-23
 * 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Resource
    private UserService userService;

    @Resource
    private EmailService emailService;

    @Resource
    private VerificationCodeService verificationCodeService;

    /**
     * 登录授权
     *
     * @param authorizationUser
     * @return
     */
    @Log("用户登录")
    @PostMapping(value = "${jwt.auth.path}")
    public ResponseEntity login(@Validated @RequestBody AuthorizationUser authorizationUser) {

        final JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(authorizationUser.getUsername());

        if (!jwtUser.getPassword().equals(EncryptUtils.encryptPassword(authorizationUser.getPassword()))) {
            throw new AccountExpiredException("密码错误");
        }

        if (!jwtUser.isEnabled()) {
            throw new AccountExpiredException("账号已停用，请联系管理员");
        }

        // 生成令牌
        final String token = jwtTokenUtil.generateToken(jwtUser);

        // 返回 token
        return ResponseEntity.ok(new AuthenticationInfo(token, jwtUser));
    }

    private void validateUser(String email) {
        boolean isExist = false;
        try {
            UserDTO user = userService.findByName(email);
            if (null != user) {
                isExist = true;
            }
        } catch (EntityNotFoundException e) {
            isExist = false;
        }
        if (isExist) {
            throw new BadRequestException("邮箱已被注册,请重新填写");
        }
    }

    @PostMapping(value = "/reg/sendcode")
    public ResponseEntity sendEmail(@Validated @RequestBody VerificationCode code) {
        validateUser(code.getValue());
        code.setType(ElAdminConstant.CodeType.email_reg.getIndex());
        code.setScenes(ElAdminConstant.CodeType.email_reg.getName());
        EmailVo emailVo = verificationCodeService.sendEmail(code);
        emailService.send(emailVo, emailService.find());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/reg/{code}")
    public ResponseEntity reg(@PathVariable String code, @RequestBody User user) {
        validateUser(user.getEmail());
        VerificationCode verificationCode = new VerificationCode(code, ElAdminConstant.CodeType.email_reg.getName(), ElAdminConstant.CodeType.email_reg.getIndex(), user.getEmail());
        verificationCodeService.validated(verificationCode);
        try {
            Set<Role> roles = new HashSet<>();
            Role role = new Role();
            role.setId(2L);
            roles.add(role);
            user.setRoles(roles);
            Job job = new Job();
            job.setId(11L);
            user.setJob(job);
            Dept dept = new Dept();
            dept.setId(5L);
            user.setDept(dept);
            user.setEnabled(true);
            user.setCreateTime(new Timestamp(System.currentTimeMillis()));
            user.setUsername(StringUtils.substringBefore(user.getEmail(), "@"));
            user.setAvatar(null);
            user.setPassword(EncryptUtils.encryptPassword(user.getPassword()));
            userService.create(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException("注册失败，请联系管理员");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping(value = "${jwt.auth.account}")
    public ResponseEntity getUserInfo() {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        return ResponseEntity.ok(jwtUser);
    }
}
