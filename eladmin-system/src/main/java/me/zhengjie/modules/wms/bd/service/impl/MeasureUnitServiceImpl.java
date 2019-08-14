package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.CustomerInfo;
import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.bd.repository.MeasureUnitRepository;
import me.zhengjie.modules.wms.bd.service.MeasureUnitService;
import me.zhengjie.modules.wms.bd.service.dto.MeasureUnitDTO;
import me.zhengjie.modules.wms.bd.service.mapper.MeasureUnitMapper;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 黄星星
 * @date 2019-07-27
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeasureUnitServiceImpl implements MeasureUnitService {

    @Autowired
    private MeasureUnitMapper measureUnitMapper;

    @Autowired
    private MeasureUnitRepository measureUnitRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeasureUnitDTO create(MeasureUnit resources) {
        /**
         * 查看状态正常的情况下该计量单位是否存在，如果存在，则提示计量单位已存在
         * 查看删除状态下该名字的计量单位，如果计量单位存在，则修改计量单位状态
         * 否则直接插入新的记录
         */
        MeasureUnit byNameAndStatusTrue = measureUnitRepository.findByNameAndStatusTrue(resources.getName());
        if(null != byNameAndStatusTrue){
            throw new BadRequestException("该计量单位已经存在");
        }
        MeasureUnit byNameAndStatusFalse = measureUnitRepository.findByNameAndStatusFalse(resources.getName());
        if(null != byNameAndStatusFalse){
            resources.setStatus(true);
            measureUnitRepository.updateStatusToTrue(byNameAndStatusFalse.getId());
            Optional<MeasureUnit> measureUnitOptional = measureUnitRepository.findById(byNameAndStatusFalse.getId());
            MeasureUnit measureUnit = measureUnitOptional.get();
            return measureUnitMapper.toDto(measureUnit);
        }else{
            resources.getName();
            resources.setStatus(true);
            MeasureUnit measureUnit = measureUnitRepository.save(resources);
            return measureUnitMapper.toDto(measureUnit);
        }
    }

    @Override
    public MeasureUnitDTO updateMeasureUnit(MeasureUnit measureUnit) {
        /**
         * 修改的名称不能为空
         * 查看修改后的name对应的状态为true的计量单位是否存在，如果存在，则提示计量单位已存在
         * 修改计量单位名称
         * 备注：
         * 如果修改后的name对应的状态为false的计量单位存在，则会有冗余数据
         */
        String name = measureUnit.getName();
        if(StringUtils.isEmpty(name)){
            throw new BadRequestException("计量单位名称不能为空");
        }

        MeasureUnit byNameAndStatusTrue = measureUnitRepository.findByNameAndStatusTrue(name);
        if(null != byNameAndStatusTrue){
            throw new BadRequestException("计量单位已存在");
        }

        measureUnitRepository.updateNameById(measureUnit.getName(), measureUnit.getId());

        Optional<MeasureUnit> measureUnitOptional = measureUnitRepository.findById(measureUnit.getId());
        return measureUnitMapper.toDto(measureUnitOptional.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MeasureUnitDTO findById(long id) {
        Optional<MeasureUnit> measureUnit = measureUnitRepository.findById(id);
        ValidationUtil.isNull(measureUnit,"MeasureUnit","id",id);
        return measureUnitMapper.toDto(measureUnit.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        //逻辑删除
        measureUnitRepository.deleteMeasureUnit(id);
    }

    @Override
    public Object queryAll(MeasureUnitDTO measureUnit, Pageable pageable) {
        Specification<MeasureUnit> specification = new Specification<MeasureUnit>() {
            @Override
            public Predicate toPredicate(Root<MeasureUnit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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

        Page<MeasureUnit> page = measureUnitRepository.findAll(specification, pageable);
        return PageUtil.toPage(page.map(measureUnitMapper::toDto));
    }

}
