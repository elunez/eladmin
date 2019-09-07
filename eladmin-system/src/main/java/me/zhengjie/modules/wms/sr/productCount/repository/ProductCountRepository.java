package me.zhengjie.modules.wms.sr.productCount.repository;

import me.zhengjie.modules.wms.sr.productCount.domain.ProductCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author jie
* @date 2019-08-29
*/
public interface ProductCountRepository extends JpaRepository<ProductCount, Long>, JpaSpecificationExecutor {

    ProductCount findByProductId(Long productId);
}