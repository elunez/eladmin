package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.IncomeCategory;
import me.zhengjie.modules.wms.bd.domain.SpendCategory;
import me.zhengjie.modules.wms.bd.repository.SpendCategoryRepository;
import me.zhengjie.modules.wms.bd.service.SpendCategoryService;
import me.zhengjie.modules.wms.bd.service.dto.SpendCategoryDTO;
import me.zhengjie.modules.wms.bd.service.mapper.SpendCategoryMapper;
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
public class SpendCategoryServiceImpl implements SpendCategoryService {

    @Autowired
    private SpendCategoryMapper spendCategoryMapper;

    @Autowired
    private SpendCategoryRepository spendCategoryRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SpendCategoryDTO create(SpendCategory resources) {
        /**
         * 查看状态正常的情况下该收入类别是否存在，如果存在，则提示收入类别已存在
         * 查看删除状态下该名字的收入类别，如果收入类别存在，则修改收入类别状态
         * 否则直接插入新的记录
         */
        SpendCategory byNameAndStatusTrue = spendCategoryRepository.findByNameAndStatusTrue(resources.getName());
        if(null != byNameAndStatusTrue){
            throw new BadRequestException("该物料类别已经存在");
        }
        SpendCategory byNameAndStatusFalse = spendCategoryRepository.findByNameAndStatusFalse(resources.getName());
        if(null != byNameAndStatusFalse){
            resources.setStatus(true);
            spendCategoryRepository.updateStatusToTrue(byNameAndStatusFalse.getId());
            Optional<SpendCategory> incomeCategoryOptional = spendCategoryRepository.findById(byNameAndStatusFalse.getId());
            SpendCategory spendCategory = incomeCategoryOptional.get();
            return spendCategoryMapper.toDto(spendCategory);
        }else{
            resources.getName();
            resources.setStatus(true);
            SpendCategory spendCategory = spendCategoryRepository.save(resources);
            return spendCategoryMapper.toDto(spendCategory);
        }
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
        spendCategoryRepository.deleteSpendCategory(id);
    }

    @Override
    public Object queryAll(SpendCategoryDTO spendCategory, Pageable pageable) {
        Specification<SpendCategoryDTO> specification = new Specification<SpendCategoryDTO>() {
            @Override
            public Predicate toPredicate(Root<SpendCategoryDTO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        Page<SpendCategory> page = spendCategoryRepository.findAll(specification, pageable);
        return PageUtil.toPage(page.map(spendCategoryMapper::toDto));
    }

}
