package me.zhengjie.modules.wms.customerOrder.service.impl;

import me.zhengjie.modules.wms.customerOrder.domain.CustomerOrderProduct;
import me.zhengjie.modules.wms.customerOrder.service.dto.CustomerOrderProductQueryCriteria;
import me.zhengjie.modules.wms.customerOrder.service.mapper.CustomerOrderProductMapper;
import me.zhengjie.modules.wms.customerOrder.repository.CustomerOrderProductRepository;
import me.zhengjie.modules.wms.customerOrder.service.CustomerOrderProductService;
import me.zhengjie.modules.wms.customerOrder.service.dto.CustomerOrderProductDTO;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
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
public class CustomerOrderProductServiceImpl implements CustomerOrderProductService {

    @Autowired
    private CustomerOrderProductRepository customerOrderProductRepository;

    @Autowired
    private CustomerOrderProductMapper customerOrderProductMapper;

    @Override
    public Object queryAll(CustomerOrderProductQueryCriteria criteria, Pageable pageable){
        Page<CustomerOrderProduct> page = customerOrderProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(customerOrderProductMapper::toDto));
    }

    @Override
    public Object queryAll(CustomerOrderProductQueryCriteria criteria){
        return customerOrderProductMapper.toDto(customerOrderProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public CustomerOrderProductDTO findById(Long id) {
        Optional<CustomerOrderProduct> sCustomerOrderProduct = customerOrderProductRepository.findById(id);
        ValidationUtil.isNull(sCustomerOrderProduct,"SCustomerOrderProduct","id",id);
        return customerOrderProductMapper.toDto(sCustomerOrderProduct.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerOrderProductDTO create(CustomerOrderProduct resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId()); 
        return customerOrderProductMapper.toDto(customerOrderProductRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CustomerOrderProduct resources) {
        Optional<CustomerOrderProduct> optionalSCustomerOrderProduct = customerOrderProductRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalSCustomerOrderProduct,"SCustomerOrderProduct","id",resources.getId());
        CustomerOrderProduct customerOrderProduct = optionalSCustomerOrderProduct.get();
        customerOrderProduct.copy(resources);
        customerOrderProductRepository.save(customerOrderProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        customerOrderProductRepository.deleteById(id);
    }
}