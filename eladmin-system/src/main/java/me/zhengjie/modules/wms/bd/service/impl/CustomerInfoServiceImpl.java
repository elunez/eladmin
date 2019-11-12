package me.zhengjie.modules.wms.bd.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.CustomerInfo;
import me.zhengjie.modules.wms.bd.domain.ProductInfo;
import me.zhengjie.modules.wms.bd.domain.SupplierCategory;
import me.zhengjie.modules.wms.bd.domain.SupplierInfo;
import me.zhengjie.modules.wms.bd.request.*;
import me.zhengjie.modules.wms.bd.service.dto.*;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.bd.repository.CustomerInfoRepository;
import me.zhengjie.modules.wms.bd.service.CustomerInfoService;
import me.zhengjie.modules.wms.bd.service.mapper.CustomerInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);

                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };
        Page<CustomerInfo> page = customerInfoRepository.findAll(specification, pageable);
        Page<CustomerInfoDTO> customerInfoDTOPage = page.map(customerInfoMapper::toDto);
        if(null != customerInfoDTOPage){
            List<CustomerInfoDTO> customerInfoDtoList = customerInfoDTOPage.getContent();
            if(!CollectionUtils.isEmpty(customerInfoDtoList)){
                for(CustomerInfoDTO customerInfoDTO : customerInfoDtoList){
                    Long customerInfoDTOId = customerInfoDTO.getId();
                    Optional<CustomerInfo> customerInfoOptional = customerInfoRepository.findById(customerInfoDTOId);
                    if(null != customerInfoOptional){
                        CustomerInfo customerInfo = customerInfoOptional.get();
                        if(null != customerInfo){
                            String customerContactJsonStr = customerInfo.getCustomerContact();
                            List<CustomerContact> customerContactList = new Gson().fromJson(customerContactJsonStr,new TypeToken<ArrayList<CustomerContact>>() {}.getType());
                            if(!CollectionUtils.isEmpty(customerContactList)){
                                for(CustomerContact customerContact : customerContactList){
                                    if(!StringUtils.isEmpty(customerContact.getFirstTag()) && customerContact.getFirstTag() == 1){
                                        customerInfoDTO.setFirstContactMobile(customerContact.getMobile());
                                        customerInfoDTO.setFirstContactName(customerContact.getName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return PageUtil.toPage(customerInfoDTOPage);
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
    public CustomerInfoDetailDTO findById(Long id) {
        CustomerInfoDetailDTO customerInfoDetailDTO = new CustomerInfoDetailDTO();

        Optional<CustomerInfo> customerInfoOptional = customerInfoRepository.findById(id);
        CustomerInfo customerInfo = customerInfoOptional.get();
        CustomerInfoDTO customerInfoDTO = customerInfoMapper.toDto(customerInfo);
        if(null != customerInfoDTO){
            BeanUtils.copyProperties( customerInfoDTO, customerInfoDetailDTO);
            String customerAddressJsonStr = customerInfo.getCustomerAddress();
            if(StringUtils.hasLength(customerAddressJsonStr)){
                List<CustomerAddress> customerAddressList = new Gson().fromJson(customerAddressJsonStr,new TypeToken<ArrayList<SupplierAddress>>() {}.getType());
                customerInfoDetailDTO.setCustomerAddress(customerAddressList);
            }


            String customerContactJsonStr = customerInfo.getCustomerContact();
            if(StringUtils.hasLength(customerContactJsonStr)){
                List<CustomerContact> customerContactList = new Gson().fromJson(customerContactJsonStr,new TypeToken<ArrayList<SupplierContact>>() {}.getType());
                customerInfoDetailDTO.setCustomerContact(customerContactList);
            }
        }
        return customerInfoDetailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerInfoDetailDTO create(CreateCustomerInfoRequest createCustomerInfoRequest) {
        CustomerInfoDetailDTO customerInfoDetailDTO = new CustomerInfoDetailDTO();

        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(createCustomerInfoRequest, customerInfo);
        customerInfo.setStatus(true);
        List<CustomerAddress> customerAddressList = createCustomerInfoRequest.getCustomerAddress();
        if(!CollectionUtils.isEmpty(customerAddressList)){
            String customerAddressStr = new Gson().toJson(customerAddressList);
            customerInfo.setCustomerAddress(customerAddressStr);
            customerInfoDetailDTO.setCustomerAddress(customerAddressList);
        }
        List<CustomerContact> customerContactList = createCustomerInfoRequest.getCustomerContact();
        if(!CollectionUtils.isEmpty(customerContactList)){
            String customerContactStr = new Gson().toJson(customerContactList);
            customerInfo.setCustomerContact(customerContactStr);
            customerInfoDetailDTO.setCustomerContact(customerContactList);
        }

        customerInfo = customerInfoRepository.save(customerInfo);
        CustomerInfoDTO customerInfoDTO = customerInfoMapper.toDto(customerInfo);
        BeanUtils.copyProperties(customerInfoDTO, customerInfoDetailDTO);
        return customerInfoDetailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateCustomerInfoRequest updateCustomerInfoRequest) {
        Long customerInfoId = updateCustomerInfoRequest.getId();
        if(null == customerInfoId){
            throw new BadRequestException("客户主键不能为空!");
        }

        // 客户资料-客户联系地址修改目标
        List<CustomerAddress> customerAddressListUpdateTarget = updateCustomerInfoRequest.getCustomerAddress();
        // 供应商资料-供应商联系方式修改目标
        List<CustomerContact> customerContactListUpdateTarget = updateCustomerInfoRequest.getCustomerContact();

        CustomerInfo customerInfo = customerInfoRepository.findByIdAndStatusTrue(customerInfoId);

        if(null == customerInfo){
            throw new BadRequestException("客户不存在");
        }

        Timestamp createTime = customerInfo.getCreateTime();

        // 将需要修改的值复制到数据库对象中
        BeanUtils.copyProperties(updateCustomerInfoRequest, customerInfo);

        // 判断提前获取的供应商联系地址和联系方式是否是空
        if(CollectionUtils.isEmpty(customerAddressListUpdateTarget)){
            customerInfo.setCustomerAddress(null);
        }else{
            String supplierAddressStr = new Gson().toJson(customerAddressListUpdateTarget);
            customerInfo.setCustomerContact(supplierAddressStr);
        }

        if(CollectionUtils.isEmpty(customerContactListUpdateTarget)){
            customerInfo.setCustomerContact(null);
        }else{
            String supplierContactStr = new Gson().toJson(customerContactListUpdateTarget);
            customerInfo.setCustomerContact(supplierContactStr);
        }

        customerInfo.setCreateTime(createTime);
        customerInfo.setStatus(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        customerInfo.setUpdateTime(Timestamp.valueOf(sdf.format(new Date())));
        // 修改客户资料
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