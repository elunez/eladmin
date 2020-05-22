package me.zhengjie.modules.wms.sr.productCount.repository;

import me.zhengjie.modules.wms.sr.productCount.domain.ProductCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
* @author jie
* @date 2019-08-29
*/
public interface ProductCountRepository extends JpaRepository<ProductCount, Long>, JpaSpecificationExecutor {

    ProductCount findByProductId(Long productId);

    ProductCount findByProductCode(String productCode);

    @Modifying
    @Query(value = "update sr_product_count set dph_number = 1 where product_code = ?2", nativeQuery = true)
    void updateDphNumber(long dphNumber, String productCode);
}