package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.bd.repository.MeasureUnitRepository;
import me.zhengjie.modules.wms.bd.service.MeasureUnitService;
import me.zhengjie.modules.wms.bd.service.dto.MeasureUnitDTO;
import me.zhengjie.modules.wms.bd.service.mapper.MeasureUnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 黄星星
 * @date 2019-07-27
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeasureUnitServiceImpl implements MeasureUnitService {

    @Autowired
    private MeasureUnitMapper measureUnitMapper;

    @Autowired
    private MeasureUnitRepository measureUnitRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeasureUnitDTO create(MeasureUnit resources) {
        return measureUnitMapper.toDto(measureUnitRepository.save(resources));
    }

}
