package me.zhengjie.modules.system.service.datascope;

import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import me.zhengjie.modules.system.service.dto.UserDto;

import java.util.Set;

public class ThisLevelStrategy implements DataScopeStrategy {

    @Override
    public void apply(Set<Long> deptIds, UserDto user, RoleSmallDto role) {
        deptIds.add(user.getDept().getId());
    }
}
