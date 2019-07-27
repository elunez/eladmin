package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.modules.wms.bd.domain.SpendCategory;
import me.zhengjie.modules.wms.bd.domain.SupplierCategory;
import me.zhengjie.modules.wms.bd.repository.SpendCategoryRepository;
import me.zhengjie.modules.wms.bd.repository.SupplierCategoryRepository;
import me.zhengjie.modules.wms.bd.service.SpendCategoryService;
import me.zhengjie.modules.wms.bd.service.SupplierCategoryService;
import me.zhengjie.modules.wms.bd.service.dto.SpendCategoryDTO;
import me.zhengjie.modules.wms.bd.service.dto.SupplierCategoryDTO;
import me.zhengjie.modules.wms.bd.service.mapper.SpendCategoryMapper;
import me.zhengjie.modules.wms.bd.service.mapper.SupplierCategoryMapper;
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
public class SupplierCategoryServiceImpl implements SupplierCategoryService {

    @Autowired
    private SupplierCategoryMapper supplierCategoryMapper;

    @Autowired
    private SupplierCategoryRepository supplierCategoryRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SupplierCategoryDTO create(SupplierCategory resources) {
        return supplierCategoryMapper.toDto(supplierCategoryRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SupplierCategoryDTO findById(long id) {
        Optional<SupplierCategory> spendCategory = supplierCategoryRepository.findById(id);
        ValidationUtil.isNull(spendCategory,"spendCategory","id",id);
        return supplierCategoryMapper.toDto(spendCategory.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        supplierCategoryRepository.deleteById(id);
    }

    @Override
    public Object queryAll(SupplierCategoryDTO supplierCategory, Pageable pageable) {
        Page<SupplierCategory> page = supplierCategoryRepository.findAll((root, query, cb) -> QueryHelp.getPredicate(root, supplierCategory, cb), pageable);
        return PageUtil.toPage(page.map(supplierCategoryMapper::toDto));
    }

}
