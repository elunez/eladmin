package me.zhengjie.modules.wms.order.service.impl;

import me.zhengjie.modules.wms.order.domain.CustomerOrder;
import me.zhengjie.modules.wms.order.repository.CustomerOrderRepository;
import me.zhengjie.modules.wms.order.service.CustomerOrderService;
import me.zhengjie.modules.wms.order.service.dto.CustomerOrderDTO;
import me.zhengjie.modules.wms.order.service.dto.CustomerOrderQueryCriteria;
import me.zhengjie.modules.wms.order.service.mapper.CustomerOrderMapper;
import me.zhengjie.utils.ValidationUtil;
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
* @date 2019-08-03
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CustomerOrderServiceImpl implements CustomerOrderService {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private CustomerOrderMapper customerOrderMapper;

    @Override
    public Object queryAll(CustomerOrderQueryCriteria criteria, Pageable pageable){
        Page<CustomerOrder> page = customerOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(customerOrderMapper::toDto));
    }

    @Override
    public Object queryAll(CustomerOrderQueryCriteria criteria){
        return customerOrderMapper.toDto(customerOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public CustomerOrderDTO findById(Long id) {
        Optional<CustomerOrder> sCustomerOrder = customerOrderRepository.findById(id);
        ValidationUtil.isNull(sCustomerOrder,"SCustomerOrder","id",id);
        return customerOrderMapper.toDto(sCustomerOrder.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerOrderDTO create(CustomerOrder resources) {
        resources.setStatus(true);
        return customerOrderMapper.toDto(customerOrderRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CustomerOrder resources) {
        Optional<CustomerOrder> optionalSCustomerOrder = customerOrderRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalSCustomerOrder,"SCustomerOrder","id",resources.getId());
        CustomerOrder customerOrder = optionalSCustomerOrder.get();
        customerOrder.copy(resources);
        customerOrderRepository.save(customerOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        customerOrderRepository.deleteCustomerOrder(id);
    }
}