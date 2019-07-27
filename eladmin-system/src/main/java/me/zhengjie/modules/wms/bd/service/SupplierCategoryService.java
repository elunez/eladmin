package me.zhengjie.modules.wms.bd.service;

import me.zhengjie.modules.wms.bd.domain.SupplierCategory;
import me.zhengjie.modules.wms.bd.service.dto.SupplierCategoryDTO;
import org.springframework.data.domain.Pageable;

/**
 * @author 黄星星
 * @date 2019-07-27
 */
public interface SupplierCategoryService {

    SupplierCategoryDTO create(SupplierCategory resources);

    SupplierCategoryDTO findById(long id);

    void delete(Long id);

    Object queryAll(SupplierCategoryDTO supplierCategory, Pageable pageable);
}
