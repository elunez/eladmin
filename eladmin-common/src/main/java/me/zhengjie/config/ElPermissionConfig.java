package me.zhengjie.config;

import me.zhengjie.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 */
@Service(value = "el")
public class ElPermissionConfig {

    public Boolean check(String ...permissions){
        // 获取当前用户的所有权限
        List<String> elPermissions = SecurityUtils.getUserDetails().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        //List<String> elPermissions = SecurityUtils.getUserDetails().getAuthorities().stream().map((Function<GrantedAuthority, String>) GrantedAuthority::getAuthority).collect(Collectors.toList());
//        List<String> elPermissions = SecurityUtils.getUserDetails().getAuthorities().stream().map(new Function<GrantedAuthority,String>() {
//            @Override
//            public String apply(GrantedAuthority o) {
//                return o.getAuthority();
//            }
//        }).collect(Collectors.toList());
//简化为map((Function<GrantedAuthority, String>) o -> o.getAuthority())，然后简化为(Function<GrantedAuthority, String>) GrantedAuthority::getAuthority
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return elPermissions.contains("admin") || Arrays.stream(permissions).anyMatch(elPermissions::contains);
//        return elPermissions.contains("admin") || Arrays.stream(permissions).anyMatch( new Predicate<String>(){
//
//            @Override
//            public boolean test(String o) {
//                return elPermissions.contains(o);
//            }
//        });
//简化为anyMatch(o -> elPermissions.contains(o)) 然后再次简化为elPermissions::contains
    }
}
