package me.zhengjie.system.repository;

import me.zhengjie.system.domain.Permission;
import me.zhengjie.system.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.Set;

/**
 * @author jie
 * @date 2018-12-03
 */
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor {

    /**
     * findByName
     * @param name
     * @return
     */
    Permission findByName(String name);

    /**
     * findByRoles
     * @param roleSet
     * @return
     */
    Set<Permission> findByRoles(Set<Role> roleSet);

    /**
     * findByPid
     * @param pid
     * @return
     */
    List<Permission> findByPid(long pid);
}
