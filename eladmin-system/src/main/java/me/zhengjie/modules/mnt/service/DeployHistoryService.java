package me.zhengjie.modules.mnt.service;

import me.zhengjie.modules.mnt.domain.DeployHistory;
import me.zhengjie.modules.mnt.service.dto.DeployHistoryDTO;
import me.zhengjie.modules.mnt.service.dto.DeployHistoryQueryCriteria;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author: ZhangHouYing
 * @date 2019-08-24
 */
public interface DeployHistoryService {

    /**
	 * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(DeployHistoryQueryCriteria criteria, Pageable pageable);

    /**
	 * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(DeployHistoryQueryCriteria criteria);

    /**
	 * findById
     * @param id
     * @return
     */
    DeployHistoryDTO findById(String id);

    /**
	 * create
     * @param resources
     * @return
     */
    DeployHistoryDTO create(DeployHistory resources);

    /**
	 * delete
     * @param id
     */
    void delete(String id);
}
