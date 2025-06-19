/*
 *  Copyright 2019-2025 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.modules.security.security;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.security.config.SecurityProperties;
import me.zhengjie.modules.security.service.dto.JwtUserDto;
import me.zhengjie.utils.RedisUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author /
 */
@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private Key signingKey;
    private JwtParser jwtParser;
    private final RedisUtils redisUtils;
    private final SecurityProperties properties;
    public static final String AUTHORITIES_UUID_KEY = "uid";
    public static final String AUTHORITIES_UID_KEY = "userId";

    public TokenProvider(SecurityProperties properties, RedisUtils redisUtils) {
        this.properties = properties;
        this.redisUtils = redisUtils;
    }

    @Override
    public void afterPropertiesSet() {
        // 解码Base64密钥并创建签名密钥
        byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        // 初始化 JwtParser
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(signingKey) // 使用预生成的签名密钥
                .build();
    }

    /**
     * 创建Token 设置永不过期，
     * Token 的时间有效性转到Redis 维护
     *
     * @param user /
     * @return /
     */
    public String createToken(JwtUserDto user) {
        // 设置参数
        Map<String, Object> claims = new HashMap<>(6);
        // 设置用户ID
        claims.put(AUTHORITIES_UID_KEY, user.getUser().getId());
        // 设置UUID，确保每次Token不一样
        claims.put(AUTHORITIES_UUID_KEY, IdUtil.simpleUUID());
        // 直接调用 Jwts.builder() 创建新实例
        return Jwts.builder()
                // 设置自定义 Claims
                .setClaims(claims)
                // 设置主题
                .setSubject(user.getUsername())
                // 使用预生成的签名密钥和算法签名
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 依据Token 获取鉴权信息
     *
     * @param token /
     * @return /
     */
    Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        User principal = new User(claims.getSubject(), "******", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }

    public Claims getClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * @param token 需要检查的token
     */
    public void checkRenewal(String token) {
        // 判断是否续期token,计算token的过期时间
        String loginKey = loginKey(token);
        long time = redisUtils.getExpire(loginKey) * 1000;
        Date expireDate = DateUtil.offset(new Date(), DateField.MILLISECOND, (int) time);
        // 判断当前时间与过期时间的时间差
        long differ = expireDate.getTime() - System.currentTimeMillis();
        // 如果在续期检查的范围内，则续期
        if (differ <= properties.getDetect()) {
            long renew = time + properties.getRenew();
            redisUtils.expire(loginKey, renew, TimeUnit.MILLISECONDS);
        }
    }

    public String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader(properties.getHeader());
        if (requestHeader != null && requestHeader.startsWith(properties.getTokenStartWith())) {
            return requestHeader.substring(7);
        }
        return null;
    }

    /**
     * 获取登录用户RedisKey
     * @param token /
     * @return key
     */
    public String loginKey(String token) {
        Claims claims = getClaims(token);
        return properties.getOnlineKey() + claims.getSubject() + ":" + getId(token);
    }

    /**
     * 获取登录用户TokenKey
     * @param token /
     * @return /
     */
    public String getId(String token) {
        Claims claims = getClaims(token);
        return claims.get(AUTHORITIES_UUID_KEY).toString();
    }
}
