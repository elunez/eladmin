package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.MaterialCategory;
import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.bd.domain.SupplierCategory;
import me.zhengjie.modules.wms.bd.repository.SupplierCategoryRepository;
import me.zhengjie.modules.wms.bd.service.SupplierCategoryService;
import me.zhengjie.modules.wms.bd.service.dto.SupplierCategoryDTO;
import me.zhengjie.modules.wms.bd.service.mapper.SupplierCategoryMapper;
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
public class SupplierCategoryServiceImpl implements SupplierCategoryService {

    @Autowired
    private SupplierCategoryMapper supplierCategoryMapper;

    @Autowired
    private SupplierCategoryRepository supplierCategoryRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SupplierCategoryDTO create(SupplierCategory resources) {
        /**
         * 查看状态正常的情况下该供应商类别是否存在，如果存在，则提示供应商类别已存在
         * 查看删除状态下该名字的供应商类别，如果供应商类别存在，则修改供应商类别状态
         * 否则直接插入新的记录
         */
        SupplierCategory byNameAndStatusTrue = supplierCategoryRepository.findByNameAndStatusTrue(resources.getName());
        if(null != byNameAndStatusTrue){
            throw new BadRequestException("该计量单位已经存在");
        }
        SupplierCategory byNameAndStatusFalse = supplierCategoryRepository.findByNameAndStatusFalse(resources.getName());
        if(null != byNameAndStatusFalse){
            resources.setStatus(true);
            supplierCategoryRepository.updateStatusToTrue(byNameAndStatusFalse.getId());
            Optional<SupplierCategory> supplierCategoryOptional = supplierCategoryRepository.findById(byNameAndStatusFalse.getId());
            SupplierCategory supplierCategory = supplierCategoryOptional.get();
            return supplierCategoryMapper.toDto(supplierCategory);
        }else{
            resources.getName();
            resources.setStatus(true);
            SupplierCategory supplierCategory = supplierCategoryRepository.save(resources);
            return supplierCategoryMapper.toDto(supplierCategory);
        }
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
        supplierCategoryRepository.deleteSupplierCategory(id);
    }

    @Override
    public Object queryAll(SupplierCategoryDTO supplierCategory, Pageable pageable) {
        Specification<SupplierCategory> specification = new Specification<SupplierCategory>() {
            @Override
            public Predicate toPredicate(Root<SupplierCategory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        Page<SupplierCategory> page = supplierCategoryRepository.findAll(specification, pageable);
        return PageUtil.toPage(page.map(supplierCategoryMapper::toDto));
    }

}
