package me.zhengjie.modules.mnt.service;

import me.zhengjie.modules.mnt.domain.Database;
import me.zhengjie.modules.mnt.service.dto.DatabaseDTO;
import me.zhengjie.modules.mnt.service.dto.DatabaseQueryCriteria;
import org.springframework.data.domain.Pageable;

/**
 * @author ZhangHouYing
 * @date 2019-08-24
 */
public interface DatabaseService {

    Object queryAll(DatabaseQueryCriteria criteria, Pageable pageable);

    Object queryAll(DatabaseQueryCriteria criteria);

    DatabaseDTO findById(String id);

    DatabaseDTO create(Database resources);

    void update(Database resources);

    void delete(String id);
}
