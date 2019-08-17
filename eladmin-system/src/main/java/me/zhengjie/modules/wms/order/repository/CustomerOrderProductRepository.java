package me.zhengjie.modules.wms.order.repository;

import me.zhengjie.modules.wms.order.domain.CustomerOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* @author jie
* @date 2019-08-03
*/
public interface CustomerOrderProductRepository extends JpaRepository<CustomerOrderProduct, Long>, JpaSpecificationExecutor {

    List<CustomerOrderProduct> findByCustomerOrderIdAndStatusTrue(Long customerId);
}