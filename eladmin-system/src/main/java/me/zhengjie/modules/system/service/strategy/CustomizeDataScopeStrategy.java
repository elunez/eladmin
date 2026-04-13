package me.zhengjie.modules.system.service.strategy;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.service.DeptService;
import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.utils.enums.DataScopeEnum;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomizeDataScopeStrategy implements DataScopeStrategy {
    
    private final DeptService deptService;
    
    @Override
    public String getScopeType() {
        return DataScopeEnum.CUSTOMIZE.getValue();
    }
    
    @Override
    public Set<Long> getDeptIds(UserDto user, RoleSmallDto role) {
        Set<Long> deptIds = new HashSet<>();
        Set<Dept> depts = deptService.findByRoleId(role.getId());
        for (Dept dept : depts) {
            deptIds.add(dept.getId());
            List<Dept> deptChildren = deptService.findByPid(dept.getId());
            if (deptChildren != null && !deptChildren.isEmpty()) {
                deptIds.addAll(deptService.getDeptChildren(deptChildren));
            }
        }
        return deptIds;
    }
}
