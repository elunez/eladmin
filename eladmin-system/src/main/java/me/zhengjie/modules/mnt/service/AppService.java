package me.zhengjie.modules.mnt.service;

import me.zhengjie.modules.mnt.domain.App;
import me.zhengjie.modules.mnt.service.dto.AppDTO;
import me.zhengjie.modules.mnt.service.dto.AppQueryCriteria;
import org.springframework.data.domain.Pageable;

/**
* @author zhanghouying
* @date 2019-08-24
*/
public interface AppService {

    /**
	 * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(AppQueryCriteria criteria, Pageable pageable);

    /**
	 * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(AppQueryCriteria criteria);

    /**
	 * findById
     * @param id
     * @return
     */
    AppDTO findById(String id);

    /**
	 * create
     * @param resources
     * @return
     */
    AppDTO create(App resources);

    /**
	 * update
     * @param resources
     */
    void update(App resources);

    /**
	 * delete
     * @param id
     */
    void delete(String id);
}
