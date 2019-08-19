package me.zhengjie.modules.wms.bd.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.CustomerInfo;
import me.zhengjie.modules.wms.bd.domain.SupplierCategory;
import me.zhengjie.modules.wms.bd.domain.SupplierInfo;
import me.zhengjie.modules.wms.bd.repository.SupplierCategoryRepository;
import me.zhengjie.modules.wms.bd.request.CreateSupplierInfoRequest;
import me.zhengjie.modules.wms.bd.request.SupplierAddress;
import me.zhengjie.modules.wms.bd.request.SupplierContact;
import me.zhengjie.modules.wms.bd.service.dto.SupplierInfoDetailDTO;
import me.zhengjie.modules.wms.bd.service.mapper.SupplierCategoryMapper;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.bd.repository.SupplierInfoRepository;
import me.zhengjie.modules.wms.bd.service.SupplierInfoService;
import me.zhengjie.modules.wms.bd.service.dto.SupplierInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.SupplierInfoQueryCriteria;
import me.zhengjie.modules.wms.bd.service.mapper.SupplierInfoMapper;
import org.springframework.beans.BeanUtils;
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
public class SupplierInfoServiceImpl implements SupplierInfoService {

    @Autowired
    private SupplierInfoRepository supplierInfoRepository;

    @Autowired
    private SupplierInfoMapper supplierInfoMapper;

    @Autowired
    private SupplierCategoryMapper supplierCategoryMapper;

    @Autowired
    private SupplierCategoryRepository supplierCategoryRepository;

    @Override
    public Object queryAll(SupplierInfoQueryCriteria criteria, Pageable pageable){
        Specification<SupplierInfo> specification = new Specification<SupplierInfo>() {
            @Override
            public Predicate toPredicate(Root<SupplierInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        Page<SupplierInfo> page = supplierInfoRepository.findAll(specification, pageable);
        Page<SupplierInfoDTO> supplierInfoDTOPage = page.map(supplierInfoMapper::toDto);
        if(null != supplierInfoDTOPage){
            List<SupplierInfoDTO> supplierInfoDtoList = supplierInfoDTOPage.getContent();
            if(!CollectionUtils.isEmpty(supplierInfoDtoList)){
                for(SupplierInfoDTO supplierInfoDTO : supplierInfoDtoList){
                    Long supplierInfoDTOId = supplierInfoDTO.getId();
                    Optional<SupplierInfo> supplierInfoOptional = supplierInfoRepository.findById(supplierInfoDTOId);
                    if(null != supplierInfoOptional){
                        SupplierInfo supplierInfo = supplierInfoOptional.get();
                        if(null != supplierInfo){
                            String supplierContactJsonStr = supplierInfo.getSupplierContact();
                            List<SupplierContact> supplierContactList = new Gson().fromJson(supplierContactJsonStr,new TypeToken<ArrayList<SupplierContact>>() {}.getType());
                            if(!CollectionUtils.isEmpty(supplierContactList)){
                                for(SupplierContact supplierContact : supplierContactList){
                                    if(supplierContact.getFirstTag() == 1){
                                        supplierInfoDTO.setFirstContactMobile(supplierContact.getMobile());
                                        supplierInfoDTO.setFirstContactName(supplierContact.getName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return PageUtil.toPage(supplierInfoDTOPage);
    }

    @Override
    public Object queryAll(SupplierInfoQueryCriteria criteria){
        Specification<SupplierInfo> specification = new Specification<SupplierInfo>() {
            @Override
            public Predicate toPredicate(Root<SupplierInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        return supplierInfoMapper.toDto(supplierInfoRepository.findAll(specification));
    }

    @Override
    public SupplierInfoDetailDTO findById(Long id) {
        SupplierInfoDetailDTO supplierInfoDetailDTO = new SupplierInfoDetailDTO();

        Optional<SupplierInfo> bdSupplierInfo = supplierInfoRepository.findById(id);
        SupplierInfo supplierInfo = bdSupplierInfo.get();
        SupplierInfoDTO supplierInfoDTO = supplierInfoMapper.toDto(supplierInfo);
        if(null != supplierInfoDTO){
            BeanUtils.copyProperties( supplierInfoDTO, supplierInfoDetailDTO);
            String supplierAddressJsonStr = supplierInfo.getSupplierAddress();
            if(StringUtils.hasLength(supplierAddressJsonStr)){
                List<SupplierAddress> supplierAddressList = new Gson().fromJson(supplierAddressJsonStr,new TypeToken<ArrayList<SupplierAddress>>() {}.getType());
                supplierInfoDetailDTO.setSupplierAddress(supplierAddressList);
            }


            String supplierContactJsonStr = supplierInfo.getSupplierContact();
            if(StringUtils.hasLength(supplierContactJsonStr)){
                List<SupplierContact> supplierContactList = new Gson().fromJson(supplierContactJsonStr,new TypeToken<ArrayList<SupplierContact>>() {}.getType());
                supplierInfoDetailDTO.setSupplierContact(supplierContactList);
            }
        }
        return supplierInfoDetailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SupplierInfoDTO create(CreateSupplierInfoRequest createSupplierInfoRequest) {
        Long supplierCategoryId = createSupplierInfoRequest.getSupplierCategoryId();
        if(null == supplierCategoryId){
            throw new BadRequestException("供应商类别不存在!");
        }
        Optional<SupplierCategory> supplierCategoryOptional = supplierCategoryRepository.findById(supplierCategoryId);
        SupplierCategory supplierCategory = supplierCategoryOptional.get();

        SupplierInfoDetailDTO supplierInfoDetailDTO = new SupplierInfoDetailDTO();

        SupplierInfo supplierInfo = new SupplierInfo();
        BeanUtils.copyProperties(createSupplierInfoRequest, supplierInfo);
        supplierInfo.setStatus(true);
        List<SupplierAddress> supplierAddressList = createSupplierInfoRequest.getSupplierAddress();
        if(!CollectionUtils.isEmpty(supplierAddressList)){
            String supplierAddressStr = new Gson().toJson(supplierAddressList);
            supplierInfo.setSupplierAddress(supplierAddressStr);
            supplierInfoDetailDTO.setSupplierAddress(supplierAddressList);
        }
        List<SupplierContact> supplierContactList = createSupplierInfoRequest.getSupplierContact();
        if(!CollectionUtils.isEmpty(supplierContactList)){
            String supplierContactStr = new Gson().toJson(supplierContactList);
            supplierInfo.setSupplierContact(supplierContactStr);
            supplierInfoDetailDTO.setSupplierContact(supplierContactList);
        }

        supplierInfo.setSupplierCategoryName(supplierCategory.getName());

        supplierInfo = supplierInfoRepository.save(supplierInfo);
        SupplierInfoDTO supplierInfoDTO = supplierInfoMapper.toDto(supplierInfo);
        BeanUtils.copyProperties(supplierInfoDTO, supplierInfoDetailDTO);
        return supplierInfoDetailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SupplierInfo resources) {
        Optional<SupplierInfo> optionalBdSupplierInfo = supplierInfoRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalBdSupplierInfo,"BdSupplierInfo","id",resources.getId());
        SupplierInfo supplierInfo = optionalBdSupplierInfo.get();
        supplierInfo.copy(resources);
        supplierInfoRepository.save(supplierInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        SupplierInfo supplierInfo = supplierInfoRepository.findByIdAndStatusTrue(id);
        if (null == supplierInfo) {
            throw new BadRequestException("供应商资料不存在!");
        }
        supplierInfoRepository.deleteSupplierInfo(id);
    }
}