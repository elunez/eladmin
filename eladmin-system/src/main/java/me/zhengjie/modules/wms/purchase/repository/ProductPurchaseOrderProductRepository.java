package me.zhengjie.modules.wms.purchase.repository;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author jie
* @date 2019-10-06
*/
public interface ProductPurchaseOrderProductRepository extends JpaRepository<ProductPurchaseOrderProduct, Long>, JpaSpecificationExecutor {

    /**
     * 查询指定产品采购单下所有的产品信息（状态为true）
     * @param productPurchaseOrderId
     * @return
     */
    @Query(value ="select * from product_purchase_order_product where product_purchase_order_id = ?1 and status =1", nativeQuery = true)
    List<ProductPurchaseOrderProduct> queryByProductPurchaseOrderIdAndStatusTrue(Long productPurchaseOrderId);

    @Modifying
    @Query(value = "delete from product_purchase_order_product  where product_code = ?1 and product_purchase_order_id = ?2", nativeQuery = true)
    void deleteByProductCodeAndProductPurchaseOrderId(String productCode, Long productPurchaseOrderId);
}