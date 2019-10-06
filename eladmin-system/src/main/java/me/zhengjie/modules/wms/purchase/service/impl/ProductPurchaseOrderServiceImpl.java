package me.zhengjie.modules.wms.purchase.service.impl;

import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrder;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.purchase.repository.ProductPurchaseOrderRepository;
import me.zhengjie.modules.wms.purchase.service.ProductPurchaseOrderService;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderDTO;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderQueryCriteria;
import me.zhengjie.modules.wms.purchase.service.mapper.ProductPurchaseOrderMapper;
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
public class ProductPurchaseOrderServiceImpl implements ProductPurchaseOrderService {

    @Autowired
    private ProductPurchaseOrderRepository productPurchaseOrderRepository;

    @Autowired
    private ProductPurchaseOrderMapper productPurchaseOrderMapper;

    @Override
    public Object queryAll(ProductPurchaseOrderQueryCriteria criteria, Pageable pageable){
        Specification<ProductPurchaseOrder> specification = new Specification<ProductPurchaseOrder>() {
            @Override
            public Predicate toPredicate(Root<ProductPurchaseOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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

        Page<ProductPurchaseOrder> page = productPurchaseOrderRepository.findAll(specification, pageable);
        return PageUtil.toPage(page.map(productPurchaseOrderMapper::toDto));
    }

    @Override
    public Object queryAll(ProductPurchaseOrderQueryCriteria criteria){
        Specification<ProductPurchaseOrder> specification = new Specification<ProductPurchaseOrder>() {
            @Override
            public Predicate toPredicate(Root<ProductPurchaseOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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

        List<ProductPurchaseOrder> productPurchaseOrderList = productPurchaseOrderRepository.findAll(specification);
        return productPurchaseOrderMapper.toDto(productPurchaseOrderList);
    }

    @Override
    public ProductPurchaseOrderDTO findById(Long id) {
        Optional<ProductPurchaseOrder> productPurchaseOrder = productPurchaseOrderRepository.findById(id);
        ValidationUtil.isNull(productPurchaseOrder,"ProductPurchaseOrder","id",id);
        return productPurchaseOrderMapper.toDto(productPurchaseOrder.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductPurchaseOrderDTO create(ProductPurchaseOrder resources) {
        return productPurchaseOrderMapper.toDto(productPurchaseOrderRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductPurchaseOrder resources) {
        Optional<ProductPurchaseOrder> optionalProductPurchaseOrder = productPurchaseOrderRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalProductPurchaseOrder,"ProductPurchaseOrder","id",resources.getId());
        ProductPurchaseOrder productPurchaseOrder = optionalProductPurchaseOrder.get();
        productPurchaseOrder.copy(resources);
        productPurchaseOrderRepository.save(productPurchaseOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        productPurchaseOrderRepository.deleteById(id);
    }
}