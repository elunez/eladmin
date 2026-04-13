package me.zhengjie.modules.system.service.strategy;

import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import me.zhengjie.modules.system.service.dto.UserDto;
import java.util.Set;

public interface DataScopeStrategy {
    
    String getScopeType();
    
    Set<Long> getDeptIds(UserDto user, RoleSmallDto role);
}
