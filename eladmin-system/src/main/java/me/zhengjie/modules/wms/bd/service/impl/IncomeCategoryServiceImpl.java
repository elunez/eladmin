package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.modules.wms.bd.domain.IncomeCategory;
import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.bd.repository.IncomeCategoryRepository;
import me.zhengjie.modules.wms.bd.repository.MeasureUnitRepository;
import me.zhengjie.modules.wms.bd.service.IncomeCategoryService;
import me.zhengjie.modules.wms.bd.service.MeasureUnitService;
import me.zhengjie.modules.wms.bd.service.dto.IncomeCategoryDTO;
import me.zhengjie.modules.wms.bd.service.dto.MeasureUnitDTO;
import me.zhengjie.modules.wms.bd.service.mapper.IncomeCategoryMapper;
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
public class IncomeCategoryServiceImpl implements IncomeCategoryService {

    @Autowired
    private IncomeCategoryMapper incomeCategoryMapper;

    @Autowired
    private IncomeCategoryRepository incomeCategoryRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IncomeCategoryDTO create(IncomeCategory resources) {
        return incomeCategoryMapper.toDto(incomeCategoryRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IncomeCategoryDTO findById(long id) {
        Optional<IncomeCategory> incomeCategory = incomeCategoryRepository.findById(id);
        ValidationUtil.isNull(incomeCategory,"IncomeCategory","id",id);
        return incomeCategoryMapper.toDto(incomeCategory.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        incomeCategoryRepository.deleteById(id);
    }

    @Override
    public Object queryAll(IncomeCategoryDTO incomeCategory, Pageable pageable) {
        Page<IncomeCategory> page = incomeCategoryRepository.findAll((root, query, cb) -> QueryHelp.getPredicate(root, incomeCategory, cb), pageable);
        return PageUtil.toPage(page.map(incomeCategoryMapper::toDto));
    }

}
