package me.zhengjie.modules.wms.invoice.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.CustomerInfo;
import me.zhengjie.modules.wms.bd.domain.ProductInfo;
import me.zhengjie.modules.wms.bd.repository.CustomerInfoRepository;
import me.zhengjie.modules.wms.bd.repository.ProductInfoRepository;
import me.zhengjie.modules.wms.customerOrder.domain.CustomerOrder;
import me.zhengjie.modules.wms.customerOrder.domain.CustomerOrderProduct;
import me.zhengjie.modules.wms.customerOrder.service.dto.CustomerOrderDTO;
import me.zhengjie.modules.wms.customerOrder.service.dto.CustomerOrderProductDTO;
import me.zhengjie.modules.wms.invoice.domain.Invoice;
import me.zhengjie.modules.wms.invoice.domain.InvoiceProduct;
import me.zhengjie.modules.wms.invoice.repository.InvoiceProductRepository;
import me.zhengjie.modules.wms.invoice.request.CreateInvoiceRequest;
import me.zhengjie.modules.wms.invoice.request.UpdateInvoiceRequest;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public Object queryAll(InvoiceQueryCriteria criteria, Pageable pageable){
        Specification<Invoice> specification = new Specification<Invoice>() {
            @Override
            public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        Page<Invoice> page = invoiceRepository.findAll(specification,pageable);
        Page<InvoiceDTO> invoiceDTOPage = page.map(invoiceMapper::toDto);
        if(null != invoiceDTOPage){
            List<InvoiceDTO> invoiceDTOList = invoiceDTOPage.getContent();
            if(!CollectionUtils.isEmpty(invoiceDTOList)){
                for(InvoiceDTO invoiceDTO : invoiceDTOList){
                    Timestamp createTime = invoiceDTO.getCreateTime();
                    invoiceDTO.setCreateTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(createTime));
                }
            }
        }
        return PageUtil.toPage(invoiceDTOPage);
    }

    @Override
    public Object queryAll(InvoiceQueryCriteria criteria){

        Specification<Invoice> specification = new Specification<Invoice>() {
            @Override
            public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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

        List invoiceLIst = invoiceRepository.findAll(specification);
        List<InvoiceDTO> invoiceDtoList = invoiceMapper.toDto(invoiceLIst);
        return invoiceDtoList;
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

        invoice.setCustomerName(customerInfo.getCustomerName());

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
        invoice.setStatus(true);
        invoiceRepository.save(invoice);
        invoice = invoiceRepository.findBySaleInvoiceCode(saleInvoiceCode);
        InvoiceDTO invoiceDTO = invoiceMapper.toDto(invoice);


        for(InvoiceProduct invoiceProduct : invoiceProductRequestList){
            invoiceProduct.setInvoiceId(invoice.getId());
            String productCode = invoiceProduct.getProductCode();
            if(StringUtils.isEmpty(productCode)){
                throw new BadRequestException("产品编号不能为空!");
            }
            ProductInfo productInfo = productInfoRepository.findByProductCode(productCode);
            if(null == productInfo){
                throw new BadRequestException("产品编号" + productInfo.getProductCode() + "对应的产品不存在!");
            }
            invoiceProduct.setProductId(productInfo.getId());
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
    public void update(UpdateInvoiceRequest updateInvoiceRequest) {
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(updateInvoiceRequest, invoice);
        // 修改发货单概要信息
        invoiceRepository.save(invoice);

        // 修改产品信息之前，查询该订单中原来的产品信息，key为产品code
        List<InvoiceProduct> invoiceProductListBeforeUpdate = invoiceProductRepository.findByInvoiceIdAndStatusTrue(invoice.getId());
        Map<String, InvoiceProduct> invoiceProductMapBefore = invoiceProductListBeforeUpdate.stream().collect(Collectors.toMap(InvoiceProduct::getProductCode, Function.identity()));

        List<InvoiceProductDTO> invoiceProductRequestList = updateInvoiceRequest.getInvoiceProductList();
        if(CollectionUtils.isEmpty(invoiceProductRequestList)){
            throw new BadRequestException("发货单产品不能为空!");
        }

        Map<String, InvoiceProductDTO> invoiceProductMapAfter = invoiceProductRequestList.stream().collect(Collectors.toMap(InvoiceProductDTO::getProductCode, Function.identity()));

        //需要将订单中原来订单对应的产品删除了的数据
        List<String> deleteTargetList = new ArrayList<>();
        //比较量个map中，key不一样的数据
        for(Map.Entry<String, InvoiceProduct> entry:invoiceProductMapBefore.entrySet()){
            String productCode = entry.getKey();
            //修改后的map记录对应的key在原来中是否存在
            InvoiceProductDTO invoiceProductDTOTemp = invoiceProductMapAfter.get(productCode);
            if(null == invoiceProductDTOTemp){
                deleteTargetList.add(entry.getKey());
            }

        }


        List<InvoiceProduct> invoiceProductList = new ArrayList<>();
        for(InvoiceProductDTO invoiceProductDTO : invoiceProductRequestList){
            InvoiceProduct invoiceProduct = new InvoiceProduct();
            BeanUtils.copyProperties(invoiceProductDTO, invoiceProduct);
            invoiceProduct.setInvoiceId(invoice.getId());
            invoiceProduct.setStatus(true);

            if(!(!CollectionUtils.isEmpty(deleteTargetList) && deleteTargetList.contains(invoiceProductDTO.getId()))){
                invoiceProductList.add(invoiceProduct);
            }
        }
        invoiceProductRepository.saveAll(invoiceProductList);

        /**
         * 场景描述:
         * 1.刚开始新增了 a b c三种产品
         * 2.修改的时候删除了 a c两种产品
         * 3.所以需要查修改前数据库中有的产品，再比较修改传过来的产品数据，如果修改后的在原来里面没有，需要将原来里面对应的删除
         */
        if(!CollectionUtils.isEmpty(deleteTargetList)){
            for(String prductCode : deleteTargetList){
                invoiceProductRepository.deleteByProductCodeAndInvoiceId(prductCode, invoice.getId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        invoiceRepository.deleteInvoice(id);
        invoiceProductRepository.deleteInvoiceProduct(id);
    }
}