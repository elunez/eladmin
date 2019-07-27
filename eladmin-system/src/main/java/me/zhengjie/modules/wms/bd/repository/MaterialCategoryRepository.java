package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.IncomeCategory;
import me.zhengjie.modules.wms.bd.domain.MaterialCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 黄星星
 * @date 2019-07-26
 */
public interface MaterialCategoryRepository extends JpaRepository<MaterialCategory, Long >, JpaSpecificationExecutor {

}
