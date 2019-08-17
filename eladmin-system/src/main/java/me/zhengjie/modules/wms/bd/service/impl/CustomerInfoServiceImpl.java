package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.CustomerInfo;
import me.zhengjie.modules.wms.bd.domain.ProductInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.bd.repository.CustomerInfoRepository;
import me.zhengjie.modules.wms.bd.service.CustomerInfoService;
import me.zhengjie.modules.wms.bd.service.dto.CustomerInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.CustomerInfoQueryCriteria;
import me.zhengjie.modules.wms.bd.service.mapper.CustomerInfoMapper;
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
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
* @author jie
* @date 2019-08-03
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CustomerInfoServiceImpl implements CustomerInfoService {

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Override
    public Object queryAll(CustomerInfoQueryCriteria criteria, Pageable pageable){

        Specification<CustomerInfo> specification = new Specification<CustomerInfo>() {
            @Override
            public Predicate toPredicate(Root<CustomerInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                //客户名称
                String customerName = criteria.getCustomerName();
                if (!StringUtils.isEmpty(customerName)) {
                    Predicate namePredicate = criteriaBuilder.like(root.get("customerName"), "%" + customerName + "%");
                    targetPredicateList.add(namePredicate);
                }

                //状态
                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);

                String customerCode = criteria.getCustomerCode();
                if(!StringUtils.isEmpty(customerCode)){
                    Predicate customerCodePredicate = criteriaBuilder.like(root.get("customerCode"), "%" + customerName + "%");
                    targetPredicateList.add(customerCodePredicate);
                }

                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };
        Page<CustomerInfo> page = customerInfoRepository.findAll(specification, pageable);
        return PageUtil.toPage(page.map(customerInfoMapper::toDto));
    }

    @Override
    public Object queryAll(CustomerInfoQueryCriteria criteria){
        Specification<CustomerInfo> specification = new Specification<CustomerInfo>() {
            @Override
            public Predicate toPredicate(Root<CustomerInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);

                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };
        List<CustomerInfo> productInfoList = customerInfoRepository.findAll(specification);
        return customerInfoMapper.toDto(productInfoList);
    }

    @Override
    public CustomerInfoDTO findById(Long id) {
        Optional<CustomerInfo> customerInfoOptional = customerInfoRepository.findById(id);
        ValidationUtil.isNull(customerInfoOptional,"customerInfo","id",id);
        CustomerInfo customerInfo = customerInfoOptional.get();
        customerInfoMapper.toDto(customerInfo);
        return customerInfoMapper.toDto(customerInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerInfoDTO create(CustomerInfo resources) {
        resources.setStatus(true);
        CustomerInfo customerInfo = customerInfoRepository.save(resources);
        CustomerInfoDTO customerInfoDTO = customerInfoMapper.toDto(customerInfo);
        return customerInfoDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CustomerInfo resources) {
        Optional<CustomerInfo> optionalBdCustomerInfo = customerInfoRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalBdCustomerInfo,"BdCustomerInfo","id",resources.getId());
        CustomerInfo customerInfo = optionalBdCustomerInfo.get();
        customerInfo.copy(resources);
        customerInfoRepository.save(customerInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        CustomerInfo customerInfo = customerInfoRepository.findByIdAndStatusTrue(id);
        if (null == customerInfo) {
            throw new BadRequestException("客户信息不存在!");
        }
        customerInfoRepository.deleteCustomerInfo(id);
    }
}