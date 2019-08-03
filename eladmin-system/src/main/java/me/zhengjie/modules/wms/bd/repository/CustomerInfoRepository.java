package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.CustomerInfo;
import me.zhengjie.modules.wms.bd.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
* @author jie
* @date 2019-08-03
*/
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long>, JpaSpecificationExecutor {

    CustomerInfo findByIdAndStatusTrue(long id);

    /**
     * 删除产品资料
     * @param id
     */
    @Modifying
    @Query(value = "update bd_customer_info set status = 0 where id = ?1",nativeQuery = true)
    void deleteCustomerInfo(long id);
}