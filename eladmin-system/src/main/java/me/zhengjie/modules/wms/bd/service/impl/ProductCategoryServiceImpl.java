package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
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
        /**
         * 查看状态正常的情况下该产品类别是否存在，如果存在，则提示产品类别已存在
         * 查看删除状态下该名字的产品类别，如果产品类别存在，则修改产品类别状态
         * 否则直接插入新的记录
         */
        ProductCategory byNameAndStatusTrue = productCategoryRepository.findByNameAndStatusTrue(resources.getName());
        if(null != byNameAndStatusTrue){
            throw new BadRequestException("该计量单位已经存在");
        }
        ProductCategory byNameAndStatusFalse = productCategoryRepository.findByNameAndStatusFalse(resources.getName());
        if(null != byNameAndStatusFalse){
            resources.setStatus(true);
            productCategoryRepository.updateStatusToTrue(byNameAndStatusFalse.getId());
            Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(byNameAndStatusFalse.getId());
            ProductCategory productCategory = productCategoryOptional.get();
            return productCategoryMapper.toDto(productCategory);
        }else{
            resources.getName();
            resources.setStatus(true);
            ProductCategory productCategory = productCategoryRepository.save(resources);
            return productCategoryMapper.toDto(productCategory);
        }
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
        productCategoryRepository.deleteProductCategory(id);
    }

    @Override
    public Object queryAll(ProductCategoryDTO productCategory, Pageable pageable) {
        Specification<ProductCategory> specification = new Specification<ProductCategory>() {
            @Override
            public Predicate toPredicate(Root<ProductCategory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                //状态
                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);

                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };
        Page<ProductCategory> page = productCategoryRepository.findAll(specification, pageable);
        return PageUtil.toPage(page.map(productCategoryMapper::toDto));
    }

}
