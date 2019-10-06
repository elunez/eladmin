package me.zhengjie.modules.wms.purchase.repository;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
* @author jie
* @date 2019-10-06
*/
public interface ProductPurchaseOrderRepository extends JpaRepository<ProductPurchaseOrder, Long>, JpaSpecificationExecutor {

    /**
     * 根据产品采购单单据编号查询产品采购单单信息
     * @param productPurchaseOrderCode
     * @return
     */
    @Query(value ="select * from product_purchase_order where product_purchase_order_code = ?1 and status = 1", nativeQuery = true)
    ProductPurchaseOrder findByProductPurchaseOrderCode(String productPurchaseOrderCode);
}