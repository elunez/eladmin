package me.zhengjie.modules.wms.outSourceProductSheet.service.impl;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.outSourceProductSheet.repository.OutSourceProcessSheetProductRepository;
import me.zhengjie.modules.wms.outSourceProductSheet.service.OutSourceProcessSheetProductService;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductQueryCriteria;
import me.zhengjie.modules.wms.outSourceProductSheet.service.mapper.OutSourceProcessSheetProductMapper;
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
* @date 2019-08-17
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OutSourceProcessSheetProductServiceImpl implements OutSourceProcessSheetProductService {

    @Autowired
    private OutSourceProcessSheetProductRepository outSourceProcessSheetProductRepository;

    @Autowired
    private OutSourceProcessSheetProductMapper outSourceProcessSheetProductMapper;

    @Override
    public Object queryAll(OutSourceProcessSheetProductQueryCriteria criteria, Pageable pageable){
        Page<OutSourceProcessSheetProduct> page = outSourceProcessSheetProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(outSourceProcessSheetProductMapper::toDto));
    }

    @Override
    public Object queryAll(OutSourceProcessSheetProductQueryCriteria criteria){
        return outSourceProcessSheetProductMapper.toDto(outSourceProcessSheetProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public OutSourceProcessSheetProductDTO findById(Long id) {
        Optional<OutSourceProcessSheetProduct> sOutSourceProcessSheetProduct = outSourceProcessSheetProductRepository.findById(id);
        ValidationUtil.isNull(sOutSourceProcessSheetProduct,"SOutSourceProcessSheetProduct","id",id);
        return outSourceProcessSheetProductMapper.toDto(sOutSourceProcessSheetProduct.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutSourceProcessSheetProductDTO create(OutSourceProcessSheetProduct resources) {
        return outSourceProcessSheetProductMapper.toDto(outSourceProcessSheetProductRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OutSourceProcessSheetProduct resources) {
        Optional<OutSourceProcessSheetProduct> optionalSOutSourceProcessSheetProduct = outSourceProcessSheetProductRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalSOutSourceProcessSheetProduct,"SOutSourceProcessSheetProduct","id",resources.getId());
        OutSourceProcessSheetProduct outSourceProcessSheetProduct = optionalSOutSourceProcessSheetProduct.get();
        outSourceProcessSheetProduct.copy(resources);
        outSourceProcessSheetProductRepository.save(outSourceProcessSheetProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        outSourceProcessSheetProductRepository.deleteById(id);
    }
}