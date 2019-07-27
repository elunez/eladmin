package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.modules.wms.bd.domain.ProductCategory;
import me.zhengjie.modules.wms.bd.domain.SpendCategory;
import me.zhengjie.modules.wms.bd.repository.ProductCategoryRepository;
import me.zhengjie.modules.wms.bd.repository.SpendCategoryRepository;
import me.zhengjie.modules.wms.bd.service.ProductCategoryService;
import me.zhengjie.modules.wms.bd.service.SpendCategoryService;
import me.zhengjie.modules.wms.bd.service.dto.ProductCategoryDTO;
import me.zhengjie.modules.wms.bd.service.dto.SpendCategoryDTO;
import me.zhengjie.modules.wms.bd.service.mapper.ProductCategoryMapper;
import me.zhengjie.modules.wms.bd.service.mapper.SpendCategoryMapper;
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
public class SpendCategoryServiceImpl implements SpendCategoryService {

    @Autowired
    private SpendCategoryMapper spendCategoryMapper;

    @Autowired
    private SpendCategoryRepository spendCategoryRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SpendCategoryDTO create(SpendCategory resources) {
        return spendCategoryMapper.toDto(spendCategoryRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SpendCategoryDTO findById(long id) {
        Optional<SpendCategory> spendCategory = spendCategoryRepository.findById(id);
        ValidationUtil.isNull(spendCategory,"spendCategory","id",id);
        return spendCategoryMapper.toDto(spendCategory.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        spendCategoryRepository.deleteById(id);
    }

    @Override
    public Object queryAll(SpendCategoryDTO spendCategory, Pageable pageable) {
        Page<SpendCategory> page = spendCategoryRepository.findAll((root, query, cb) -> QueryHelp.getPredicate(root, spendCategory, cb), pageable);
        return PageUtil.toPage(page.map(spendCategoryMapper::toDto));
    }

}
