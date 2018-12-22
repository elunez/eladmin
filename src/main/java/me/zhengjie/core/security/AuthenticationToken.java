package me.zhengjie.core.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.Serializable;

/**
 * @author jie
 * @date 2018-11-23
 * 返回token
 */
@Getter
@AllArgsConstructor
public class AuthenticationToken implements Serializable {

    private final String token;
}
