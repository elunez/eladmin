package me.zhengjie.modules.wms.purchase.repository;

import me.zhengjie.modules.wms.purchase.domain.ConsumablesPurchaseOrderProduct;
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
public interface ConsumablesPurchaseOrderProductRepository extends JpaRepository<ConsumablesPurchaseOrderProduct, Long>, JpaSpecificationExecutor {

    /**
     * 查询指定产品采购单下所有的产品信息（状态为true）
     * @param consumablesPurchaseOrderId
     * @return
     */
    @Query(value ="select * from consumables_purchase_order_product where consumables_purchase_order_id = ?1 and status =1", nativeQuery = true)
    List<ConsumablesPurchaseOrderProduct> queryByConsumablesPurchaseOrderIdAndStatusTrue(Long consumablesPurchaseOrderId);

    @Modifying
    @Query(value = "delete from product_purchase_order_product  where consumables_code = ?1 and consumables_purchase_order_id = ?2", nativeQuery = true)
    void deleteByProductCodeAndConsumablesPurchaseOrderId(String consumablesCode, Long consumablesPurchaseOrderId);
}