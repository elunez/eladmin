/*
 *  Copyright 2019-2020 Zheng Jie
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
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.constant.SecurityConstant;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.utils.enums.DataScopeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 获取当前登录的用户
 *
 * @author Zheng Jie
 * @date 2019-01-17
 */
@Slf4j
public class SecurityUtils {

    /**
     * 获取当前登录的用户
     *
     * @return UserDetails
     */
    public static UserDetails getCurrentUser() {
        UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetailsService.loadUserByUsername(userDetails.getUsername());
    }

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getCurrentUsername() {
        return getHeaderByAuthException(SecurityConstant.JWT_KEY_USERNAME);
    }

    /**
     * 获取系统用户ID
     *
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        return getLongHeaderByAuthException(SecurityConstant.JWT_KEY_USER_ID);
    }

    /**
     * 获取当前用户的数据权限
     *
     * @return /
     */
    public static List<Long> getCurrentUserDataScope() {
        Set<String> deptStrIds = RequestHolder.getHeaders(SecurityConstant.JWT_KEY_DATA_SCOPE_DEPT_IDS);
        List<Long> deptIds = new ArrayList<>();
        if (CollUtil.isNotEmpty(deptStrIds)) {
            deptIds = deptStrIds.stream().map(o -> Long.parseLong(o)).collect(Collectors.toList());
        }
        return deptIds;
    }

    /**
     * 获取数据权限级别
     *
     * @return 级别
     */
    public static String getDataScopeType() {
        List<Long> dataScopes = getCurrentUserDataScope();
        if (dataScopes.size() != 0) {
            return "";
        }
        return DataScopeEnum.ALL.getValue();
    }

    /**
     * <p>
     * 获取权限列表
     * </p>
     *
     * @return /
     */
    public static Set<String> listPermission() {
        return RequestHolder.getHeaders(SecurityConstant.JWT_KEY_PERMISSION);
    }

    /**
     * <p>
     * 获取header，验证用户信息抛出异常
     * </p>
     *
     * @author miaoyj
     * @since 2022-06-23
     */
    private static String getHeaderByAuthException(String headerName) {
        String value = RequestHolder.getHeader(headerName);
        if (StrUtil.isNotBlank(value)) {
            return value;
        }
        throw new BadRequestException(HttpStatus.UNAUTHORIZED, "找不到当前登录的信息");
    }

    /**
     * <p>
     * 获取header，验证用户信息抛出异常
     * </p>
     *
     * @author miaoyj
     * @since 2022-06-23
     */
    private static Long getLongHeaderByAuthException(String headerName) {
        Long value = RequestHolder.getHeaderLong(headerName);
        if (value != null && value > 0) {
            return value;
        }
        throw new BadRequestException(HttpStatus.UNAUTHORIZED, "找不到当前登录的信息");
    }
}
