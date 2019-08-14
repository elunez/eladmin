package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.modules.wms.bd.domain.IncomeCategory;
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
