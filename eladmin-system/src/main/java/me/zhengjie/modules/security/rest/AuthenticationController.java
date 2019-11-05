package me.zhengjie.modules.security.rest;

import cn.hutool.core.util.IdUtil;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.monitor.service.RedisService;
import me.zhengjie.modules.security.security.AuthInfo;
import me.zhengjie.modules.security.security.AuthUser;
import me.zhengjie.modules.security.security.ImgResult;
import me.zhengjie.modules.security.security.JwtUser;
import me.zhengjie.modules.security.service.OnlineUserService;
import me.zhengjie.utils.EncryptUtils;
import me.zhengjie.modules.security.utils.JwtTokenUtil;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 * 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@Api(tags = "系统：系统授权接口")
public class AuthenticationController {

    @Value("${jwt.codeKey}")
    private String codeKey;

    private final JwtTokenUtil jwtTokenUtil;

    private final RedisService redisService;

    private final UserDetailsService userDetailsService;

    private final OnlineUserService onlineUserService;

    public AuthenticationController(JwtTokenUtil jwtTokenUtil, RedisService redisService, @Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService, OnlineUserService onlineUserService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.redisService = redisService;
        this.userDetailsService = userDetailsService;
        this.onlineUserService = onlineUserService;
    }

    @Log("用户登录")
    @ApiOperation("登录授权")
    @AnonymousAccess
    @PostMapping(value = "/login")
    public ResponseEntity login(@Validated @RequestBody AuthUser authUser, HttpServletRequest request){

        // 查询验证码
        String code = redisService.getCodeVal(authUser.getUuid());
        // 清除验证码
        redisService.delete(authUser.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("验证码已过期");
        }
        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误");
        }
        final JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(authUser.getUsername());

        if(!jwtUser.getPassword().equals(EncryptUtils.encryptPassword(authUser.getPassword()))){
            throw new AccountExpiredException("密码错误");
        }

        if(!jwtUser.isEnabled()){
            throw new AccountExpiredException("账号已停用，请联系管理员");
        }
        // 生成令牌
        final String token = jwtTokenUtil.generateToken(jwtUser);
        // 保存在线信息
        onlineUserService.save(jwtUser, token, request);
        // 返回 token
        return ResponseEntity.ok(new AuthInfo(token,jwtUser));
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public ResponseEntity getUserInfo(){
        JwtUser jwtUser = (JwtUser)userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        return ResponseEntity.ok(jwtUser);
    }

    @ApiOperation("获取验证码")
    @AnonymousAccess
    @GetMapping(value = "/code")
    public ImgResult getCode(){
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果：5
        String result = captcha.text();
        String uuid = codeKey + IdUtil.simpleUUID();
        redisService.saveCode(uuid,result);
        return new ImgResult(captcha.toBase64(),uuid);
    }

    @ApiOperation("退出登录")
    @AnonymousAccess
    @DeleteMapping(value = "/logout")
    public ResponseEntity logout(HttpServletRequest request){
        onlineUserService.logout(jwtTokenUtil.getToken(request));
        return new ResponseEntity(HttpStatus.OK);
    }
}
