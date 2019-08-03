package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.CustomerInfo;
import me.zhengjie.modules.wms.bd.domain.SupplierInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.bd.repository.SupplierInfoRepository;
import me.zhengjie.modules.wms.bd.service.SupplierInfoService;
import me.zhengjie.modules.wms.bd.service.dto.SupplierInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.SupplierInfoQueryCriteria;
import me.zhengjie.modules.wms.bd.service.mapper.SupplierInfoMapper;
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
        return PageUtil.toPage(page.map(supplierInfoMapper::toDto));
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
    public SupplierInfoDTO findById(Long id) {
        Optional<SupplierInfo> bdSupplierInfo = supplierInfoRepository.findById(id);
        ValidationUtil.isNull(bdSupplierInfo,"BdSupplierInfo","id",id);
        return supplierInfoMapper.toDto(bdSupplierInfo.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SupplierInfoDTO create(SupplierInfo resources) {
        resources.setStatus(true);
        SupplierInfo supplierInfo = supplierInfoRepository.save(resources);
        supplierInfoMapper.toDto(supplierInfo);
        return supplierInfoMapper.toDto(supplierInfo);
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