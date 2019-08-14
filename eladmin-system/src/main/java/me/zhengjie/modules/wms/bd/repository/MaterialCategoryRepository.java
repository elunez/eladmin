package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.MaterialCategory;
import me.zhengjie.modules.wms.bd.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 黄星星
 * @date 2019-07-26
 */
public interface MaterialCategoryRepository extends JpaRepository<MaterialCategory, Long >, JpaSpecificationExecutor {

    /**
     * 查询存在的物料类别
     * @param id
     * @return
     */
    MaterialCategory findByIdAndStatusTrue(long id);

    /**
     * 根据name查询状态正常的类别类别
     * @param name
     * @return
     */
    MaterialCategory findByNameAndStatusTrue(String name);

    /**
     * 根据name查询状态删除的类别类别
     * @param name
     * @return
     */
    MaterialCategory findByNameAndStatusFalse(String name);

    /**
     * 更新指定类别类别状态为true
     * @param id
     */
    @Modifying
    @Query(value = "update bd_product_category set status = 1 where id = ?1",nativeQuery = true)
    void updateStatusToTrue(long id);

    /**
     * 删除物料类别(逻辑删除)
     * @param id
     */
    @Modifying
    @Query(value = "update bd_product_category set status = 0 where id = ?1",nativeQuery = true)
    void deleteMaterialCategory(long id);
}
