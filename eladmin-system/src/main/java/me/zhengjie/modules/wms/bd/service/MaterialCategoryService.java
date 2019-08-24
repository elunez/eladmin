package me.zhengjie.modules.wms.bd.service;

import me.zhengjie.modules.wms.bd.domain.MaterialCategory;
import me.zhengjie.modules.wms.bd.service.dto.MaterialCategoryDTO;
import org.springframework.data.domain.Pageable;

/**
 * @author 黄星星
 * @date 2019-07-27
 */
public interface MaterialCategoryService {

    MaterialCategoryDTO create(MaterialCategory resources);

    MaterialCategoryDTO findById(long id);

    void delete(Long id);

    Object queryAll(MaterialCategoryDTO materialCategory, Pageable pageable);

    Object queryAll(MaterialCategoryDTO materialCategory);
}
