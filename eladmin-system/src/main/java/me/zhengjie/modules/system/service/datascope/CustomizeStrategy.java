package me.zhengjie.modules.system.service.datascope;

import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.service.DeptService;
import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import me.zhengjie.modules.system.service.dto.UserDto;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class CustomizeStrategy implements DataScopeStrategy {

    private final DeptService deptService;

    @Override
    public void apply(Set<Long> deptIds, UserDto user, RoleSmallDto role) {
        Set<Dept> depts = deptService.findByRoleId(role.getId());
        for (Dept dept : depts) {
            deptIds.add(dept.getId());
            List<Dept> deptChildren = deptService.findByPid(dept.getId());
            if (CollUtil.isNotEmpty(deptChildren)) {
                deptIds.addAll(deptService.getDeptChildren(deptChildren));
            }
        }
    }
}
