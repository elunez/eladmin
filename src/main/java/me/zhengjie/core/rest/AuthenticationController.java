package me.zhengjie.core.rest;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.common.aop.log.Log;
import me.zhengjie.core.security.AuthenticationToken;
import me.zhengjie.core.security.AuthorizationUser;
import me.zhengjie.core.utils.JwtTokenUtil;
import me.zhengjie.core.security.JwtUser;
import me.zhengjie.core.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

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
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    /**
     * 登录授权
     * @param authorizationUser
     * @return
     */
    @Log(description = "用户登录")
    @PostMapping(value = "${jwt.auth.path}")
    public ResponseEntity<?> authenticationLogin(@RequestBody AuthorizationUser authorizationUser){

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authorizationUser.getUsername());

        if(!userDetails.getPassword().equals(EncryptUtils.encryptPassword(authorizationUser.getPassword()))){
            throw new AccountExpiredException("密码错误");
        }

        if(!userDetails.isEnabled()){
            throw new AccountExpiredException("账号已停用，请联系管理员");
        }

        // 生成令牌
        final String token = jwtTokenUtil.generateToken(userDetails);

        // 返回 token
        return ResponseEntity.ok(new AuthenticationToken(token));
    }

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    @GetMapping(value = "${jwt.auth.account}")
    public ResponseEntity getUserInfo(HttpServletRequest request){
        JwtUser jwtUser = (JwtUser)userDetailsService.loadUserByUsername(jwtTokenUtil.getUserName(request));
        return ResponseEntity.ok(jwtUser);
    }
}
