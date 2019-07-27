package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.modules.wms.bd.domain.ProductCategory;
import me.zhengjie.modules.wms.bd.repository.ProductCategoryRepository;
import me.zhengjie.modules.wms.bd.service.ProductCategoryService;
import me.zhengjie.modules.wms.bd.service.dto.ProductCategoryDTO;
import me.zhengjie.modules.wms.bd.service.mapper.ProductCategoryMapper;
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
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductCategoryDTO create(ProductCategory resources) {
        return productCategoryMapper.toDto(productCategoryRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductCategoryDTO findById(long id) {
        Optional<ProductCategory> productCategory = productCategoryRepository.findById(id);
        ValidationUtil.isNull(productCategory,"productCategory","id",id);
        return productCategoryMapper.toDto(productCategory.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        productCategoryRepository.deleteById(id);
    }

    @Override
    public Object queryAll(ProductCategoryDTO productCategory, Pageable pageable) {
        Page<ProductCategory> page = productCategoryRepository.findAll((root, query, cb) -> QueryHelp.getPredicate(root, productCategory, cb), pageable);
        return PageUtil.toPage(page.map(productCategoryMapper::toDto));
    }

}
