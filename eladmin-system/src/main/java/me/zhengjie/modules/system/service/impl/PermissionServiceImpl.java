package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.Permission;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.modules.system.repository.PermissionRepository;
import me.zhengjie.modules.system.service.PermissionService;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.dto.PermissionDTO;
import me.zhengjie.modules.system.service.dto.PermissionQueryCriteria;
import me.zhengjie.modules.system.service.mapper.PermissionMapper;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "permission")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    private final PermissionMapper permissionMapper;

    private final RoleService roleService;

    public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionMapper permissionMapper, RoleService roleService) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
        this.roleService = roleService;
    }

    @Override
    @Cacheable
    public List<PermissionDTO> queryAll(PermissionQueryCriteria criteria) {
        return permissionMapper.toDto(permissionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public PermissionDTO findById(long id) {
        Permission permission = permissionRepository.findById(id).orElseGet(Permission::new);
        ValidationUtil.isNull(permission.getId(),"Permission","id",id);
        return permissionMapper.toDto(permission);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public PermissionDTO create(Permission resources) {
        if(permissionRepository.findByName(resources.getName()) != null){
            throw new EntityExistException(Permission.class,"name",resources.getName());
        }
        return permissionMapper.toDto(permissionRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Permission resources) {
        Permission permission = permissionRepository.findById(resources.getId()).orElseGet(Permission::new);
        if(resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        ValidationUtil.isNull(permission.getId(),"Permission","id",resources.getId());

        Permission permission1 = permissionRepository.findByName(resources.getName());

        if(permission1 != null && !permission1.getId().equals(permission.getId())){
            throw new EntityExistException(Permission.class,"name",resources.getName());
        }

        permission.setName(resources.getName());
        permission.setAlias(resources.getAlias());
        permission.setPid(resources.getPid());
        permissionRepository.save(permission);
    }

    @Override
    public Set<Permission> getDeletePermission(List<Permission> permissions, Set<Permission> permissionSet) {
        // 递归找出待删除的菜单
        for (Permission permission : permissions) {
            permissionSet.add(permission);
            List<Permission> permissionList = permissionRepository.findByPid(permission.getId());
            if(permissionList!=null && permissionList.size()!=0){
                getDeletePermission(permissionList, permissionSet);
            }
        }
        return permissionSet;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Permission> permissions) {
        for (Permission permission : permissions) {
            roleService.untiedPermission(permission.getId());
            permissionRepository.delete(permission);
        }
    }

    @Override
    @Cacheable(key = "'tree'")
    public Object getPermissionTree(List<Permission> permissions) {
        List<Map<String,Object>> list = new LinkedList<>();
        permissions.forEach(permission -> {
                    if (permission!=null){
                        List<Permission> permissionList = permissionRepository.findByPid(permission.getId());
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",permission.getId());
                        map.put("label",permission.getAlias());
                        if(permissionList!=null && permissionList.size()!=0){
                            map.put("children",getPermissionTree(permissionList));
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

    @Override
    @Cacheable(key = "'pid:'+#p0")
    public List<Permission> findByPid(long pid) {
        return permissionRepository.findByPid(pid);
    }

    @Override
    @Cacheable
    public Object buildTree(List<PermissionDTO> permissionDTOS) {

        List<PermissionDTO> trees = new ArrayList<>();

        for (PermissionDTO permissionDTO : permissionDTOS) {

            if ("0".equals(permissionDTO.getPid().toString())) {
                trees.add(permissionDTO);
            }

            for (PermissionDTO it : permissionDTOS) {
                if (it.getPid().equals(permissionDTO.getId())) {
                    if (permissionDTO.getChildren() == null) {
                        permissionDTO.setChildren(new ArrayList<>());
                    }
                    permissionDTO.getChildren().add(it);
                }
            }
        }

        Integer totalElements = permissionDTOS.size();

        Map<String,Object> map = new HashMap<>();
        map.put("content",trees.size() == 0?permissionDTOS:trees);
        map.put("totalElements",totalElements);
        return map;
    }
}
