package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.modules.wms.bd.domain.MaterialCategory;
import me.zhengjie.modules.wms.bd.repository.MaterialCategoryRepository;
import me.zhengjie.modules.wms.bd.service.MaterialCategoryService;
import me.zhengjie.modules.wms.bd.service.dto.MaterialCategoryDTO;
import me.zhengjie.modules.wms.bd.service.mapper.MaterialCategoryMapper;
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
public class MaterialCategoryServiceImpl implements MaterialCategoryService {

    @Autowired
    private MaterialCategoryMapper materialCategoryMapper;

    @Autowired
    private MaterialCategoryRepository materialCategoryRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialCategoryDTO create(MaterialCategory resources) {
        return materialCategoryMapper.toDto(materialCategoryRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialCategoryDTO findById(long id) {
        Optional<MaterialCategory> incomeCategory = materialCategoryRepository.findById(id);
        ValidationUtil.isNull(incomeCategory,"IncomeCategory","id",id);
        return materialCategoryMapper.toDto(incomeCategory.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        materialCategoryRepository.deleteById(id);
    }

    @Override
    public Object queryAll(MaterialCategoryDTO maaterialCategory, Pageable pageable) {
        Page<MaterialCategory> page = materialCategoryRepository.findAll((root, query, cb) -> QueryHelp.getPredicate(root, maaterialCategory, cb), pageable);
        return PageUtil.toPage(page.map(materialCategoryMapper::toDto));
    }

}
