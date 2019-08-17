package me.zhengjie.modules.wms.order.repository;

import me.zhengjie.modules.wms.order.domain.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
* @author jie
* @date 2019-08-03
*/
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>, JpaSpecificationExecutor {

    /**
     * 删除仓库(逻辑删除)
     *
     * @param id
     */
    @Modifying
    @Query(value = "update s_customer_order set status = 0 where id = ?1", nativeQuery = true)
    void deleteCustomerOrder(long id);

    /**
     * 根据订单编号查询订单状态正常的客户订单
     *
     * @param customerOrderCode
     * @return
     */
    @Query(value = "select * from s_customer_order where customerOrderCode = ?1  and status = 1", nativeQuery = true)
    CustomerOrder findByCustomerOrderCodeAndStatusTrue(String customerOrderCode);
}
