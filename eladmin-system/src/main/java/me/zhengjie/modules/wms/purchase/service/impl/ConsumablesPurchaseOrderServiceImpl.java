package me.zhengjie.modules.wms.purchase.service.impl;

import me.zhengjie.modules.wms.purchase.domain.ConsumablesPurchaseOrder;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrder;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.purchase.repository.ConsumablesPurchaseOrderRepository;
import me.zhengjie.modules.wms.purchase.service.ConsumablesPurchaseOrderService;
import me.zhengjie.modules.wms.purchase.service.dto.ConsumablesPurchaseOrderDTO;
import me.zhengjie.modules.wms.purchase.service.dto.ConsumablesPurchaseOrderQueryCriteria;
import me.zhengjie.modules.wms.purchase.service.mapper.ConsumablesPurchaseOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
* @author jie
* @date 2019-10-06
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ConsumablesPurchaseOrderServiceImpl implements ConsumablesPurchaseOrderService {

    @Autowired
    private ConsumablesPurchaseOrderRepository consumablesPurchaseOrderRepository;

    @Autowired
    private ConsumablesPurchaseOrderMapper consumablesPurchaseOrderMapper;

    @Override
    public Object queryAll(ConsumablesPurchaseOrderQueryCriteria criteria, Pageable pageable){
        Specification<ConsumablesPurchaseOrder> specification = new Specification<ConsumablesPurchaseOrder>() {
            @Override
            public Predicate toPredicate(Root<ConsumablesPurchaseOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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

        Page<ConsumablesPurchaseOrder> page = consumablesPurchaseOrderRepository.findAll(specification, pageable);
        return PageUtil.toPage(page.map(consumablesPurchaseOrderMapper::toDto));
    }

    @Override
    public Object queryAll(ConsumablesPurchaseOrderQueryCriteria criteria){
        Specification<ConsumablesPurchaseOrder> specification = new Specification<ConsumablesPurchaseOrder>() {
            @Override
            public Predicate toPredicate(Root<ConsumablesPurchaseOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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

        List<ConsumablesPurchaseOrder> consumablesPurchaseOrderList = consumablesPurchaseOrderRepository.findAll(specification);
        return consumablesPurchaseOrderMapper.toDto(consumablesPurchaseOrderList);
    }

    @Override
    public ConsumablesPurchaseOrderDTO findById(Long id) {
        Optional<ConsumablesPurchaseOrder> consumablesPurchaseOrder = consumablesPurchaseOrderRepository.findById(id);
        ValidationUtil.isNull(consumablesPurchaseOrder,"ConsumablesPurchaseOrder","id",id);
        return consumablesPurchaseOrderMapper.toDto(consumablesPurchaseOrder.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ConsumablesPurchaseOrderDTO create(ConsumablesPurchaseOrder resources) {
        return consumablesPurchaseOrderMapper.toDto(consumablesPurchaseOrderRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ConsumablesPurchaseOrder resources) {
        Optional<ConsumablesPurchaseOrder> optionalConsumablesPurchaseOrder = consumablesPurchaseOrderRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalConsumablesPurchaseOrder,"ConsumablesPurchaseOrder","id",resources.getId());
        ConsumablesPurchaseOrder consumablesPurchaseOrder = optionalConsumablesPurchaseOrder.get();
        consumablesPurchaseOrder.copy(resources);
        consumablesPurchaseOrderRepository.save(consumablesPurchaseOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        consumablesPurchaseOrderRepository.deleteById(id);
    }
}