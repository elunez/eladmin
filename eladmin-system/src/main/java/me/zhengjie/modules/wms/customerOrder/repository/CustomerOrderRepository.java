package me.zhengjie.modules.wms.customerOrder.repository;

import me.zhengjie.modules.wms.customerOrder.domain.CustomerOrder;
import me.zhengjie.modules.wms.customerOrder.domain.CustomerOrderProduct;
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
    @Query(value = "select * from s_customer_order where customer_order_code = ?1  and status = 1", nativeQuery = true)
    CustomerOrder findByCustomerOrderCodeAndStatusTrue(String customerOrderCode);


    /**
     * 更新客户订单状态
     * @param procStatus
     */
    @Modifying
    @Query(value = "update s_customer_order set proc_status = ?1 where customer_order_code = ?2", nativeQuery = true)
    void updateProcStatus(String procStatus, String customerOrderCode);


}
