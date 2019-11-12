package me.zhengjie.modules.wms.qualityCheckSheet.service.impl;

import me.zhengjie.modules.wms.qualityCheckSheet.domain.QualityCheckSheetProduct;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.qualityCheckSheet.repository.QualityCheckSheetProductRepository;
import me.zhengjie.modules.wms.qualityCheckSheet.service.QualityCheckSheetProductService;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetProductDTO;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetProductQueryCriteria;
import me.zhengjie.modules.wms.qualityCheckSheet.service.mapper.QualityCheckSheetProductMapper;
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
* @author huangxingxing
* @date 2019-11-12
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QualityCheckSheetProductServiceImpl implements QualityCheckSheetProductService {

    @Autowired
    private QualityCheckSheetProductRepository qualityCheckSheetProductRepository;

    @Autowired
    private QualityCheckSheetProductMapper qualityCheckSheetProductMapper;

    @Override
    public Object queryAll(QualityCheckSheetProductQueryCriteria criteria, Pageable pageable){
        Page<QualityCheckSheetProduct> page = qualityCheckSheetProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(qualityCheckSheetProductMapper::toDto));
    }

    @Override
    public Object queryAll(QualityCheckSheetProductQueryCriteria criteria){
        return qualityCheckSheetProductMapper.toDto(qualityCheckSheetProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public QualityCheckSheetProductDTO findById(Long id) {
        Optional<QualityCheckSheetProduct> qualityCheckSheetProduct = qualityCheckSheetProductRepository.findById(id);
        ValidationUtil.isNull(qualityCheckSheetProduct,"QualityCheckSheetProduct","id",id);
        return qualityCheckSheetProductMapper.toDto(qualityCheckSheetProduct.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QualityCheckSheetProductDTO create(QualityCheckSheetProduct resources) {
        return qualityCheckSheetProductMapper.toDto(qualityCheckSheetProductRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QualityCheckSheetProduct resources) {
        Optional<QualityCheckSheetProduct> optionalQualityCheckSheetProduct = qualityCheckSheetProductRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalQualityCheckSheetProduct,"QualityCheckSheetProduct","id",resources.getId());
        QualityCheckSheetProduct qualityCheckSheetProduct = optionalQualityCheckSheetProduct.get();
        qualityCheckSheetProduct.copy(resources);
        qualityCheckSheetProductRepository.save(qualityCheckSheetProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        qualityCheckSheetProductRepository.deleteById(id);
    }
}