package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Dict;
import me.zhengjie.modules.system.service.dto.DictDTO;
import me.zhengjie.modules.system.service.dto.DictQueryCriteria;
import org.springframework.data.domain.Pageable;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
public interface DictService {

    Object queryAll(DictQueryCriteria dict, Pageable pageable);

    DictDTO findById(Long id);

    DictDTO create(Dict resources);

    void update(Dict resources);

    void delete(Long id);
}