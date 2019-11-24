package me.zhengjie.modules.mnt.repository;

import me.zhengjie.modules.mnt.domain.ServerDeploy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
* @author zhanghouying
* @date 2019-08-24
*/
public interface ServerDeployRepository extends JpaRepository<ServerDeploy, String>, JpaSpecificationExecutor {

    @Modifying
    @Query(value = "update mnt_server set account_id = null where account_id = ?1", nativeQuery = true)
    void changeByAccount(String id);
}
