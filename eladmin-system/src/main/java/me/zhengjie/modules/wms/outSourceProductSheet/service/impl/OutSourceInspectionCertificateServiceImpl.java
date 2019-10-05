package me.zhengjie.modules.wms.outSourceProductSheet.service.impl;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificate;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.outSourceProductSheet.repository.OutSourceInspectionCertificateRepository;
import me.zhengjie.modules.wms.outSourceProductSheet.service.OutSourceInspectionCertificateService;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateQueryCriteria;
import me.zhengjie.modules.wms.outSourceProductSheet.service.mapper.OutSourceInspectionCertificateMapper;
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
* @author jie
* @date 2019-10-01
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OutSourceInspectionCertificateServiceImpl implements OutSourceInspectionCertificateService {

    @Autowired
    private OutSourceInspectionCertificateRepository outSourceInspectionCertificateRepository;

    @Autowired
    private OutSourceInspectionCertificateMapper outSourceInspectionCertificateMapper;

    @Override
    public Object queryAll(OutSourceInspectionCertificateQueryCriteria criteria, Pageable pageable){
        Page<OutSourceInspectionCertificate> page = outSourceInspectionCertificateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(outSourceInspectionCertificateMapper::toDto));
    }

    @Override
    public Object queryAll(OutSourceInspectionCertificateQueryCriteria criteria){
        return outSourceInspectionCertificateMapper.toDto(outSourceInspectionCertificateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public OutSourceInspectionCertificateDTO findById(Long id) {
        Optional<OutSourceInspectionCertificate> sOutSourceInspectionCertificate = outSourceInspectionCertificateRepository.findById(id);
        ValidationUtil.isNull(sOutSourceInspectionCertificate,"SOutSourceInspectionCertificate","id",id);
        return outSourceInspectionCertificateMapper.toDto(sOutSourceInspectionCertificate.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutSourceInspectionCertificateDTO create(OutSourceInspectionCertificate resources) {
        return outSourceInspectionCertificateMapper.toDto(outSourceInspectionCertificateRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OutSourceInspectionCertificate resources) {
        Optional<OutSourceInspectionCertificate> optionalSOutSourceInspectionCertificate = outSourceInspectionCertificateRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalSOutSourceInspectionCertificate,"SOutSourceInspectionCertificate","id",resources.getId());
        OutSourceInspectionCertificate outSourceInspectionCertificate = optionalSOutSourceInspectionCertificate.get();
        outSourceInspectionCertificate.copy(resources);
        outSourceInspectionCertificateRepository.save(outSourceInspectionCertificate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        outSourceInspectionCertificateRepository.deleteById(id);
    }
}