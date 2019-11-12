package me.zhengjie.modules.wms.qualityCheckSheet.service.impl;

import me.zhengjie.modules.wms.qualityCheckSheet.domain.QualityCheckSheet;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.qualityCheckSheet.repository.QualityCheckSheetRepository;
import me.zhengjie.modules.wms.qualityCheckSheet.service.QualityCheckSheetService;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetDTO;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetQueryCriteria;
import me.zhengjie.modules.wms.qualityCheckSheet.service.mapper.QualityCheckSheetMapper;
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
public class QualityCheckSheetServiceImpl implements QualityCheckSheetService {

    @Autowired
    private QualityCheckSheetRepository qualityCheckSheetRepository;

    @Autowired
    private QualityCheckSheetMapper qualityCheckSheetMapper;

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
    public QualityCheckSheetDTO create(QualityCheckSheet resources) {
        return qualityCheckSheetMapper.toDto(qualityCheckSheetRepository.save(resources));
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