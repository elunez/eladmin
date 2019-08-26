package me.zhengjie.modules.wms.bd.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.*;
import me.zhengjie.modules.wms.bd.repository.MaterialCategoryRepository;
import me.zhengjie.modules.wms.bd.repository.MeasureUnitRepository;
import me.zhengjie.modules.wms.bd.request.*;
import me.zhengjie.modules.wms.bd.service.dto.*;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.bd.repository.MaterialInfoRepository;
import me.zhengjie.modules.wms.bd.service.MaterialInfoService;
import me.zhengjie.modules.wms.bd.service.mapper.MaterialInfoMapper;
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

import javax.persistence.criteria.*;

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

                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);

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
        Specification<MaterialInfo> specification = new Specification<MaterialInfo>() {
            @Override
            public Predicate toPredicate(Root<MaterialInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        List<MaterialInfo> materialInfoList = materialInfoRepository.findAll(specification);
        return materialInfoMapper.toDto(materialInfoList);
    }

    @Override
    public MaterialInfoDetailDTO findById(Long id) {
        MaterialInfoDetailDTO materialInfoDetailDTO = new MaterialInfoDetailDTO();

        Optional<MaterialInfo> materialInfoOptional = materialInfoRepository.findById(id);
        MaterialInfo materialInfo = materialInfoOptional.get();
        MaterialInfoDTO materialInfoDTO = materialInfoMapper.toDto(materialInfo);
        if(null != materialInfoDTO){
            BeanUtils.copyProperties( materialInfoDTO, materialInfoDetailDTO);
            String productInventoryWarningStr = materialInfo.getMaterialInventoryWarning();
            if(StringUtils.hasLength(productInventoryWarningStr)){
                List<MaterialInventoryWarning> materialInventoryWarningList = new Gson().fromJson(productInventoryWarningStr,new TypeToken<ArrayList<MaterialInventoryWarning>>() {}.getType());
                materialInfoDetailDTO.setMaterialInventoryWarning(materialInventoryWarningList);
            }


            String materialInitialSetupStr = materialInfo.getMaterialInitialSetup();
            if(StringUtils.hasLength(materialInitialSetupStr)){
                List<MaterialInitialSetup> materialInitialSetupList = new Gson().fromJson(materialInitialSetupStr,new TypeToken<ArrayList<MaterialInitialSetup>>() {}.getType());
                materialInfoDetailDTO.setMaterialInitialSetup(materialInitialSetupList);
            }
        }
        return materialInfoDetailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialInfoDetailDTO create(CreateMaterialInfoRequest createMaterialInfoRequest) {
        Long measureUnitId = createMaterialInfoRequest.getMeasureUnitId();
        if(null == measureUnitId){
            throw new BadRequestException("计量单位不能为空!");
        }
        Optional<MeasureUnit> measureUnitOptional = measureUnitRepository.findById(measureUnitId);
        MeasureUnit measureUnit = measureUnitOptional.get();
        if(null == measureUnit){
            throw new BadRequestException("计量单位不存在!");
        }

        Long materialCategoryId = createMaterialInfoRequest.getMaterialCategoryId();
        if(null == materialCategoryId){
            throw new BadRequestException("物料类别不能为空!");
        }
        Optional<MaterialCategory> materialCategoryOptional = materialCategoryRepository.findById(materialCategoryId);
        MaterialCategory materialCategory = materialCategoryOptional.get();
        if(null == materialCategory){
            throw new BadRequestException("物料类别不存在!");
        }

        MaterialInfoDetailDTO materialInfoDetailDTO = new MaterialInfoDetailDTO();

        MaterialInfo materialInfo = new MaterialInfo();
        BeanUtils.copyProperties(createMaterialInfoRequest, materialInfo);
        materialInfo.setStatus(true);
        List<MaterialInventoryWarning> materialInventoryWarningList = createMaterialInfoRequest.getMaterialInventoryWarning();
        if(!CollectionUtils.isEmpty(materialInventoryWarningList)){
            String materialInventoryWarningStr = new Gson().toJson(materialInventoryWarningList);
            materialInfo.setMaterialInventoryWarning(materialInventoryWarningStr);
            materialInfoDetailDTO.setMaterialInventoryWarning(materialInventoryWarningList);
        }
        List<MaterialInitialSetup> materialInitialSetupList = createMaterialInfoRequest.getMaterialInitialSetup();
        if(!CollectionUtils.isEmpty(materialInitialSetupList)){
            String materialInitialSetupStr = new Gson().toJson(materialInitialSetupList);
            materialInfo.setMaterialInitialSetup(materialInitialSetupStr);
            materialInfoDetailDTO.setMaterialInitialSetup(materialInitialSetupList);
        }

        materialInfo.setMaterialCategoryName(materialCategory.getName());
        materialInfo.setMeasureUnitName(measureUnit.getName());

        materialInfo = materialInfoRepository.save(materialInfo);
        MaterialInfoDTO materialInfoDTO = materialInfoMapper.toDto(materialInfo);
        BeanUtils.copyProperties(materialInfoDTO, materialInfoDetailDTO);
        return materialInfoDetailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateMaterialInfoRequest updateMaterialInfoRequest) {
        Long materialInfoId = updateMaterialInfoRequest.getId();
        if(null == materialInfoId){
            throw new BadRequestException("物料主键不能为空!");
        }

        Long materialCategoryId = updateMaterialInfoRequest.getMaterialCategoryId();
        if(null == materialCategoryId){
            throw new BadRequestException("物料类别主键不能为空!");
        }

        Optional<MaterialCategory> materialCategoryOptional = materialCategoryRepository.findById(materialCategoryId);
        MaterialCategory materialCategory = materialCategoryOptional.get();
        if(null == materialCategory){
            throw new BadRequestException("物料类别不存在!");
        }

        Long measureUnitId = updateMaterialInfoRequest.getMeasureUnitId();
        if(null == measureUnitId){
            throw new BadRequestException("计量单位主键不能为空!");
        }

        Optional<MeasureUnit> measureUnitOptional = measureUnitRepository.findById(measureUnitId);
        MeasureUnit measureUnit = measureUnitOptional.get();
        if(null== measureUnit){
            throw new BadRequestException("计量单位不存在!");
        }

        // 物料资料-物料仓库预警修改目标
        List<MaterialInventoryWarning> materialInventoryWarningTarget = updateMaterialInfoRequest.getMaterialInventoryWarning();
        // 物料资料-供应商联系方式修改目标
        List<MaterialInitialSetup> materialInitialSetupTarget = updateMaterialInfoRequest.getMaterialInitialSetup();

        MaterialInfo materialInfo = materialInfoRepository.findByIdAndStatusTrue(materialInfoId);

        if(null == materialInfo){
            throw new BadRequestException("物料资料不存在");
        }

        Timestamp createTime = materialInfo.getCreateTime();

        // 将需要修改的值复制到数据库对象中
        BeanUtils.copyProperties(updateMaterialInfoRequest, materialInfo);

        // 判断提前获取的供应商联系地址和联系方式是否是空
        if(CollectionUtils.isEmpty(materialInventoryWarningTarget)){
            materialInfo.setMaterialInventoryWarning(null);
        }else{
            String materialInventoryWarningStr = new Gson().toJson(materialInventoryWarningTarget);
            materialInfo.setMaterialInventoryWarning(materialInventoryWarningStr);
        }

        if(CollectionUtils.isEmpty(materialInitialSetupTarget)){
            materialInfo.setMaterialInitialSetup(null);
        }else{
            String materialInitialSetupStr = new Gson().toJson(materialInitialSetupTarget);
            materialInfo.setMaterialInventoryWarning(materialInitialSetupStr);
        }

        materialInfo.setCreateTime(createTime);
        materialInfo.setStatus(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        materialInfo.setUpdateTime(Timestamp.valueOf(sdf.format(new Date())));
        materialInfo.setMaterialCategoryName(materialCategory.getName());
        materialInfo.setMeasureUnitName(materialCategory.getName());
        // 修改物料资料
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