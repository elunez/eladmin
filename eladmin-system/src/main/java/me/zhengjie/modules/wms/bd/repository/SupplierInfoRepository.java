package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.CustomerInfo;
import me.zhengjie.modules.wms.bd.domain.SupplierInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
* @author jie
* @date 2019-08-03
*/
public interface SupplierInfoRepository extends JpaRepository<SupplierInfo, Long>, JpaSpecificationExecutor {

    /**
     * 根据主键查看状态正常的供应商资料
     * @param id
     * @return
     */
    SupplierInfo findByIdAndStatusTrue(long id);

    /**
     * 删除供应商资料
     * @param id
     */
    @Modifying
    @Query(value = "update bd_supplier_info set status = 0 where id = ?1",nativeQuery = true)
    void deleteSupplierInfo(long id);
}