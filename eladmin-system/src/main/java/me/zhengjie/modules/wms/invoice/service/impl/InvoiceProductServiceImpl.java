package me.zhengjie.modules.wms.invoice.service.impl;

import me.zhengjie.modules.wms.invoice.domain.InvoiceProduct;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.invoice.repository.InvoiceProductRepository;
import me.zhengjie.modules.wms.invoice.service.InvoiceProductService;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceProductDTO;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceProductQueryCriteria;
import me.zhengjie.modules.wms.invoice.service.mapper.InvoiceProductMapper;
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
* @date 2019-08-27
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class InvoiceProductServiceImpl implements InvoiceProductService {

    @Autowired
    private InvoiceProductRepository invoiceProductRepository;

    @Autowired
    private InvoiceProductMapper invoiceProductMapper;

    @Override
    public Object queryAll(InvoiceProductQueryCriteria criteria, Pageable pageable){
        Page<InvoiceProduct> page = invoiceProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(invoiceProductMapper::toDto));
    }

    @Override
    public Object queryAll(InvoiceProductQueryCriteria criteria){
        return invoiceProductMapper.toDto(invoiceProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public InvoiceProductDTO findById(Long id) {
        Optional<InvoiceProduct> sInvoiceProduct = invoiceProductRepository.findById(id);
        ValidationUtil.isNull(sInvoiceProduct,"SInvoiceProduct","id",id);
        return invoiceProductMapper.toDto(sInvoiceProduct.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvoiceProductDTO create(InvoiceProduct resources) {
        return invoiceProductMapper.toDto(invoiceProductRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(InvoiceProduct resources) {
        Optional<InvoiceProduct> optionalSInvoiceProduct = invoiceProductRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalSInvoiceProduct,"SInvoiceProduct","id",resources.getId());
        InvoiceProduct invoiceProduct = optionalSInvoiceProduct.get();
        invoiceProduct.copy(resources);
        invoiceProductRepository.save(invoiceProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        invoiceProductRepository.deleteById(id);
    }
}