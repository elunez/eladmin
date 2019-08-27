package me.zhengjie.modules.wms.invoice.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.CustomerInfo;
import me.zhengjie.modules.wms.bd.repository.CustomerInfoRepository;
import me.zhengjie.modules.wms.invoice.domain.Invoice;
import me.zhengjie.modules.wms.invoice.domain.InvoiceProduct;
import me.zhengjie.modules.wms.invoice.repository.InvoiceProductRepository;
import me.zhengjie.modules.wms.invoice.request.CreateInvoiceRequest;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceDetailDTO;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceProductDTO;
import me.zhengjie.modules.wms.invoice.service.mapper.InvoiceProductMapper;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.invoice.repository.InvoiceRepository;
import me.zhengjie.modules.wms.invoice.service.InvoiceService;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceDTO;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceQueryCriteria;
import me.zhengjie.modules.wms.invoice.service.mapper.InvoiceMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
* @author jie
* @date 2019-08-27
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private InvoiceProductMapper invoiceProductMapper;

    @Autowired
    private InvoiceProductRepository invoiceProductRepository;

    @Override
    public Object queryAll(InvoiceQueryCriteria criteria, Pageable pageable){
        Page<Invoice> page = invoiceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(invoiceMapper::toDto));
    }

    @Override
    public Object queryAll(InvoiceQueryCriteria criteria){
        return invoiceMapper.toDto(invoiceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public InvoiceDetailDTO findById(Long id) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        ValidationUtil.isNull(invoiceOptional,"SInvoice","id",id);
        Invoice invoice = invoiceOptional.get();
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);

        InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO();
        BeanUtils.copyProperties(invoiceDTO, invoiceDetailDTO);

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoiceIdAndStatusTrue(id);
        if(!CollectionUtils.isEmpty(invoiceProductList)){
            List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductMapper.toDto(invoiceProductList);
            invoiceDetailDTO.setInvoiceProductDTOList(invoiceProductDTOList);
        }
        return invoiceDetailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvoiceDetailDTO create(CreateInvoiceRequest createInvoiceRequest) {
        Invoice invoice = new Invoice();
        // 客户订单编号
        String customerOrderCode = createInvoiceRequest.getCustomerOrderCode();
        if(StringUtils.isEmpty(customerOrderCode)){
            throw new BadRequestException("客户订单编号不能为空!");
        }

        // 客户ID
        Long customerId = createInvoiceRequest.getCustomerId();
        if(null == customerId){
            throw new BadRequestException("客户ID不能为空!");
        }

        Optional<CustomerInfo> customerInfoOptional = customerInfoRepository.findById(customerId);
        if(null == customerInfoOptional){
            throw new BadRequestException("客户不存在!");
        }
        CustomerInfo customerInfo = customerInfoOptional.get();
        if(null == customerInfo){
            throw new BadRequestException("客户不存在!");
        }

        // 销售发货单号
        String saleInvoiceCode = createInvoiceRequest.getSaleInvoiceCode();
        if(StringUtils.isEmpty(saleInvoiceCode)){
            throw new BadRequestException("销售发货单单据编号不能为空!");
        }

        List<InvoiceProduct> invoiceProductRequestList = createInvoiceRequest.getInvoiceProductList();
        if(CollectionUtils.isEmpty(invoiceProductRequestList)){
            throw new BadRequestException("发货单产品信息不能为空!");
        }

        BeanUtils.copyProperties(createInvoiceRequest, invoice);
        invoiceRepository.save(invoice);
        invoice = invoiceRepository.findBySaleInvoiceCode(saleInvoiceCode);
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);


        for(InvoiceProduct invoiceProduct : invoiceProductRequestList){
            invoiceProduct.setInvoiceId(invoice.getId());
            invoiceProductRepository.save(invoiceProduct);
        }

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoiceIdAndStatusTrue(invoice.getId());
        List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductMapper.toDto(invoiceProductList);

        InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO();
        BeanUtils.copyProperties(invoiceDTO, invoiceDetailDTO);
        invoiceDetailDTO.setInvoiceProductDTOList(invoiceProductDTOList);
        return invoiceDetailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Invoice resources) {
        Optional<Invoice> optionalSInvoice = invoiceRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalSInvoice,"SInvoice","id",resources.getId());
        Invoice invoice = optionalSInvoice.get();
        invoice.copy(resources);
        invoiceRepository.save(invoice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        invoiceRepository.deleteById(id);
    }
}