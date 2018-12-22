package me.zhengjie.system.service.mapper;

import me.zhengjie.common.mapper.EntityMapper;
import me.zhengjie.system.domain.Permission;
import me.zhengjie.system.domain.Role;
import me.zhengjie.system.service.dto.PermissionDTO;
import me.zhengjie.system.service.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author jie
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper extends EntityMapper<PermissionDTO, Permission> {

}
