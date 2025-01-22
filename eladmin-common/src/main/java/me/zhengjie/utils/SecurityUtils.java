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
package me.zhengjie.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.utils.enums.DataScopeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * 获取当前登录的用户
 * @author Zheng Jie
 * @date 2019-01-17
 */
@Slf4j
@Component
public class SecurityUtils {

    public static String header;

    public static String tokenStartWith;

    @Value("${jwt.header}")
    public void setHeader(String header) {
        SecurityUtils.header = header;
    }

    @Value("${jwt.token-start-with}")
    public void setTokenStartWith(String tokenStartWith) {
        SecurityUtils.tokenStartWith = tokenStartWith;
    }

    /**
     * 获取当前登录的用户
     * @return UserDetails
     */
    public static UserDetails getCurrentUser() {
        UserDetailsService userDetailsService = SpringBeanHolder.getBean(UserDetailsService.class);
        return userDetailsService.loadUserByUsername(getCurrentUsername());
    }

    /**
     * 获取当前用户的数据权限
     * @return /
     */
    public static List<Long> getCurrentUserDataScope(){
        UserDetails userDetails = getCurrentUser();
        // 将 Java 对象转换为 JSONObject 对象
        JSONObject jsonObject = (JSONObject) JSON.toJSON(userDetails);
        JSONArray jsonArray = jsonObject.getJSONArray("dataScopes");
        return JSON.parseArray(jsonArray.toJSONString(), Long.class);
    }

    /**
     * 获取数据权限级别
     * @return 级别
     */
    public static String getDataScopeType() {
        List<Long> dataScopes = getCurrentUserDataScope();
        if(CollUtil.isEmpty(dataScopes)){
            return "";
        }
        return DataScopeEnum.ALL.getValue();
    }

    /**
     * 获取用户ID
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        return getCurrentUserId(getToken());
    }

    /**
     * 获取用户ID
     * @return 系统用户ID
     */
    public static Long getCurrentUserId(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        return Long.valueOf(jwt.getPayload("userId").toString());
    }

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getCurrentUsername() {
        return getCurrentUsername(getToken());
    }

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getCurrentUsername(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        return jwt.getPayload("sub").toString();
    }

    /**
     * 获取Token
     * @return /
     */
    public static String getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest();
        String bearerToken = request.getHeader(header);
        if (bearerToken != null && bearerToken.startsWith(tokenStartWith)) {
            // 去掉令牌前缀
            return bearerToken.replace(tokenStartWith, "");
        } else {
            log.debug("非法Token：{}", bearerToken);
        }
        return null;
    }
}
