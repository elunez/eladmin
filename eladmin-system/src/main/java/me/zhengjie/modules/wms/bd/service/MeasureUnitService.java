package me.zhengjie.modules.wms.bd.service;

import me.zhengjie.modules.system.service.dto.DictDTO;
import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.bd.service.dto.MeasureUnitDTO;
import org.springframework.data.domain.Pageable;

/**
 * @author 黄星星
 * @date 2019-07-27
 */
public interface MeasureUnitService {

    MeasureUnitDTO create(MeasureUnit resources);

    MeasureUnitDTO findById(long id);

    void delete(Long id);

    Object queryAll(MeasureUnitDTO dict, Pageable pageable);
}
