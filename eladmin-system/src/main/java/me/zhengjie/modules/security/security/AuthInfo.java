package me.zhengjie.modules.security.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.Serializable;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 * 返回token
 */
@Getter
@AllArgsConstructor
public class AuthInfo implements Serializable {

    private final String token;

    private final JwtUser user;
}
