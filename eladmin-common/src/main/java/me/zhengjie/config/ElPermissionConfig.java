package me.zhengjie.config;

import me.zhengjie.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "el")
public class ElPermissionConfig {

    public Boolean check(String ...permissions){
        // 如果是匿名访问的，就放行
        String anonymous = "anonymous";
        if(Arrays.asList(permissions).contains(anonymous)){
            return true;
        }
        // 获取当前用户的所有权限
        List<String> elPermissions = SecurityUtils.getUserDetails().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // admin 直接放行
        if (elPermissions.contains("admin")) {
            return true;
        }
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return  Arrays.stream(permissions).filter(elPermissions::contains).collect(Collectors.toList()).size() > 0;
    }
}
