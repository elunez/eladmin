package me.zhengjie.modules.system.service.datascope;

import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import me.zhengjie.modules.system.service.dto.UserDto;

import java.util.Set;

public interface DataScopeStrategy {

    void apply(Set<Long> deptIds, UserDto user, RoleSmallDto role);
}
