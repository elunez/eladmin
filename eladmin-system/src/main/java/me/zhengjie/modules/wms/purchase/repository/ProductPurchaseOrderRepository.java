package me.zhengjie.modules.wms.purchase.repository;

import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author jie
* @date 2019-10-06
*/
public interface ProductPurchaseOrderRepository extends JpaRepository<ProductPurchaseOrder, Long>, JpaSpecificationExecutor {
}