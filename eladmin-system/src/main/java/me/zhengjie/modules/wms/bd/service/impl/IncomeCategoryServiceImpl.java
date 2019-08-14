package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.IncomeCategory;
import me.zhengjie.modules.wms.bd.domain.MaterialCategory;
import me.zhengjie.modules.wms.bd.domain.ProductCategory;
import me.zhengjie.modules.wms.bd.repository.IncomeCategoryRepository;
import me.zhengjie.modules.wms.bd.service.IncomeCategoryService;
import me.zhengjie.modules.wms.bd.service.dto.IncomeCategoryDTO;
import me.zhengjie.modules.wms.bd.service.mapper.IncomeCategoryMapper;
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
public class IncomeCategoryServiceImpl implements IncomeCategoryService {

    @Autowired
    private IncomeCategoryMapper incomeCategoryMapper;

    @Autowired
    private IncomeCategoryRepository incomeCategoryRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IncomeCategoryDTO create(IncomeCategory resources) {
        /**
         * 查看状态正常的情况下该收入类别是否存在，如果存在，则提示收入类别已存在
         * 查看删除状态下该名字的收入类别，如果收入类别存在，则修改收入类别状态
         * 否则直接插入新的记录
         */
        IncomeCategory byNameAndStatusTrue = incomeCategoryRepository.findByNameAndStatusTrue(resources.getName());
        if(null != byNameAndStatusTrue){
            throw new BadRequestException("该物料类别已经存在");
        }
        IncomeCategory byNameAndStatusFalse = incomeCategoryRepository.findByNameAndStatusFalse(resources.getName());
        if(null != byNameAndStatusFalse){
            resources.setStatus(true);
            incomeCategoryRepository.updateStatusToTrue(byNameAndStatusFalse.getId());
            Optional<IncomeCategory> incomeCategoryOptional = incomeCategoryRepository.findById(byNameAndStatusFalse.getId());
            IncomeCategory incomeCategory = incomeCategoryOptional.get();
            return incomeCategoryMapper.toDto(incomeCategory);
        }else{
            resources.getName();
            resources.setStatus(true);
            IncomeCategory materialCategory = incomeCategoryRepository.save(resources);
            return incomeCategoryMapper.toDto(materialCategory);
        }
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
        incomeCategoryRepository.deleteSupplierCategory(id);
    }

    @Override
    public Object queryAll(IncomeCategoryDTO incomeCategory, Pageable pageable) {
        Specification<IncomeCategory> specification = new Specification<IncomeCategory>() {
            @Override
            public Predicate toPredicate(Root<IncomeCategory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        Page<IncomeCategory> page = incomeCategoryRepository.findAll(specification, pageable);
        return PageUtil.toPage(page.map(incomeCategoryMapper::toDto));
    }

}
