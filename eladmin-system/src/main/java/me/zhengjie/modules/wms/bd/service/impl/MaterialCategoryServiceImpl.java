package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.MaterialCategory;
import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.bd.domain.ProductCategory;
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
public class MaterialCategoryServiceImpl implements MaterialCategoryService {

    @Autowired
    private MaterialCategoryMapper materialCategoryMapper;

    @Autowired
    private MaterialCategoryRepository materialCategoryRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialCategoryDTO create(MaterialCategory resources) {
        /**
         * 查看状态正常的情况下该物料类别是否存在，如果存在，则提示物料类别已存在
         * 查看删除状态下该名字的物料类别，如果产品类别存在，则修改物料类别状态
         * 否则直接插入新的记录
         */
        MaterialCategory byNameAndStatusTrue = materialCategoryRepository.findByNameAndStatusTrue(resources.getName());
        if(null != byNameAndStatusTrue){
            throw new BadRequestException("该物料类别已经存在");
        }
        MaterialCategory byNameAndStatusFalse = materialCategoryRepository.findByNameAndStatusFalse(resources.getName());
        if(null != byNameAndStatusFalse){
            resources.setStatus(true);
            materialCategoryRepository.updateStatusToTrue(byNameAndStatusFalse.getId());
            Optional<MaterialCategory> productCategoryOptional = materialCategoryRepository.findById(byNameAndStatusFalse.getId());
            MaterialCategory materialCategory = productCategoryOptional.get();
            return materialCategoryMapper.toDto(materialCategory);
        }else{
            resources.getName();
            resources.setStatus(true);
            MaterialCategory materialCategory = materialCategoryRepository.save(resources);
            return materialCategoryMapper.toDto(materialCategory);
        }
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
        materialCategoryRepository.deleteMaterialCategory(id);
    }

    @Override
    public Object queryAll(MaterialCategoryDTO maaterialCategory, Pageable pageable) {
        Specification<MaterialCategory> specification = new Specification<MaterialCategory>() {
            @Override
            public Predicate toPredicate(Root<MaterialCategory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        Page<MaterialCategory> page = materialCategoryRepository.findAll(specification, pageable);
        return PageUtil.toPage(page.map(materialCategoryMapper::toDto));
    }

}
