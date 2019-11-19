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

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(ServerDeployQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(ServerDeployQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    ServerDeployDTO findById(String id);

    /**
     * create
     * @param resources
     * @return
     */
	ServerDeployDTO create(ServerDeploy resources);

    /**
     * update
     * @param resources
     */
    void update(ServerDeploy resources);

    /**
     * delete
     * @param id
     */
    void delete(String id);
}
