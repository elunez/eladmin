package me.zhengjie.modules.system.service.strategy;

import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.utils.enums.DataScopeEnum;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class ThisLevelDataScopeStrategy implements DataScopeStrategy {
    
    @Override
    public String getScopeType() {
        return DataScopeEnum.THIS_LEVEL.getValue();
    }
    
    @Override
    public Set<Long> getDeptIds(UserDto user, RoleSmallDto role) {
        Set<Long> deptIds = new HashSet<>();
        if (user.getDept() != null) {
            deptIds.add(user.getDept().getId());
        }
        return deptIds;
    }
}
