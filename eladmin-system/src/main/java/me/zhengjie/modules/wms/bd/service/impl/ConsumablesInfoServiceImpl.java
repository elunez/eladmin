package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.ConsumablesInfo;
import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.bd.repository.ConsumablesInfoRepository;
import me.zhengjie.modules.wms.bd.service.ConsumablesInfoService;
import me.zhengjie.modules.wms.bd.service.dto.ConsumablesInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.ConsumablesInfoQueryCriteria;
import me.zhengjie.modules.wms.bd.service.mapper.ConsumablesInfoMapper;
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
* @date 2019-10-05
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ConsumablesInfoServiceImpl implements ConsumablesInfoService {

    @Autowired
    private ConsumablesInfoRepository consumablesInfoRepository;

    @Autowired
    private ConsumablesInfoMapper consumablesInfoMapper;

    @Override
    public Object queryAll(ConsumablesInfoQueryCriteria criteria, Pageable pageable){
        Specification<ConsumablesInfo> specification = new Specification<ConsumablesInfo>() {
            @Override
            public Predicate toPredicate(Root<ConsumablesInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                //状态
                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);

                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };

        Page<ConsumablesInfo> page = consumablesInfoRepository.findAll(specification, pageable);
        return PageUtil.toPage(page.map(consumablesInfoMapper::toDto));
    }

    @Override
    public Object queryAll(ConsumablesInfoQueryCriteria criteria){
        return consumablesInfoMapper.toDto(consumablesInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ConsumablesInfoDTO findById(Long id) {
        Optional<ConsumablesInfo> bdConsumablesInfo = consumablesInfoRepository.findById(id);
        ValidationUtil.isNull(bdConsumablesInfo,"BdConsumablesInfo","id",id);
        return consumablesInfoMapper.toDto(bdConsumablesInfo.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ConsumablesInfoDTO create(ConsumablesInfo resources) {

        /**
         * 查看状态正常的情况下该耗材是否存在，如果存在，则提示材已存在
         * 查看删除状态下该名字的材，如果材存在，则修改材状态
         * 否则直接插入新的记录
         */
        ConsumablesInfo byNameAndStatusTrue = consumablesInfoRepository.findByConsumablesNameAndStatusTrue(resources.getConsumablesName());
        if(null != byNameAndStatusTrue){
            throw new BadRequestException("该耗材已经存在");
        }
        ConsumablesInfo byNameAndStatusFalse = consumablesInfoRepository.findByConsumablesNameAndStatusFalse(resources.getConsumablesName());
        if(null != byNameAndStatusFalse){
            resources.setStatus(true);
            consumablesInfoRepository.updateStatusToTrue(byNameAndStatusFalse.getId());
            Optional<ConsumablesInfo> measureUnitOptional = consumablesInfoRepository.findById(byNameAndStatusFalse.getId());
            ConsumablesInfo consumablesInfo = measureUnitOptional.get();
            return consumablesInfoMapper.toDto(consumablesInfo);
        }else{
            resources.getConsumablesName();
            resources.setStatus(true);
            ConsumablesInfo consumablesInfo = consumablesInfoRepository.save(resources);
            return consumablesInfoMapper.toDto(consumablesInfo);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ConsumablesInfo resources) {
        Optional<ConsumablesInfo> optionalBdConsumablesInfo = consumablesInfoRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalBdConsumablesInfo,"BdConsumablesInfo","id",resources.getId());
        ConsumablesInfo bdConsumablesInfo = optionalBdConsumablesInfo.get();
        bdConsumablesInfo.copy(resources);
        consumablesInfoRepository.save(bdConsumablesInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        consumablesInfoRepository.deleteById(id);
    }
}