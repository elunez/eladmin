package me.zhengjie.modules.wms.bd.service;

import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.bd.domain.ProductCategory;
import me.zhengjie.modules.wms.bd.service.dto.MeasureUnitDTO;
import me.zhengjie.modules.wms.bd.service.dto.ProductCategoryDTO;
import org.springframework.data.domain.Pageable;

/**
 * @author 黄星星
 * @date 2019-07-27
 */
public interface ProductCategoryService {

    ProductCategoryDTO create(ProductCategory resources);

    ProductCategoryDTO findById(long id);

    void delete(Long id);

    Object queryAll(ProductCategoryDTO productCategory, Pageable pageable);
}
