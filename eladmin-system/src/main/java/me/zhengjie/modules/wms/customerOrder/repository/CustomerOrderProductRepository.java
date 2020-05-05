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

    /**
     * 根据客户订单id查看客户订单对应的所有产品
     * @param customerOrderId
     * @return
     */
    List<CustomerOrderProduct> findByCustomerOrderIdAndStatusTrue(Long customerOrderId);

    /**
     * 根据客户订单编号查询客户订单对应的所有产品信息
     * @param customerOrderCode
     * @return
     */
    List<CustomerOrderProduct> findByCustomerOrderCodeAndStatusTrue(String customerOrderCode);

    /**
     * 根据产品code以及客户订单id删除订单中对应的产品信息
     * @param productCode
     * @param customerOrderId
     */
    @Modifying
    @Query(value = "delete s_customer_order_product  where product_code = ?1 and customer_order_id = ?2", nativeQuery = true)
    void deleteByProductCodeAndCustomerOrderId(String productCode, Long customerOrderId);


    /**
     * 根据客户订单主键删除客户订单中的产品信息
     * @param customerOrderId
     */
    @Modifying
    @Query(value = "update s_customer_order_product set status = 0 where customer_order_id = ?1", nativeQuery = true)
    void deleteByCustomerOrderId(Long customerOrderId);

}