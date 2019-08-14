package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.bd.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 黄星星
 * @date 2019-07-26
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long >, JpaSpecificationExecutor {

    /**
     * 根据主键查询状态正常的产品类别
     * @param id
     * @return
     */
    ProductCategory findByIdAndStatusTrue(long id);

    /**
     * 根据name查询状态正常的产品类别
     * @param name
     * @return
     */
    ProductCategory findByNameAndStatusTrue(String name);

    /**
     * 根据name查询状态删除的产品类别
     * @param name
     * @return
     */
    ProductCategory findByNameAndStatusFalse(String name);

    /**
     * 更新指定产品类别状态为true
     * @param id
     */
    @Modifying
    @Query(value = "update bd_product_category set status = 1 where id = ?1",nativeQuery = true)
    void updateStatusToTrue(long id);

    /**
     * 删除产品类别(逻辑删除)
     * @param id
     */
    @Modifying
    @Query(value = "update bd_product_category set status = 0 where id = ?1",nativeQuery = true)
    void deleteProductCategory(long id);

    /**
     * 修改产品类别名称
     * @param name
     * @param id
     */
    @Modifying
    @Query(value = "update bd_product_category set name = ?1 where id = ?2",nativeQuery = true)
    void updateNameById(String name, long id);
}
