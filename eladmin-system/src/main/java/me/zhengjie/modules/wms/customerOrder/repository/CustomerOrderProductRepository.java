package me.zhengjie.modules.wms.customerOrder.repository;

import me.zhengjie.modules.wms.customerOrder.domain.CustomerOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author jie
* @date 2019-08-03
*/
public interface CustomerOrderProductRepository extends JpaRepository<CustomerOrderProduct, Long>, JpaSpecificationExecutor {

    List<CustomerOrderProduct> findByCustomerOrderIdAndStatusTrue(Long customerOrderId);

    @Modifying
    @Query(value = "delete s_customer_order_product  where product_code = ?1 and customer_order = ?2", nativeQuery = true)
    void deleteByProductCodeAndCustomerOrderId(String productCode, Long customerOrderId);

}