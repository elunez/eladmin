package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.MaterialCategory;
import me.zhengjie.modules.wms.bd.domain.MaterialInfo;
import me.zhengjie.modules.wms.bd.domain.MeasureUnit;
import me.zhengjie.modules.wms.bd.repository.MaterialCategoryRepository;
import me.zhengjie.modules.wms.bd.repository.MeasureUnitRepository;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.bd.repository.MaterialInfoRepository;
import me.zhengjie.modules.wms.bd.service.MaterialInfoService;
import me.zhengjie.modules.wms.bd.service.dto.MaterialInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.MaterialInfoQueryCriteria;
import me.zhengjie.modules.wms.bd.service.mapper.MaterialInfoMapper;
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
 * @author 黄星星
 * @date 2019-07-27
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MaterialInfoServiceImpl implements MaterialInfoService {

    @Autowired
    private MaterialInfoRepository materialInfoRepository;

    @Autowired
    private MaterialInfoMapper materialInfoMapper;

    @Autowired
    private MaterialCategoryRepository materialCategoryRepository;

    @Autowired
    private MeasureUnitRepository measureUnitRepository;

    @Override
    public Object queryAll(MaterialInfoQueryCriteria criteria, Pageable pageable) {
        Specification<MaterialInfo> specification = new Specification<MaterialInfo>() {
            @Override
            public Predicate toPredicate(Root<MaterialInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                List<Predicate> nameOrMaterialCodeList = new ArrayList<>();
                String name = criteria.getName();
                if (!StringUtils.isEmpty(name)) {
                    Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                    nameOrMaterialCodeList.add(namePredicate);
                }

                String materialCode = criteria.getMaterialCode();
                if (!StringUtils.isEmpty(materialCode)) {
                    Predicate materialCodePredicate = criteriaBuilder.like(root.get("materialCode"), "%" + materialCode + "%");
                    nameOrMaterialCodeList.add(materialCodePredicate);
                }

                //name与materialCode组装成
                Predicate nameOrMaterialCodePredicate = null;
                if (!CollectionUtils.isEmpty(nameOrMaterialCodeList)) {
                    nameOrMaterialCodePredicate = criteriaBuilder.or(nameOrMaterialCodeList.toArray(new Predicate[nameOrMaterialCodeList.size()]));
                    targetPredicateList.add(nameOrMaterialCodePredicate);
                }



                Predicate materialCategoryIdPredicate = null;
                Long materialCategoryId = criteria.getMaterialCategoryId();
                if (null != materialCategoryId) {
                    materialCategoryIdPredicate = criteriaBuilder.equal(root.get("materialCategoryId"), materialCategoryId);
                    targetPredicateList.add(materialCategoryIdPredicate);
                }


                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };

        Page<MaterialInfo> page = materialInfoRepository.findAll(specification, pageable);
        return PageUtil.toPage(page.map(materialInfoMapper::toDto));
    }

    @Override
    public Object queryAll(MaterialInfoQueryCriteria criteria) {
        return materialInfoMapper.toDto(materialInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public MaterialInfoDTO findById(Long id) {
        Optional<MaterialInfo> bdMaterialInfo = materialInfoRepository.findById(id);
        ValidationUtil.isNull(bdMaterialInfo, "BdMaterialInfo", "id", id);
        return materialInfoMapper.toDto(bdMaterialInfo.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialInfoDTO create(MaterialInfo resources) {
        Long materialCategoryId = resources.getMaterialCategoryId();
        String materialCategoryName = resources.getMaterialCategoryName();
        if (null == materialCategoryId || StringUtils.isEmpty(materialCategoryName)) {
            throw new BadRequestException("物料类别不能为空!");
        }

        MaterialCategory materialCategory = materialCategoryRepository.findByIdAndStatusTrue(materialCategoryId);
        if (null == materialCategory) {
            throw new BadRequestException("物料类别不存在!");
        }

        Long measureUnitId = resources.getMeasureUnitId();
        String measureUnitName = resources.getMeasureUnitName();
        if (null == measureUnitId || StringUtils.isEmpty(measureUnitName)) {
            throw new BadRequestException("计量单位不能为空!");
        }

        MeasureUnit measureUnit = measureUnitRepository.findByIdAndStatusTrue(measureUnitId);
        if (null == measureUnit) {
            throw new BadRequestException("计量单位不存在!");
        }

        // 物料编码
        String materialCode = resources.getMaterialCode();
        MaterialInfo byMaterialCodeAndStatusTrue = materialInfoRepository.findByMaterialCodeAndStatusTrue(materialCode);
        if (null != byMaterialCodeAndStatusTrue) {
            throw new BadRequestException("物料编码已存在!");
        }
        return materialInfoMapper.toDto(materialInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MaterialInfo resources) {
        Optional<MaterialInfo> optionalBdMaterialInfo = materialInfoRepository.findById(resources.getId());
        ValidationUtil.isNull(optionalBdMaterialInfo, "MaterialInfo", "id", resources.getId());
        MaterialInfo materialInfo = optionalBdMaterialInfo.get();
        materialInfo.copy(resources);
        materialInfoRepository.save(materialInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(long id) {
        MaterialInfo materialInfo = materialInfoRepository.findByIdAndStatusTrue(id);
        if (null == materialInfo) {
            throw new BadRequestException("物料资料不存在!");
        }
        materialInfoRepository.deleteMaterialInfo(id);
    }
}