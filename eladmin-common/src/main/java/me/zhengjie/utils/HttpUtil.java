package me.zhengjie.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import me.zhengjie.constant.SecurityConstant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * http
 * </p>
 *
 * @author miaoyj
 * @since 2022-07-05
 */
public class HttpUtil {

    /**
     * <p>
     * 设置header的用户信息
     * </p>
     *
     * @param userDetails /
     * @return /
     */
    public static Map<String, Object> getUserHeaders(UserDetails userDetails) {
        Map<String, Object> httpHeaders = new HashMap<>();
        if (userDetails != null) {
            //用户基本信息
            String userId = new JSONObject(new JSONObject(userDetails).get("user")).get("id", String.class);
            String username = userDetails.getUsername();
            List<String> elPermissions = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            //部门id
            JSONArray array = JSONUtil.parseArray(new JSONObject(userDetails).get("dataScopes"));
            List<Long> dataScopeDeptIds = JSONUtil.toList(array,Long.class);
            List<String> dataScopeDeptIdsList = new ArrayList<>();
            if (CollUtil.isNotEmpty(dataScopeDeptIds)) {
                dataScopeDeptIdsList = dataScopeDeptIds.stream().map(o -> String.valueOf(o)).collect(Collectors.toList());
            }
            httpHeaders.put(SecurityConstant.JWT_KEY_USER_ID, userId);
            httpHeaders.put(SecurityConstant.JWT_KEY_USERNAME, username);
            httpHeaders.put(SecurityConstant.JWT_KEY_PERMISSION, elPermissions);
            httpHeaders.put(SecurityConstant.JWT_KEY_DATA_SCOPE_DEPT_IDS, dataScopeDeptIdsList);
        }
        return httpHeaders;
    }
}
