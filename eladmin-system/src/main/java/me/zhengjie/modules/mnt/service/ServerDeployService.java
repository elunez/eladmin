package me.zhengjie.modules.mnt.service;

import me.zhengjie.modules.mnt.domain.ServerDeploy;
import me.zhengjie.modules.mnt.service.dto.ServerDeployDTO;
import me.zhengjie.modules.mnt.service.dto.ServerDeployQueryCriteria;
import org.springframework.data.domain.Pageable;

/**
* @author zhanghouying
* @date 2019-08-24
*/
public interface ServerDeployService {

    Object queryAll(ServerDeployQueryCriteria criteria, Pageable pageable);

    Object queryAll(ServerDeployQueryCriteria criteria);

    ServerDeployDTO findById(Long id);

	ServerDeployDTO create(ServerDeploy resources);

    void update(ServerDeploy resources);

    void delete(Long id);

    ServerDeployDTO findByIp(String ip);
}
