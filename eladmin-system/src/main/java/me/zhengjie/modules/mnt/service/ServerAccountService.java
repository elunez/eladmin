package me.zhengjie.modules.mnt.service;

import me.zhengjie.modules.mnt.domain.ServerAccount;
import me.zhengjie.modules.mnt.service.dto.ServerAccountDTO;
import me.zhengjie.modules.mnt.service.dto.ServerAccountQueryCriteria;
import org.springframework.data.domain.Pageable;

/**
* @author zhanghouying
* @date 2019-08-24
*/
public interface ServerAccountService {

    /**
	 * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(ServerAccountQueryCriteria criteria, Pageable pageable);

    /**
	 * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(ServerAccountQueryCriteria criteria);

    /**
	 *  findById
     * @param id
     * @return
     */
    ServerAccountDTO findById(String id);

    /**
	 * create
     * @param resources
     * @return
     */
    ServerAccountDTO create(ServerAccount resources);

    /**
	 * update
     * @param resources
     */
    void update(ServerAccount resources);

    /**
	 * delete
     * @param id
     */
    void delete(String id);
}
