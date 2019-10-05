package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.ConsumablesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
* @author jie
* @date 2019-10-05
*/
public interface ConsumablesInfoRepository extends JpaRepository<ConsumablesInfo, Long>, JpaSpecificationExecutor {

    /**
     * 根据主键查询状态正常的计量单位
     * @param id
     * @return
     */
    ConsumablesInfo findByIdAndStatusTrue(long id);

    /**
     * 根据name查询状态正常的计量单位
     * @param consumablesName
     * @return
     */
    ConsumablesInfo findByConsumablesNameAndStatusTrue(String consumablesName);

    /**
     * 根据name查询状态删除的计量单位
     * @param consumablesName
     * @return
     */
    ConsumablesInfo findByConsumablesNameAndStatusFalse(String consumablesName);


    @Modifying
    @Query(value = "update bd_consumables_info set status = 1 where id = ?1",nativeQuery = true)
    void updateStatusToTrue(long id);

    /**
     * 删除计量单位(逻辑删除)
     * @param id
     */
    @Modifying
    @Query(value = "update bd_consumables_info set status = 0 where id = ?1",nativeQuery = true)
    void deleteConsumablesInfo(long id);

    /**
     * 修改计量单位名称
     * @param name
     * @param id
     */
    @Modifying
    @Query(value = "update bd_consumables_info set consumables_name = ?1 where id = ?2",nativeQuery = true)
    void updateNameById(String name, long id);
}