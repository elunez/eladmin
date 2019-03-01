package me.zhengjie.modules.security.service;

import me.zhengjie.modules.system.domain.Permission;
import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.modules.system.repository.PermissionRepository;
import me.zhengjie.modules.system.repository.RoleRepository;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.modules.security.security.JwtUser;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author jie
 * @date 2018-11-22
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username){

        User user = null;
        if(ValidationUtil.isEmail(username)){
            user = userRepository.findByEmail(username);
        } else {
            user = userRepository.findByUsername(username);
        }

        if (user == null) {
            throw new EntityNotFoundException(User.class, "name", username);
        } else {
            return create(user);
        }
    }

    public UserDetails create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getAvatar(),
                user.getEmail(),
                mapToGrantedAuthorities(roleRepository.findByUsers_Id(user.getId()),permissionRepository),
                user.getEnabled(),
                user.getCreateTime(),
                user.getLastPasswordResetTime()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> roles,PermissionRepository permissionRepository) {

        Set<Permission> permissions = new HashSet<>();
        for (Role role : roles) {
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            permissions.addAll(permissionRepository.findByRoles_Id(role.getId()));
        }

        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());
    }
}
