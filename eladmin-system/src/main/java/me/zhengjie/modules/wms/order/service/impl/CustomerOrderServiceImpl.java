package me.zhengjie.modules.wms.order.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.order.domain.CustomerOrder;
import me.zhengjie.modules.wms.order.domain.CustomerOrderProduct;
import me.zhengjie.modules.wms.order.repository.CustomerOrderProductRepository;
import me.zhengjie.modules.wms.order.repository.CustomerOrderRepository;
import me.zhengjie.modules.wms.order.request.CreateCustomerOrderRequest;
import me.zhengjie.modules.wms.order.request.CustomerOrderProductRequest;
import me.zhengjie.modules.wms.order.service.CustomerOrderService;
import me.zhengjie.modules.wms.order.service.dto.CustomerOrderDTO;
import me.zhengjie.modules.wms.order.service.dto.CustomerOrderProductDTO;
import me.zhengjie.modules.wms.order.service.dto.CustomerOrderQueryCriteria;
import me.zhengjie.modules.wms.order.service.mapper.CustomerOrderMapper;
import me.zhengjie.modules.wms.order.service.mapper.CustomerOrderProductMapper;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CustomerOrderProductRepository customerOrderProductRepository;

    @Autowired
    private CustomerOrderMapper customerOrderMapper;

    @Autowired
    private CustomerOrderProductMapper customerOrderProductMapper;

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
    public CustomerOrderDTO create(CreateCustomerOrderRequest createCustomerOrderRequest) {
        CustomerOrder customerOrder = new CustomerOrder();
        BeanUtils.copyProperties(createCustomerOrderRequest, customerOrder);
        customerOrder.setStatus(true);
        //插入客户订单
        customerOrderRepository.save(customerOrder);
        customerOrder= customerOrderRepository.findByCustomerOrderCodeAndStatusTrue(createCustomerOrderRequest.getCustomerOrderCode());

        //插入客户订单对应的产品信息
        List<CustomerOrderProductRequest> customerOrderProductRequestList = createCustomerOrderRequest.getCustomerOrderProductList();
        if(CollectionUtils.isEmpty(customerOrderProductRequestList)){
            throw new BadRequestException("订单产品不能为空!");
        }

        List<CustomerOrderProduct> customerOrderProductList = new ArrayList<>();
        for(CustomerOrderProductRequest customerOrderProductRequest : customerOrderProductRequestList){
            CustomerOrderProduct customerOrderProduct = new CustomerOrderProduct();
            BeanUtils.copyProperties(customerOrderProductRequest, customerOrderProduct);
            customerOrderProduct.setCustomerOrderId(customerOrder.getId());
            customerOrderProduct.setStatus(true);
        }

        customerOrderProductRepository.saveAll(customerOrderProductList);


        CustomerOrderDTO customerOrderDTO = customerOrderMapper.toDto(customerOrder);
        List<CustomerOrderProduct> byCustomerOrderIdAndStatusTrue = customerOrderProductRepository.findByCustomerOrderIdAndStatusTrue(customerOrder.getId());
        List<CustomerOrderProductDTO> customerOrderProductDTOList = customerOrderProductMapper.toDto(byCustomerOrderIdAndStatusTrue);
        customerOrderDTO.setCustomerOrderProductList(customerOrderProductDTOList);


        return customerOrderDTO;
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