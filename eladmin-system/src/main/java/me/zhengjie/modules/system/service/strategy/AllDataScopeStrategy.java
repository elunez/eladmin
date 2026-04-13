package me.zhengjie.modules.system.service.strategy;

import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.utils.enums.DataScopeEnum;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class AllDataScopeStrategy implements DataScopeStrategy {
    
    @Override
    public String getScopeType() {
        return DataScopeEnum.ALL.getValue();
    }
    
    @Override
    public Set<Long> getDeptIds(UserDto user, RoleSmallDto role) {
        return new HashSet<>();
    }
}
