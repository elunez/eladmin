package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Permission;
import me.zhengjie.modules.system.service.dto.PermissionDTO;
import me.zhengjie.modules.system.service.dto.PermissionQueryCriteria;
import java.util.List;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-12-08
 */
public interface PermissionService {

    PermissionDTO findById(long id);

    PermissionDTO create(Permission resources);

    void update(Permission resources);

    void delete(Set<Permission> permissions);

    Object getPermissionTree(List<Permission> permissions);

    List<Permission> findByPid(long pid);

    Object buildTree(List<PermissionDTO> permissionDTOS);

    List<PermissionDTO> queryAll(PermissionQueryCriteria criteria);

    Set<Permission> getDeletePermission(List<Permission> permissions, Set<Permission> permissionSet);
}
