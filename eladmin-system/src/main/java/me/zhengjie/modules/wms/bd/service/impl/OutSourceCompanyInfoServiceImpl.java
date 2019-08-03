package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.OutSourceCompanyInfo;
import me.zhengjie.modules.wms.bd.domain.SupplierInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.bd.repository.OutSourceCompanyInfoRepository;
import me.zhengjie.modules.wms.bd.service.OutSourceCompanyInfoService;
import me.zhengjie.modules.wms.bd.service.dto.OutSourceCompanyInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.OutSourceCompanyInfoQueryCriteria;
import me.zhengjie.modules.wms.bd.service.mapper.OutSourceCompanyInfoMapper;
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
public class OutSourceCompanyInfoServiceImpl implements OutSourceCompanyInfoService {

    @Autowired
    private OutSourceCompanyInfoRepository outSourceCompanyInfoRepository;

    @Autowired
    private OutSourceCompanyInfoMapper outSourceCompanyInfoMapper;

    @Override
    public Object queryAll(OutSourceCompanyInfoQueryCriteria criteria, Pageable pageable){
        Specification<OutSourceCompanyInfo> specification = new Specification<OutSourceCompanyInfo>() {
            @Override
            public Predicate toPredicate(Root<OutSourceCompanyInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        Page<OutSourceCompanyInfo> page = outSourceCompanyInfoRepository.findAll(specification, pageable);
        return PageUtil.toPage(page.map(outSourceCompanyInfoMapper::toDto));
    }

    @Override
    public Object queryAll(OutSourceCompanyInfoQueryCriteria criteria){
        Specification<OutSourceCompanyInfo> specification = new Specification<OutSourceCompanyInfo>() {
            @Override
            public Predicate toPredicate(Root<OutSourceCompanyInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        return outSourceCompanyInfoMapper.toDto(outSourceCompanyInfoRepository.findAll(specification));
    }

    @Override
    public OutSourceCompanyInfoDTO findById(Long id) {
        Optional<OutSourceCompanyInfo> bdOutSourceCompanyInfo = outSourceCompanyInfoRepository.findById(id);
        ValidationUtil.isNull(bdOutSourceCompanyInfo,"OutSourceCompanyInfo","id",id);
        return outSourceCompanyInfoMapper.toDto(bdOutSourceCompanyInfo.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutSourceCompanyInfoDTO create(OutSourceCompanyInfo resources) {
        resources.setStatus(true);
        return outSourceCompanyInfoMapper.toDto(outSourceCompanyInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OutSourceCompanyInfo resources) {
        Optional<OutSourceCompanyInfo> optionalBdOutSourceCompanyInfo = outSourceCompanyInfoRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalBdOutSourceCompanyInfo,"BdOutSourceCompanyInfo","id",resources.getId());
        OutSourceCompanyInfo outSourceCompanyInfo = optionalBdOutSourceCompanyInfo.get();
        outSourceCompanyInfo.copy(resources);
        outSourceCompanyInfoRepository.save(outSourceCompanyInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        OutSourceCompanyInfo outSourceCompanyInfo = outSourceCompanyInfoRepository.findByIdAndStatusTrue(id);
        if (null == outSourceCompanyInfo) {
            throw new BadRequestException("委外公司资料不存在!");
        }
        outSourceCompanyInfoRepository.deleteOutSourceCompanyInfo(id);
    }
}