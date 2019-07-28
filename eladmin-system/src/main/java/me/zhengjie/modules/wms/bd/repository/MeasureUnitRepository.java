package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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
}
