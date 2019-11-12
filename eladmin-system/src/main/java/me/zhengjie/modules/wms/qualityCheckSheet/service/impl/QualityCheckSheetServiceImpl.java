package me.zhengjie.modules.wms.qualityCheckSheet.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.request.OutSourceProcessSheetProductRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductDTO;
import me.zhengjie.modules.wms.qualityCheckSheet.domain.QualityCheckSheet;
import me.zhengjie.modules.wms.qualityCheckSheet.domain.QualityCheckSheetProduct;
import me.zhengjie.modules.wms.qualityCheckSheet.repository.QualityCheckSheetProductRepository;
import me.zhengjie.modules.wms.qualityCheckSheet.request.CreateQualityCheckSheetRequest;
import me.zhengjie.modules.wms.qualityCheckSheet.request.QualityCheckSheetProductRequest;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetProductDTO;
import me.zhengjie.modules.wms.qualityCheckSheet.service.mapper.QualityCheckSheetProductMapper;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.qualityCheckSheet.repository.QualityCheckSheetRepository;
import me.zhengjie.modules.wms.qualityCheckSheet.service.QualityCheckSheetService;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetDTO;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetQueryCriteria;
import me.zhengjie.modules.wms.qualityCheckSheet.service.mapper.QualityCheckSheetMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
* @author huangxingxing
* @date 2019-11-12
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QualityCheckSheetServiceImpl implements QualityCheckSheetService {

    @Autowired
    private QualityCheckSheetRepository qualityCheckSheetRepository;

    @Autowired
    private QualityCheckSheetMapper qualityCheckSheetMapper;

    @Autowired
    private QualityCheckSheetProductRepository qualityCheckSheetProductRepository;

    @Autowired
    private QualityCheckSheetProductMapper qualityCheckSheetProductMapper;

    @Override
    public Object queryAll(QualityCheckSheetQueryCriteria criteria, Pageable pageable){
        Page<QualityCheckSheet> page = qualityCheckSheetRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(qualityCheckSheetMapper::toDto));
    }

    @Override
    public Object queryAll(QualityCheckSheetQueryCriteria criteria){
        return qualityCheckSheetMapper.toDto(qualityCheckSheetRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public QualityCheckSheetDTO findById(Long id) {
        Optional<QualityCheckSheet> qualityCheckSheet = qualityCheckSheetRepository.findById(id);
        ValidationUtil.isNull(qualityCheckSheet,"QualityCheckSheet","id",id);
        return qualityCheckSheetMapper.toDto(qualityCheckSheet.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QualityCheckSheetDTO create(CreateQualityCheckSheetRequest createQualityCheckSheetRequest) {
        QualityCheckSheet qualityCheckSheet = new QualityCheckSheet();
        BeanUtils.copyProperties(createQualityCheckSheetRequest, qualityCheckSheet);

        String qualityCheekSheetCode = createQualityCheckSheetRequest.getQualityCheekSheetCode();
        if(!StringUtils.hasLength(qualityCheekSheetCode)){
            throw new BadRequestException("质量检验单单据编号不能为空!");
        }

        qualityCheckSheet.setStatus(true);
        // 新增质量检验单
        qualityCheckSheetRepository.save(qualityCheckSheet);

        qualityCheckSheet = qualityCheckSheetRepository.findByQualityCheekSheetCode(qualityCheekSheetCode);

        // 新增质量检验单产品信息
        List<QualityCheckSheetProductRequest> qualityCheckSheetProductRequestList = createQualityCheckSheetRequest.getQualityCheckSheetProductList();
        if(CollectionUtils.isEmpty(qualityCheckSheetProductRequestList)){
            throw new BadRequestException("质量检验单产品信息不能为空!");
        }

        for(QualityCheckSheetProductRequest qualityCheckSheetProductRequest : qualityCheckSheetProductRequestList){
            QualityCheckSheetProduct qualityCheckSheetProduct = new QualityCheckSheetProduct();
            BeanUtils.copyProperties(qualityCheckSheetProductRequest, qualityCheckSheetProduct);
            qualityCheckSheetProduct.setStatus(true);
            qualityCheckSheetProduct.setQualityCheckSheetId(qualityCheckSheet.getId());
            qualityCheckSheetProductRepository.save(qualityCheckSheetProduct);
        }


        QualityCheckSheetDTO qualityCheckSheetDTO = qualityCheckSheetMapper.toDto(qualityCheckSheet);

        List<QualityCheckSheetProduct> qualityCheckSheetProductList = qualityCheckSheetProductRepository.queryByQualityCheckSheetIdAndStatusTrue(qualityCheckSheet.getId());
        if(!CollectionUtils.isEmpty(qualityCheckSheetProductList)){
            List<QualityCheckSheetProductDTO> qualityCheckSheetProductDTOList = new ArrayList<>();
            for(QualityCheckSheetProduct qualityCheckSheetProduct : qualityCheckSheetProductList){
                QualityCheckSheetProductDTO qualityCheckSheetProductDTO = new QualityCheckSheetProductDTO();
                BeanUtils.copyProperties(qualityCheckSheetProduct, qualityCheckSheetProductDTO);
                qualityCheckSheetProductDTOList.add(qualityCheckSheetProductDTO);
            }
            qualityCheckSheetDTO.setQualityCheckSheetProductList(qualityCheckSheetProductDTOList);
        }

        return qualityCheckSheetDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QualityCheckSheet resources) {
        Optional<QualityCheckSheet> optionalQualityCheckSheet = qualityCheckSheetRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalQualityCheckSheet,"QualityCheckSheet","id",resources.getId());
        QualityCheckSheet qualityCheckSheet = optionalQualityCheckSheet.get();
        qualityCheckSheet.copy(resources);
        qualityCheckSheetRepository.save(qualityCheckSheet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        qualityCheckSheetRepository.deleteById(id);
    }
}