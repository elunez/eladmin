package me.zhengjie.modules.wms.bd.service;

import me.zhengjie.modules.wms.bd.domain.IncomeCategory;
import me.zhengjie.modules.wms.bd.service.dto.IncomeCategoryDTO;
import org.springframework.data.domain.Pageable;

/**
 * @author 黄星星
 * @date 2019-07-27
 */
public interface IncomeCategoryService {

    IncomeCategoryDTO create(IncomeCategory resources);

    IncomeCategoryDTO findById(long id);

    void delete(Long id);

    Object queryAll(IncomeCategoryDTO incomeCategory, Pageable pageable);
}
