package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.bd.domain.SupplierCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 黄星星
 * @date 2019-07-26
 */
public interface SupplierCategoryRepository extends JpaRepository<SupplierCategory, Long >, JpaSpecificationExecutor {

    /**
     * 根据主键查询状态正常的供应商类别
     * @param id
     * @return
     */
    SupplierCategory findByIdAndStatusTrue(long id);

    /**
     * 根据name查询状态正常的供应商类别
     * @param name
     * @return
     */
    SupplierCategory findByNameAndStatusTrue(String name);

    /**
     * 根据name查询状态删除的供应商类别
     * @param name
     * @return
     */
    SupplierCategory findByNameAndStatusFalse(String name);


    @Modifying
    @Query(value = "update bd_supplier_category set status = 1 where id = ?1",nativeQuery = true)
    void updateStatusToTrue(long id);

    /**
     * 删除供应商类别(逻辑删除)
     * @param id
     */
    @Modifying
    @Query(value = "update bd_supplier_category set status = 0 where id = ?1",nativeQuery = true)
    void deleteSupplierCategory(long id);
}
