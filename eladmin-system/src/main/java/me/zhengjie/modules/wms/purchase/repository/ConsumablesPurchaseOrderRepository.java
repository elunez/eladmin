package me.zhengjie.modules.wms.purchase.repository;

import me.zhengjie.modules.wms.purchase.domain.ConsumablesPurchaseOrder;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
* @author jie
* @date 2019-10-06
*/
public interface ConsumablesPurchaseOrderRepository extends JpaRepository<ConsumablesPurchaseOrder, Long>, JpaSpecificationExecutor {
    /**
     * 根据耗材采购单单据编号查询耗材采购单单信息
     * @param consumablesPurchaseOrderCode
     * @return
     */
    @Query(value ="select * from consumables_purchase_order where consumables_purchase_order_code = ?1 and status = 1", nativeQuery = true)
    ConsumablesPurchaseOrder findByConsumablesPurchaseOrderCode(String consumablesPurchaseOrderCode);
}