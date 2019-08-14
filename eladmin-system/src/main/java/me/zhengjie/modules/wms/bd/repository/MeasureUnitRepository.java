package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 黄星星
 * @date 2019-07-26
 */
public interface MeasureUnitRepository extends JpaRepository<MeasureUnit, Long >, JpaSpecificationExecutor {

    /**
     * 根据主键查询状态正常的计量单位
     * @param id
     * @return
     */
    MeasureUnit findByIdAndStatusTrue(long id);

    /**
     * 根据name查询状态正常的计量单位
     * @param name
     * @return
     */
    MeasureUnit findByNameAndStatusTrue(String name);

    /**
     * 根据name查询状态删除的计量单位
     * @param name
     * @return
     */
    MeasureUnit findByNameAndStatusFalse(String name);


    @Modifying
    @Query(value = "update bd_measure_unit set status = 1 where id = ?1",nativeQuery = true)
    void updateStatusToTrue(long id);

    /**
     * 删除计量单位(逻辑删除)
     * @param id
     */
    @Modifying
    @Query(value = "update bd_measure_unit set status = 0 where id = ?1",nativeQuery = true)
    void deleteMeasureUnit(long id);

    /**
     * 修改计量单位名称
     * @param name
     * @param id
     */
    @Modifying
    @Query(value = "update bd_measure_unit set name = ?1 where id = ?2",nativeQuery = true)
    void updateNameById(String name, long id);
}
