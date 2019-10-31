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
        List<String> elPermissions = SecurityUtils.getUserDetails().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        List<String> list = Arrays.stream(permissions).filter(elPermissions::contains).collect(Collectors.toList());
        return elPermissions.contains("admin") || list.size() != 0;
    }
}
