package me.zhengjie;

import me.zhengjie.modules.system.domain.Permission;
import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.modules.system.repository.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EladminSystemApplicationTests {
    @Autowired
    RoleRepository roleRepository;

    @Test
    public void contextLoads() {
        Collection<String> roles = roleRepository.findByUsers_Id(3l).stream().flatMap(role -> role.getPermissions().stream()).map(permi -> permi.getName()).collect(Collectors.toList());
        System.out.println(roles);
//        System.out.println(role);
    }

}

