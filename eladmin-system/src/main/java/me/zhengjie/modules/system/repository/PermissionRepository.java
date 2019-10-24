package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    Permission findByName(String name);

    List<Permission> findByPid(long pid);
}
