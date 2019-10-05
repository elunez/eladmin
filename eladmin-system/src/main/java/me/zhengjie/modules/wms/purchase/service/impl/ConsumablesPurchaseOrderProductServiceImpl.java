package me.zhengjie.modules.wms.purchase.service.impl;

import me.zhengjie.modules.wms.purchase.domain.ConsumablesPurchaseOrderProduct;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.purchase.repository.ConsumablesPurchaseOrderProductRepository;
import me.zhengjie.modules.wms.purchase.service.ConsumablesPurchaseOrderProductService;
import me.zhengjie.modules.wms.purchase.service.dto.ConsumablesPurchaseOrderProductDTO;
import me.zhengjie.modules.wms.purchase.service.dto.ConsumablesPurchaseOrderProductQueryCriteria;
import me.zhengjie.modules.wms.purchase.service.mapper.ConsumablesPurchaseOrderProductMapper;
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
public class ConsumablesPurchaseOrderProductServiceImpl implements ConsumablesPurchaseOrderProductService {

    @Autowired
    private ConsumablesPurchaseOrderProductRepository consumablesPurchaseOrderProductRepository;

    @Autowired
    private ConsumablesPurchaseOrderProductMapper consumablesPurchaseOrderProductMapper;

    @Override
    public Object queryAll(ConsumablesPurchaseOrderProductQueryCriteria criteria, Pageable pageable){
        Page<ConsumablesPurchaseOrderProduct> page = consumablesPurchaseOrderProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(consumablesPurchaseOrderProductMapper::toDto));
    }

    @Override
    public Object queryAll(ConsumablesPurchaseOrderProductQueryCriteria criteria){
        return consumablesPurchaseOrderProductMapper.toDto(consumablesPurchaseOrderProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ConsumablesPurchaseOrderProductDTO findById(Long id) {
        Optional<ConsumablesPurchaseOrderProduct> consumablesPurchaseOrderProduct = consumablesPurchaseOrderProductRepository.findById(id);
        ValidationUtil.isNull(consumablesPurchaseOrderProduct,"ConsumablesPurchaseOrderProduct","id",id);
        return consumablesPurchaseOrderProductMapper.toDto(consumablesPurchaseOrderProduct.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ConsumablesPurchaseOrderProductDTO create(ConsumablesPurchaseOrderProduct resources) {
        return consumablesPurchaseOrderProductMapper.toDto(consumablesPurchaseOrderProductRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ConsumablesPurchaseOrderProduct resources) {
        Optional<ConsumablesPurchaseOrderProduct> optionalConsumablesPurchaseOrderProduct = consumablesPurchaseOrderProductRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalConsumablesPurchaseOrderProduct,"ConsumablesPurchaseOrderProduct","id",resources.getId());
        ConsumablesPurchaseOrderProduct consumablesPurchaseOrderProduct = optionalConsumablesPurchaseOrderProduct.get();
        consumablesPurchaseOrderProduct.copy(resources);
        consumablesPurchaseOrderProductRepository.save(consumablesPurchaseOrderProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        consumablesPurchaseOrderProductRepository.deleteById(id);
    }
}