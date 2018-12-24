package me.zhengjie.system.service.impl;

import me.zhengjie.common.exception.BadRequestException;
import me.zhengjie.common.exception.EntityExistException;
import me.zhengjie.common.utils.ValidationUtil;
import me.zhengjie.system.domain.Role;
import me.zhengjie.system.repository.RoleRepository;
import me.zhengjie.system.service.RoleService;
import me.zhengjie.system.service.dto.RoleDTO;
import me.zhengjie.system.service.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author jie
 * @date 2018-12-03
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public RoleDTO findById(long id) {
        Optional<Role> role = roleRepository.findById(id);
        ValidationUtil.isNull(role,"Role","id",id);
        return roleMapper.toDto(role.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDTO create(Role resources) {
        if(roleRepository.findByName(resources.getName()) != null){
            throw new EntityExistException(Role.class,"username",resources.getName());
        }
        return roleMapper.toDto(roleRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Role resources) {
        Optional<Role> optionalRole = roleRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalRole,"Role","id",resources.getId());

        Role role = optionalRole.get();

        /**
         * 根据实际需求修改
         */
        if(role.getId().equals(1L)){
            throw new BadRequestException("该角色不能被修改");
        }

        Role role1 = roleRepository.findByName(resources.getName());

        if(role1 != null && !role1.getId().equals(role.getId())){
            throw new EntityExistException(Role.class,"username",resources.getName());
        }

        role.setName(resources.getName());
        role.setRemark(resources.getRemark());
        role.setPermissions(resources.getPermissions());
        roleRepository.save(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {

        /**
         * 根据实际需求修改
         */
        if(id.equals(1L)){
            throw new BadRequestException("该角色不能被删除");
        }
        roleRepository.deleteById(id);
    }

    @Override
    public Object getRoleTree() {

        List<Role> roleList = roleRepository.findAll();

        List<Map<String, Object>> list = new ArrayList<>();
        for (Role role : roleList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",role.getId());
            map.put("label",role.getName());
            list.add(map);
        }
        return list;
    }
}
