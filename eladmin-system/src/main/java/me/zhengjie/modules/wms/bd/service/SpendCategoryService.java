package me.zhengjie.modules.wms.bd.service;

import me.zhengjie.modules.wms.bd.domain.SpendCategory;
import me.zhengjie.modules.wms.bd.service.dto.SpendCategoryDTO;
import org.springframework.data.domain.Pageable;

/**
 * @author 黄星星
 * @date 2019-07-27
 */
public interface SpendCategoryService {

    SpendCategoryDTO create(SpendCategory resources);

    SpendCategoryDTO findById(long id);

    void delete(Long id);

    Object queryAll(SpendCategoryDTO spendCategoryDTO, Pageable pageable);
}
