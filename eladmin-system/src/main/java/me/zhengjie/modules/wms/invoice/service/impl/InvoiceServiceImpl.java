package me.zhengjie.modules.wms.invoice.service.impl;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.cons.MessageModulePath;
import me.zhengjie.modules.system.cons.MessageModuleType;
import me.zhengjie.modules.system.cons.MessageReadStatus;
import me.zhengjie.modules.system.domain.Message;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.repository.MessageRepository;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.modules.wms.bd.domain.CustomerInfo;
import me.zhengjie.modules.wms.bd.domain.ProductInfo;
import me.zhengjie.modules.wms.bd.repository.CustomerInfoRepository;
import me.zhengjie.modules.wms.bd.repository.ProductInfoRepository;
import me.zhengjie.modules.wms.customerOrder.domain.CustomerOrder;
import me.zhengjie.modules.wms.customerOrder.domain.CustomerOrderProduct;
import me.zhengjie.modules.wms.customerOrder.repository.CustomerOrderProductRepository;
import me.zhengjie.modules.wms.customerOrder.repository.CustomerOrderRepository;
import me.zhengjie.modules.wms.invoice.domain.Invoice;
import me.zhengjie.modules.wms.invoice.domain.InvoiceProduct;
import me.zhengjie.modules.wms.invoice.repository.InvoiceProductRepository;
import me.zhengjie.modules.wms.invoice.request.CreateInvoiceRequest;
import me.zhengjie.modules.wms.invoice.request.UpdateInvoiceRequest;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceDetailDTO;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceProductDTO;
import me.zhengjie.modules.wms.invoice.service.mapper.InvoiceProductMapper;
import me.zhengjie.modules.wms.sr.productCount.domain.ProductCount;
import me.zhengjie.modules.wms.sr.productCount.repository.ProductCountRepository;
import me.zhengjie.utils.*;
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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@Slf4j
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
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private CustomerOrderProductRepository customerOrderProductRepository;

    @Autowired
    private InvoiceProductMapper invoiceProductMapper;

    @Autowired
    private InvoiceProductRepository invoiceProductRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductCountRepository productCountRepository;

    @Override
    public Object queryAll(InvoiceQueryCriteria criteria, Pageable pageable){
        Specification<Invoice> specification = new Specification<Invoice>() {
            @Override
            public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);


                if(!me.zhengjie.utils.StringUtils.isEmpty(criteria.getCustomerOrderCode())){
                    Predicate customerOrderCodePredicate = criteriaBuilder.like(root.get("customerOrderCode").as(String.class), "%" + criteria.getCustomerOrderCode() + "%");
                    targetPredicateList.add(customerOrderCodePredicate);
                }

                if(!me.zhengjie.utils.StringUtils.isEmpty(criteria.getCustomerName())){
                    Predicate customerNamePredicate = criteriaBuilder.like(root.get("customerName").as(String.class),"%" +  criteria.getCustomerName() + "%");
                    targetPredicateList.add(customerNamePredicate);
                }

                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));

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
            for(InvoiceProductDTO invoiceProductDTO : invoiceProductDTOList){
                invoiceProductDTO.setProductNumber(invoiceProductDTO.getCustomerOrderNumber());
            }
            invoiceDetailDTO.setInvoiceProductDTOList(invoiceProductDTOList);
        }
        return invoiceDetailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvoiceDetailDTO create(CreateInvoiceRequest createInvoiceRequest) {
        List<String> repeatProductCodeList =createInvoiceRequest.getInvoiceProductList().stream().
                collect(Collectors.groupingBy(dog->dog.getProductCode() ,Collectors.counting()))
                .entrySet().stream()
                .filter(entry->entry.getValue()>1)
                .map(entry->entry.getKey())
                .collect(Collectors.toList());

        if(!CollectionUtils.isEmpty(repeatProductCodeList)){
            String repeatProductCodeStr = me.zhengjie.utils.StringUtils.join(repeatProductCodeList.toArray(), ",");
            throw new BadRequestException("产品" + repeatProductCodeStr + "请合并为一条记录");
        }


        Invoice invoice = new Invoice();
        // 客户订单编号
        String customerOrderCode = createInvoiceRequest.getCustomerOrderCode();
        if(StringUtils.isEmpty(customerOrderCode)){
            throw new BadRequestException("客户订单编号不能为空!");
        }

        CustomerOrder customerOrder = customerOrderRepository.findByCustomerOrderCodeAndStatusTrue(customerOrderCode);
        customerOrder.setProcStatus(ProcStatusEnum.SENDING_GOOD.getCode());
        customerOrderRepository.save(customerOrder);

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

        // 校验
        for(InvoiceProduct invoiceProduct : invoiceProductRequestList){
            String productCode = invoiceProduct.getProductCode();
            if(StringUtils.isEmpty(productCode)){
                throw new BadRequestException("产品编号不能为空!");
            }
            String productName = invoiceProduct.getProductName();
            if(StringUtils.isEmpty(productName)){
                throw new BadRequestException("产品编号" + productCode+"对应的产品名称不能为空!");
            }
            String specifications = invoiceProduct.getSpecifications();
            if(StringUtils.isEmpty(specifications)){
                throw new BadRequestException("产品编号" + productCode+"对应的产品规格不能为空!");
            }
            Long unitPrice = invoiceProduct.getUnitPrice();
            if(null == unitPrice){
                throw new BadRequestException("产品编号" + productCode+"对应的产品单价不能为空!");
            }
            Long customerOrderNumber = invoiceProduct.getCustomerOrderNumber();
            if(null == customerOrderNumber){
                throw new BadRequestException("产品编号" + productCode+"对应的订单数量不能为空!");
            }
            Long actualInvoiceNumber = invoiceProduct.getActualInvoiceNumber();
            if(null == actualInvoiceNumber){
                throw new BadRequestException("产品编号" + productCode+"对应的实发数量不能为空!");
            }
            Long salePrice = invoiceProduct.getSalePrice();
            if(null == salePrice){
                throw new BadRequestException("产品编号" + productCode+"对应的销售金额不能为空!");
            }

        }

        BeanUtils.copyProperties(createInvoiceRequest, invoice);
        invoice.setStatus(true);
        invoice.setPici(PiciUtil.initPici(customerOrderCode));
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
            invoiceProduct.setCustomerOrderCode(customerOrderCode);
            invoiceProduct.setProductId(productInfo.getId());
            invoiceProductRepository.save(invoiceProduct);

            //TODO 发货之后，扣除统计数据
            ProductCount productCount = productCountRepository.findByProductCode(productCode);
            if(null != productCount){
                Long dphNumber = productCount.getDphNumber();
                // 剩余数量
                long syNumber = dphNumber - invoiceProduct.getActualInvoiceNumber();
                if(syNumber<0){
                    syNumber = 0;
                }
                productCountRepository.updateDphNumber(dphNumber, productCode);
            }
        }

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoiceIdAndStatusTrue(invoice.getId());
        List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductMapper.toDto(invoiceProductList);
        for(InvoiceProductDTO invoiceProductDTO : invoiceProductDTOList){
            invoiceProductDTO.setProductNumber(invoiceProductDTO.getCustomerOrderNumber());
        }

        InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO();
        BeanUtils.copyProperties(invoiceDTO, invoiceDetailDTO);
        invoiceDetailDTO.setInvoiceProductDTOList(invoiceProductDTOList);

        /**
         * 判断客户订单是否完成
         * 查看客户订单所有的商品以及数量
         * 查看该订单的所有发货单数量，
         * 比较是否完成
         */

        /**
         * 新增消息通知
         */
        try {
            // 查看所有用户
            List<User> userList =(List<User>)userRepository.findByEnabled(true);
            if(!CollectionUtils.isEmpty(userList)){
                List<Message> messageList = new ArrayList<>();
                for(User user : userList){
                    Message message = new Message();
                    message.setUserIdAccept(user.getId());
                    String messageContent = MessageModuleType.INVOICE.getName() + "(" + saleInvoiceCode + ")" + "新录入,请查看";
                    message.setMessContent(messageContent);
                    message.setModulePath(MessageModulePath.DELIVERY_ORDER_INFO_LIST.getCode());
                    message.setModuleTypeName(MessageModuleType.INVOICE.getName());
                    message.setReadStatus(MessageReadStatus.NO_READ.getStatus());
                    message.setInitCode(saleInvoiceCode);
                    message.setModuleTypeCode(saleInvoiceCode);
                    message.setStatus(true);
                    message.setUserIdSend(SecurityUtils.getUserId());
                    message.setUserNameSend(SecurityUtils.getUsername());
                    message.setInitCode(customerOrderCode);
                    messageList.add(message);
                }
                messageRepository.saveAll(messageList);
            }

            // 查看发货单的数量是否根订单数量一致，如果一致则订单已发货，如果
            Map<String, Long> existProduct = new HashMap<>();
            List<InvoiceProduct> existProductList = invoiceProductRepository.findByCustomerOrderCodeAndStatusTrue(customerOrderCode);
            if(!CollectionUtils.isEmpty(existProductList)){
                Map<String, List<InvoiceProduct>> existProductMap = existProductList.stream().collect(Collectors.groupingBy(InvoiceProduct::getProductCode));
                for(Map.Entry<String, List<InvoiceProduct>> entry : existProductMap.entrySet()){
                    String productCode = entry.getKey();
                    List<InvoiceProduct> invoiceProductListTemp = entry.getValue();
                    if(!CollectionUtils.isEmpty(invoiceProductListTemp)){
                        Long productNumberTotal = invoiceProductListTemp.stream().mapToLong(InvoiceProduct::getActualInvoiceNumber).sum();
                        existProduct.put(productCode, productNumberTotal);
                    }
                }
            }
            Map<String, Long> sourceProduct = new HashMap<>();
            List<CustomerOrderProduct> sourceProductList = customerOrderProductRepository.findByCustomerOrderCodeAndStatusTrue(customerOrderCode);
            if(!CollectionUtils.isEmpty(sourceProductList)){
                Map<String, List<CustomerOrderProduct>> sourceProductMap = sourceProductList.stream().collect(Collectors.groupingBy(CustomerOrderProduct::getProductCode));
                for(Map.Entry<String, List<CustomerOrderProduct>> entry : sourceProductMap.entrySet()){
                    String productCode = entry.getKey();
                    List<CustomerOrderProduct> customerOrderProductListTemp = entry.getValue();
                    if(!CollectionUtils.isEmpty(customerOrderProductListTemp)){
                        Long productNumberTotal = customerOrderProductListTemp.stream().mapToLong(CustomerOrderProduct::getProductNumber).sum();
                        sourceProduct.put(productCode, productNumberTotal);
                    }
                }
            }

            if(!CollectionUtils.isEmpty(sourceProduct)){
                for(Map.Entry<String, Long> entry : sourceProduct.entrySet()){
                    String productCodeTemp = entry.getKey();
                    long productNumberTemp = entry.getValue();
                    long productNumberTempExist = existProduct.get(productCodeTemp);
                    if(productNumberTemp == productNumberTempExist){
                        customerOrderRepository.updateProcStatus(ProcStatusEnum.COMPLETED.getCode(), customerOrderCode);
                    }else{
                        customerOrderRepository.updateProcStatus(ProcStatusEnum.SENDING_GOOD.getCode(), customerOrderCode);
                    }
                }
            }
        }catch (Exception e){
            log.error("单据编号:插入消息失败!");
        }

        return invoiceDetailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateInvoiceRequest updateInvoiceRequest) {

        List<String> repeatProductCodeList =updateInvoiceRequest.getInvoiceProductList().stream().
                collect(Collectors.groupingBy(dog->dog.getProductCode() ,Collectors.counting()))
                .entrySet().stream()
                .filter(entry->entry.getValue()>1)
                .map(entry->entry.getKey())
                .collect(Collectors.toList());

        if(!CollectionUtils.isEmpty(repeatProductCodeList)){
            String repeatProductCodeStr = me.zhengjie.utils.StringUtils.join(repeatProductCodeList.toArray(), ",");
            throw new BadRequestException("产品" + repeatProductCodeStr + "请合并为一条记录");
        }

        Optional<Invoice> invoiceOptional = invoiceRepository.findById(updateInvoiceRequest.getId());
        ValidationUtil.isNull(invoiceOptional,"SInvoice","id",updateInvoiceRequest.getId());
        Invoice invoiceOrigin = invoiceOptional.get();


        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(updateInvoiceRequest, invoice);
        // 修改发货单概要信息
        invoice.setCreateTime(invoiceOrigin.getCreateTime());
        Timestamp updateTime = new Timestamp(System.currentTimeMillis());
        invoice.setUpdateTime(updateTime);
        invoice.setStatus(true);
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

    /**
     * 处理当前客户订单的完成状态
     * @param customerOrderCode
     */
    public void dealCustomerOrderCompleteStatus(String customerOrderCode){
        if(!StringUtils.isEmpty(customerOrderCode)){
            List<CustomerOrderProduct> customerOrderProductList = customerOrderProductRepository.findByCustomerOrderCodeAndStatusTrue(customerOrderCode);
            if(!CollectionUtils.isEmpty(customerOrderProductList)){
                Map<String, CustomerOrderProduct> customerOrderProductMap = customerOrderProductList.stream().collect(Collectors.toMap(CustomerOrderProduct::getProductCode, a -> a, (k1, k2) -> k1));

                // 查看客户订单对应销售发货单所有产品
                List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByCustomerOrderCodeAndStatusTrue(customerOrderCode);
                if(!CollectionUtils.isEmpty(invoiceProductList)){
                    // 销售发货单产品集合，key为产品，value为产品集合
//                    Map<String, List<InvoiceProduct>> invoiceProductMap = invoiceProductList.stream().collect(Collectors.groupingBy(InvoiceProduct::getProductCode));
                    Map<String,Integer> existProductNumnerMap = new HashMap<>();
//                    invoiceProductList.forEach(invoiceProduct -> existProductNumnerMap.computeIfPresent(invoiceProduct.getCustomerOrderCode(), invoiceProduct.getCustomerOrderNumber()));
                }
            }
        }
    }

    public static void main(String[] args) {
        List<InvoiceProduct> invoiceProductList = new ArrayList<>();

        InvoiceProduct invoiceProduct = new InvoiceProduct();
        invoiceProduct.setProductCode("01001");
        invoiceProduct.setCustomerOrderNumber(10L);
        invoiceProductList.add(invoiceProduct);


        InvoiceProduct invoiceProduct2 = new InvoiceProduct();
        invoiceProduct2.setProductCode("01001");
        invoiceProduct2.setCustomerOrderNumber(10L);
        invoiceProductList.add(invoiceProduct2);



        Map<String, Long> targetMap = new HashMap<>();

        invoiceProductList.forEach(item->{
            System.out.println(item.getProductCode() + ":" + item.getCustomerOrderNumber());

            Long tempNum = targetMap.computeIfAbsent(item.getProductCode(), (k) ->{
                Long tempNumber = null == targetMap.get(k)?0L:targetMap.get(k);
                tempNumber += item.getCustomerOrderNumber();
                return tempNumber;

            });


            targetMap.put(item.getProductCode(), tempNum);
            System.out.println(tempNum);
        });


        System.out.println(new Gson().toJson(targetMap));

    }
}