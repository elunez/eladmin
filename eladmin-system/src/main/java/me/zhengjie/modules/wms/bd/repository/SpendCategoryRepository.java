package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.IncomeCategory;
import me.zhengjie.modules.wms.bd.domain.SpendCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 黄星星
 * @date 2019-07-26
 */
public interface SpendCategoryRepository extends JpaRepository<SpendCategory, Long >, JpaSpecificationExecutor {

    /**
     * 查询存在的支出类别
     * @param id
     * @return
     */
    SpendCategory findByIdAndStatusTrue(long id);

    /**
     * 根据name查询状态正常的支出类别
     * @param name
     * @return
     */
    SpendCategory findByNameAndStatusTrue(String name);

    /**
     * 根据name查询状态删除的支出类别
     * @param name
     * @return
     */
    SpendCategory findByNameAndStatusFalse(String name);

    /**
     * 更新指定支出类别状态为true
     * @param id
     */
    @Modifying
    @Query(value = "update bd_spend_category set status = 1 where id = ?1",nativeQuery = true)
    void updateStatusToTrue(long id);

    /**
     * 删除支出类别(逻辑删除)
     * @param id
     */
    @Modifying
    @Query(value = "update bd_spend_category set status = 0 where id = ?1",nativeQuery = true)
    void deleteSpendCategory(long id);
}
