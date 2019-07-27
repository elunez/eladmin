package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.bd.repository.MeasureUnitRepository;
import me.zhengjie.modules.wms.bd.service.MeasureUnitService;
import me.zhengjie.modules.wms.bd.service.dto.MeasureUnitDTO;
import me.zhengjie.modules.wms.bd.service.mapper.MeasureUnitMapper;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeasureUnitDTO findById(long id) {
        Optional<MeasureUnit> measureUnit = measureUnitRepository.findById(id);
        ValidationUtil.isNull(measureUnit,"MeasureUnit","id",id);
        return measureUnitMapper.toDto(measureUnit.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        measureUnitRepository.deleteById(id);
    }

    @Override
    public Object queryAll(MeasureUnitDTO measureUnit, Pageable pageable) {
        Page<MeasureUnit> page = measureUnitRepository.findAll((root, query, cb) -> QueryHelp.getPredicate(root, measureUnit, cb), pageable);
        return PageUtil.toPage(page.map(measureUnitMapper::toDto));
    }

}
