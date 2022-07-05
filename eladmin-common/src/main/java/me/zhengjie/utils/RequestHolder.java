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
import cn.hutool.core.convert.Convert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Set;

/**
 * 获取 HttpServletRequest
 * @author Zheng Jie
 * @date 2018-11-24
 */
public class RequestHolder {

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }


    /**
     * <p>
     * 获取头部信息
     * </p>
     *
     * @param headerName 头部名称
     * @return /
     */
    public static String getHeader(String headerName) {
        return getHttpServletRequest().getHeader(headerName);
    }

    /**
     * <p>
     * 获取头部信息
     * </p>
     *
     * @param headerName 头部名称
     * @return /
     */
    public static Long getHeaderLong(String headerName) {
        return Convert.toLong(getHeader(headerName));
    }

    /**
     * <p>
     * 获取头部集合
     * </p>
     *
     * @param headerName 头部名称
     * @return /
     */
    public static Set<String> getHeaders(String headerName) {
        Enumeration<String> values = getHttpServletRequest().getHeaders(headerName);
        return CollUtil.newHashSet(true, values);
    }
}
