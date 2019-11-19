package me.zhengjie.modules.mnt.service;

import me.zhengjie.modules.mnt.domain.Database;
import me.zhengjie.modules.mnt.service.dto.DatabaseDTO;
import me.zhengjie.modules.mnt.service.dto.DatabaseQueryCriteria;
import org.springframework.data.domain.Pageable;

/**
 * @author: ZhangHouYing
 * @date 2019-08-24
 */
public interface DatabaseService {

    /**
	 * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    Object queryAll(DatabaseQueryCriteria criteria, Pageable pageable);

    /**
	 * queryAll 不分页
    * @param criteria
    * @return
    */
    public Object queryAll(DatabaseQueryCriteria criteria);

    /**
	 * findById
     * @param id
     * @return
     */
    DatabaseDTO findById(String id);

    /**
	 * create
     * @param resources
     * @return
     */
    DatabaseDTO create(Database resources);

    /**
	 * update
     * @param resources
     */
    void update(Database resources);

    /**
	 * delete
     * @param id
     */
    void delete(String id);
}
