package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.modules.system.service.dto.RoleDTO;
import me.zhengjie.modules.system.service.dto.RoleQueryCriteria;
import me.zhengjie.modules.system.service.dto.RoleSmallDTO;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
public interface RoleService {

    RoleDTO findById(long id);

    RoleDTO create(Role resources);

    void update(Role resources);

    void delete(Long id);

    List<RoleSmallDTO> findByUsers_Id(Long id);

    Integer findByRoles(Set<Role> roles);

    void updateMenu(Role resources, RoleDTO roleDTO);

    void untiedMenu(Long id);

    Object queryAll(Pageable pageable);

    Object queryAll(RoleQueryCriteria criteria, Pageable pageable);

    List<RoleDTO> queryAll(RoleQueryCriteria criteria);

    void download(List<RoleDTO> queryAll, HttpServletResponse response) throws IOException;
}
