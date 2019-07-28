package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.modules.wms.bd.domain.MaterialInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.bd.repository.MaterialInfoRepository;
import me.zhengjie.modules.wms.bd.service.MaterialInfoService;
import me.zhengjie.modules.wms.bd.service.dto.MaterialInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.MaterialInfoQueryCriteria;
import me.zhengjie.modules.wms.bd.service.mapper.MaterialInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

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

    @Override
    public Object queryAll(MaterialInfoQueryCriteria criteria, Pageable pageable){
        Page<MaterialInfo> page = materialInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(materialInfoMapper::toDto));
    }

    @Override
    public Object queryAll(MaterialInfoQueryCriteria criteria){
        return materialInfoMapper.toDto(materialInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public MaterialInfoDTO findById(Integer id) {
        Optional<MaterialInfo> bdMaterialInfo = materialInfoRepository.findById(id);
        ValidationUtil.isNull(bdMaterialInfo,"BdMaterialInfo","id",id);
        return materialInfoMapper.toDto(bdMaterialInfo.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialInfoDTO create(MaterialInfo resources) {
        return materialInfoMapper.toDto(materialInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MaterialInfo resources) {
        Optional<MaterialInfo> optionalBdMaterialInfo = materialInfoRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalBdMaterialInfo,"BdMaterialInfo","id",resources.getId());
        MaterialInfo materialInfo = optionalBdMaterialInfo.get();
        materialInfo.copy(resources);
        materialInfoRepository.save(materialInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        materialInfoRepository.deleteById(id);
    }
}