package me.zhengjie.modules.wms.outSourceProductSheet.service.impl;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificateProduct;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.outSourceProductSheet.repository.OutSourceInspectionCertificateProductRepository;
import me.zhengjie.modules.wms.outSourceProductSheet.service.OutSourceInspectionCertificateProductService;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateProductDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateProductQueryCriteria;
import me.zhengjie.modules.wms.outSourceProductSheet.service.mapper.OutSourceInspectionCertificateProductMapper;
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
public class OutSourceInspectionCertificateProductServiceImpl implements OutSourceInspectionCertificateProductService {

    @Autowired
    private OutSourceInspectionCertificateProductRepository outSourceInspectionCertificateProductRepository;

    @Autowired
    private OutSourceInspectionCertificateProductMapper outSourceInspectionCertificateProductMapper;

    @Override
    public Object queryAll(OutSourceInspectionCertificateProductQueryCriteria criteria, Pageable pageable){
        Page<OutSourceInspectionCertificateProduct> page = outSourceInspectionCertificateProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(outSourceInspectionCertificateProductMapper::toDto));
    }

    @Override
    public Object queryAll(OutSourceInspectionCertificateProductQueryCriteria criteria){
        return outSourceInspectionCertificateProductMapper.toDto(outSourceInspectionCertificateProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public OutSourceInspectionCertificateProductDTO findById(Long id) {
        Optional<OutSourceInspectionCertificateProduct> sOutSourceInspectionCertificateProduct = outSourceInspectionCertificateProductRepository.findById(id);
        ValidationUtil.isNull(sOutSourceInspectionCertificateProduct,"SOutSourceInspectionCertificateProduct","id",id);
        return outSourceInspectionCertificateProductMapper.toDto(sOutSourceInspectionCertificateProduct.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutSourceInspectionCertificateProductDTO create(OutSourceInspectionCertificateProduct resources) {
        return outSourceInspectionCertificateProductMapper.toDto(outSourceInspectionCertificateProductRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OutSourceInspectionCertificateProduct resources) {
        Optional<OutSourceInspectionCertificateProduct> optionalSOutSourceInspectionCertificateProduct = outSourceInspectionCertificateProductRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalSOutSourceInspectionCertificateProduct,"SOutSourceInspectionCertificateProduct","id",resources.getId());
        OutSourceInspectionCertificateProduct outSourceInspectionCertificateProduct = optionalSOutSourceInspectionCertificateProduct.get();
        outSourceInspectionCertificateProduct.copy(resources);
        outSourceInspectionCertificateProductRepository.save(outSourceInspectionCertificateProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        outSourceInspectionCertificateProductRepository.deleteById(id);
    }
}