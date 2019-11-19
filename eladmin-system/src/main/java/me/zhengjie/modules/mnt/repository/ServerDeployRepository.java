package me.zhengjie.modules.mnt.repository;

import me.zhengjie.modules.mnt.domain.ServerDeploy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author zhanghouying
* @date 2019-08-24
*/
public interface ServerDeployRepository extends JpaRepository<ServerDeploy, String>, JpaSpecificationExecutor {
}
