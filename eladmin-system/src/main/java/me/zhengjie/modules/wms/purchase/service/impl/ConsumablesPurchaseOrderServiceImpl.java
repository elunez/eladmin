package me.zhengjie.modules.wms.purchase.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.purchase.domain.ConsumablesPurchaseOrder;
import me.zhengjie.modules.wms.purchase.domain.ConsumablesPurchaseOrderProduct;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrder;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrderProduct;
import me.zhengjie.modules.wms.purchase.repository.ConsumablesPurchaseOrderProductRepository;
import me.zhengjie.modules.wms.purchase.request.*;
import me.zhengjie.modules.wms.purchase.service.dto.*;
import me.zhengjie.modules.wms.purchase.service.mapper.ConsumablesPurchaseOrderProductMapper;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.purchase.repository.ConsumablesPurchaseOrderRepository;
import me.zhengjie.modules.wms.purchase.service.ConsumablesPurchaseOrderService;
import me.zhengjie.modules.wms.purchase.service.mapper.ConsumablesPurchaseOrderMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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

    @Autowired
    private ConsumablesPurchaseOrderProductRepository consumablesPurchaseOrderProductRepository;

    @Autowired
    private ConsumablesPurchaseOrderProductMapper consumablesPurchaseOrderProductMapper;

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
    public ConsumablesPurchaseOrderDTO create(CreateConsumablesPurchaseOrderRequest createConsumablesPurchaseOrderRequest) {
        ConsumablesPurchaseOrder consumablesPurchaseOrder = new ConsumablesPurchaseOrder();
        BeanUtils.copyProperties(createConsumablesPurchaseOrderRequest, consumablesPurchaseOrder);

        String consumablesPurchaseOrderCode = consumablesPurchaseOrder.getConsumablesPurchaseOrderCode();
        if(!StringUtils.hasLength(consumablesPurchaseOrderCode)){
            throw new BadRequestException("耗材采购单单据编号不能为空!");
        }

        consumablesPurchaseOrder.setStatus(true);
        // 新增耗材采购单
        consumablesPurchaseOrderRepository.save(consumablesPurchaseOrder);

        consumablesPurchaseOrder = consumablesPurchaseOrderRepository.findByConsumablesPurchaseOrderCode(consumablesPurchaseOrder.getConsumablesPurchaseOrderCode());

        // 新增产品采购单产品信息
        List<ConsumablesPurchaseOrderProductRequest> consumablesPurchaseOrderProductRequestList = createConsumablesPurchaseOrderRequest.getConsumablesPurchaseOrderProductList();
        if(CollectionUtils.isEmpty(consumablesPurchaseOrderProductRequestList)){
            throw new BadRequestException("耗材采购单产品信息不能为空!");
        }

        for(ConsumablesPurchaseOrderProductRequest consumablesPurchaseOrderProductRequest : consumablesPurchaseOrderProductRequestList){
            ConsumablesPurchaseOrderProduct consumablesPurchaseOrderProduct = new ConsumablesPurchaseOrderProduct();
            BeanUtils.copyProperties(consumablesPurchaseOrderProductRequest, consumablesPurchaseOrderProduct);
            consumablesPurchaseOrderProduct.setStatus(true);
            consumablesPurchaseOrderProduct.setConsumablesPurchaseOrderId(consumablesPurchaseOrder.getId());
            consumablesPurchaseOrderProductRepository.save(consumablesPurchaseOrderProduct);
        }


        ConsumablesPurchaseOrderDTO consumablesPurchaseOrderDTO = consumablesPurchaseOrderMapper.toDto(consumablesPurchaseOrder);

        List<ConsumablesPurchaseOrderProduct> consumablesPurchaseOrderProductList = consumablesPurchaseOrderProductRepository.queryByConsumablesPurchaseOrderIdAndStatusTrue(consumablesPurchaseOrder.getId());
        if(!CollectionUtils.isEmpty(consumablesPurchaseOrderProductList)){
            List<ConsumablesPurchaseOrderProductDTO> consumablesPurchaseOrderProductDTOList = new ArrayList<>();
            for(ConsumablesPurchaseOrderProduct consumablesPurchaseOrderProduct : consumablesPurchaseOrderProductList){
                ConsumablesPurchaseOrderProductDTO consumablesPurchaseOrderProductDTO = new ConsumablesPurchaseOrderProductDTO();
                BeanUtils.copyProperties(consumablesPurchaseOrderProduct, consumablesPurchaseOrderProductDTO);
                consumablesPurchaseOrderProductDTOList.add(consumablesPurchaseOrderProductDTO);
            }
            consumablesPurchaseOrderDTO.setConsumablesPurchaseOrderProductList(consumablesPurchaseOrderProductDTOList);
        }

        return consumablesPurchaseOrderDTO;
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


    /**
     * 审核耗材采购单
     * @param auditConsumablesPurchaseOrderRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditConsumablesPurchaseOrder(AuditConsumablesPurchaseOrderRequest auditConsumablesPurchaseOrderRequest) {
        Long id = auditConsumablesPurchaseOrderRequest.getId();
        Long auditUserId = auditConsumablesPurchaseOrderRequest.getAuditUserId();
        String auditUserName = auditConsumablesPurchaseOrderRequest.getAuditUserName();
        String auditOpinion = auditConsumablesPurchaseOrderRequest.getAuditOpinion();

        Optional<ConsumablesPurchaseOrder> optionalProductPurchaseOrder = consumablesPurchaseOrderRepository.findById(id);

        ValidationUtil.isNull( optionalProductPurchaseOrder,"ProductPurchaseOrder","id",id);
        ConsumablesPurchaseOrder consumablesPurchaseOrder = optionalProductPurchaseOrder.get();
        consumablesPurchaseOrder.setAuditUserId(auditUserId);
        consumablesPurchaseOrder.setAuditUserName(auditUserName);
        consumablesPurchaseOrder.setAuditOpinion(auditOpinion);
        Date date = new Date();
        consumablesPurchaseOrder.setAuditTime(date);
        consumablesPurchaseOrderRepository.save(consumablesPurchaseOrder);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        consumablesPurchaseOrderRepository.deleteById(id);
    }
}