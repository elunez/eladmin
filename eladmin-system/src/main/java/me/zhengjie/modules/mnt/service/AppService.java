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

    Object queryAll(AppQueryCriteria criteria, Pageable pageable);

    Object queryAll(AppQueryCriteria criteria);

    AppDTO findById(Long id);

    AppDTO create(App resources);

    void update(App resources);

    void delete(Long id);
}
