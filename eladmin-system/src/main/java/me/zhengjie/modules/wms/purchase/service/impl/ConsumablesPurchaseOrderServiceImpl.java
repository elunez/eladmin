package me.zhengjie.modules.wms.purchase.service.impl;

import me.zhengjie.modules.wms.purchase.domain.ConsumablesPurchaseOrder;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.purchase.repository.ConsumablesPurchaseOrderRepository;
import me.zhengjie.modules.wms.purchase.service.ConsumablesPurchaseOrderService;
import me.zhengjie.modules.wms.purchase.service.dto.ConsumablesPurchaseOrderDTO;
import me.zhengjie.modules.wms.purchase.service.dto.ConsumablesPurchaseOrderQueryCriteria;
import me.zhengjie.modules.wms.purchase.service.mapper.ConsumablesPurchaseOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

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
        Page<ConsumablesPurchaseOrder> page = consumablesPurchaseOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(consumablesPurchaseOrderMapper::toDto));
    }

    @Override
    public Object queryAll(ConsumablesPurchaseOrderQueryCriteria criteria){
        return consumablesPurchaseOrderMapper.toDto(consumablesPurchaseOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
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