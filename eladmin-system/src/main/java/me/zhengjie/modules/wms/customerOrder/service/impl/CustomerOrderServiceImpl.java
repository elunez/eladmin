package me.zhengjie.modules.wms.customerOrder.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.customerOrder.domain.CustomerOrderProduct;
import me.zhengjie.modules.wms.customerOrder.repository.CustomerOrderRepository;
import me.zhengjie.modules.wms.customerOrder.request.UpdateCustomerOrderRequest;
import me.zhengjie.modules.wms.customerOrder.service.CustomerOrderService;
import me.zhengjie.modules.wms.customerOrder.service.dto.CustomerOrderProductDTO;
import me.zhengjie.modules.wms.customerOrder.service.dto.CustomerOrderQueryCriteria;
import me.zhengjie.modules.wms.customerOrder.service.mapper.CustomerOrderProductMapper;
import me.zhengjie.modules.wms.customerOrder.domain.CustomerOrder;
import me.zhengjie.modules.wms.customerOrder.repository.CustomerOrderProductRepository;
import me.zhengjie.modules.wms.customerOrder.request.CreateCustomerOrderRequest;
import me.zhengjie.modules.wms.customerOrder.request.CustomerOrderProductRequest;
import me.zhengjie.modules.wms.customerOrder.service.dto.CustomerOrderDTO;
import me.zhengjie.modules.wms.customerOrder.service.mapper.CustomerOrderMapper;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public void update(UpdateCustomerOrderRequest updateCustomerOrderRequest) {

        CustomerOrder customerOrder = new CustomerOrder();
        BeanUtils.copyProperties(updateCustomerOrderRequest, customerOrder);
        // 修改客户订单概要信息
        customerOrderRepository.save(customerOrder);

        // 修改产品信息之前，查询该订单中原来的产品信息，key为产品code
        List<CustomerOrderProduct> customerOrderProductListBeforeUpdate = customerOrderProductRepository.findByCustomerOrderIdAndStatusTrue(customerOrder.getId());
        Map<String, CustomerOrderProduct> customerOrderProductMapBefore = customerOrderProductListBeforeUpdate.stream().collect(Collectors.toMap(CustomerOrderProduct::getProductCode, Function.identity()));

        List<CustomerOrderProductDTO> customerOrderProductRequestList = updateCustomerOrderRequest.getCustomerOrderProductList();
        if(CollectionUtils.isEmpty(customerOrderProductRequestList)){
            throw new BadRequestException("订单产品不能为空!");
        }

        Map<String, CustomerOrderProductDTO> customerOrderProductMapAfter = customerOrderProductRequestList.stream().collect(Collectors.toMap(CustomerOrderProductDTO::getProductCode, Function.identity()));

        //需要将订单中原来订单对应的产品删除了的数据
        List<String> deleteTargetList = new ArrayList<>();
        //比较量个map中，key不一样的数据
        for(Map.Entry<String, CustomerOrderProduct> entry:customerOrderProductMapBefore.entrySet()){
            String productCode = entry.getKey();
            //修改后的map记录对应的key在原来中是否存在
            CustomerOrderProductDTO customerOrderProductDTOTemp = customerOrderProductMapAfter.get(productCode);
            if(null == customerOrderProductDTOTemp){
                deleteTargetList.add(entry.getKey());
            }

        }


        List<CustomerOrderProduct> customerOrderProductList = new ArrayList<>();
        for(CustomerOrderProductDTO customerOrderProductDTO : customerOrderProductRequestList){
            CustomerOrderProduct customerOrderProduct = new CustomerOrderProduct();
            BeanUtils.copyProperties(customerOrderProductDTO, customerOrderProduct);
            customerOrderProduct.setCustomerOrderId(customerOrder.getId());
            customerOrderProduct.setStatus(true);
        }
        customerOrderProductRepository.saveAll(customerOrderProductList);

        /**
         * 场景描述:
         * 1.刚开始新增了 a b c三种产品
         * 2.修改的时候删除了 a c两种产品
         * 3.所以需要查修改前数据库中有的产品，再比较修改传过来的产品数据，如果修改后的在原来里面没有，需要将原来里面对应的删除
         */
        if(!CollectionUtils.isEmpty(deleteTargetList)){
            for(String prductCode : deleteTargetList){
                customerOrderProductRepository.deleteByProductCodeAndCustomerOrderId(prductCode, customerOrder.getId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        customerOrderRepository.deleteCustomerOrder(id);
    }
}