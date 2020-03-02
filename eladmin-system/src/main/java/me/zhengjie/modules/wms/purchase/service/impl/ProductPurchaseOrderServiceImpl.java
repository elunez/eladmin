package me.zhengjie.modules.wms.purchase.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.cons.MessageModulePath;
import me.zhengjie.modules.system.cons.MessageModuleType;
import me.zhengjie.modules.system.cons.MessageReadStatus;
import me.zhengjie.modules.system.domain.Message;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.repository.MessageRepository;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.UserDTO;
import me.zhengjie.modules.system.service.dto.UserQueryCriteria;
import me.zhengjie.modules.wms.purchase.cons.AuditStatus;
import me.zhengjie.modules.wms.purchase.domain.ConsumablesPurchaseOrder;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrder;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrderProduct;
import me.zhengjie.modules.wms.purchase.repository.ProductPurchaseOrderProductRepository;
import me.zhengjie.modules.wms.purchase.request.AuditProductPurchaseOrderRequest;
import me.zhengjie.modules.wms.purchase.request.CreateProductPurchaseOrderRequest;
import me.zhengjie.modules.wms.purchase.request.ProductPurchaseOrderProductRequest;
import me.zhengjie.modules.wms.purchase.request.UpdateProductPurchaseOrderRequest;
import me.zhengjie.modules.wms.purchase.service.dto.ConsumablesPurchaseOrderDTO;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderProductDTO;
import me.zhengjie.modules.wms.purchase.service.mapper.ProductPurchaseOrderProductMapper;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.purchase.repository.ProductPurchaseOrderRepository;
import me.zhengjie.modules.wms.purchase.service.ProductPurchaseOrderService;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderDTO;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderQueryCriteria;
import me.zhengjie.modules.wms.purchase.service.mapper.ProductPurchaseOrderMapper;
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
* @date 2019-10-06
*/
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProductPurchaseOrderServiceImpl implements ProductPurchaseOrderService {

    @Autowired
    private ProductPurchaseOrderRepository productPurchaseOrderRepository;

    @Autowired
    private ProductPurchaseOrderMapper productPurchaseOrderMapper;

    @Autowired
    private ProductPurchaseOrderProductRepository productPurchaseOrderProductRepository;

    @Autowired
    private ProductPurchaseOrderProductMapper productPurchaseOrderProductMapper;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Object queryAll(ProductPurchaseOrderQueryCriteria criteria, Pageable pageable){
        Specification<ProductPurchaseOrder> specification = new Specification<ProductPurchaseOrder>() {
            @Override
            public Predicate toPredicate(Root<ProductPurchaseOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                //状态
                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);

                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };

        Page<ProductPurchaseOrder> page = productPurchaseOrderRepository.findAll(specification, pageable);

        Page<ProductPurchaseOrderDTO> productPurchaseOrderDTOPage = page.map(productPurchaseOrderMapper::toDto);
        if(null != productPurchaseOrderDTOPage){
            List<ProductPurchaseOrderDTO> productPurchaseOrderDTOList = productPurchaseOrderDTOPage.getContent();
            if(!CollectionUtils.isEmpty(productPurchaseOrderDTOList)){
                for(ProductPurchaseOrderDTO productPurchaseOrderDTO : productPurchaseOrderDTOList){
                    String auditStatusName = AuditStatus.getName(productPurchaseOrderDTO.getAuditStatus());
                    productPurchaseOrderDTO.setAuditStatusName(auditStatusName);

                    Timestamp createTime = productPurchaseOrderDTO.getCreateTime();
                    productPurchaseOrderDTO.setCreateTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(createTime));
                }
            }
        }

        Map map = PageUtil.toPage(productPurchaseOrderDTOPage);
        return map;
    }

    @Override
    public Object queryAll(ProductPurchaseOrderQueryCriteria criteria){
        Specification<ProductPurchaseOrder> specification = new Specification<ProductPurchaseOrder>() {
            @Override
            public Predicate toPredicate(Root<ProductPurchaseOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                //状态
                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);

                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));

                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };

        List<ProductPurchaseOrder> productPurchaseOrderList = productPurchaseOrderRepository.findAll(specification);
        return productPurchaseOrderMapper.toDto(productPurchaseOrderList);
    }

    @Override
    public ProductPurchaseOrderDTO findById(Long id) {

        Optional<ProductPurchaseOrder> invoiceOptional = productPurchaseOrderRepository.findById(id);
        ProductPurchaseOrder productPurchaseOrder = invoiceOptional.get();
        ProductPurchaseOrderDTO productPurchaseOrderDTO = productPurchaseOrderMapper.toDto(productPurchaseOrder);


        List<ProductPurchaseOrderProduct> productPurchaseOrderProductList = productPurchaseOrderProductRepository.queryByProductPurchaseOrderIdAndStatusTrue(id);
        if(!CollectionUtils.isEmpty(productPurchaseOrderProductList)){
            List<ProductPurchaseOrderProductDTO> productPurchaseOrderProductDTOList = productPurchaseOrderProductMapper.toDto(productPurchaseOrderProductList);
            productPurchaseOrderDTO.setProductPurchaseOrderProductList(productPurchaseOrderProductDTOList);
        }
        return productPurchaseOrderDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductPurchaseOrderDTO create(CreateProductPurchaseOrderRequest createProductPurchaseOrderRequest) {
        ProductPurchaseOrder productPurchaseOrder = new ProductPurchaseOrder();
        BeanUtils.copyProperties(createProductPurchaseOrderRequest, productPurchaseOrder);

        String productPurchaseOrderCode = productPurchaseOrder.getProductPurchaseOrderCode();
        if(!StringUtils.hasLength(productPurchaseOrderCode)){
            throw new BadRequestException("产品采购单单据编号不能为空!");
        }

        productPurchaseOrder.setStatus(true);
        // 新增产品采购单
        productPurchaseOrderRepository.save(productPurchaseOrder);

        productPurchaseOrder = productPurchaseOrderRepository.findByProductPurchaseOrderCode(productPurchaseOrder.getProductPurchaseOrderCode());

        // 新增产品采购单产品信息
        List<ProductPurchaseOrderProductRequest> productPurchaseOrderProductRequestRequestList = createProductPurchaseOrderRequest.getProductPurchaseOrderProductList();
        if(CollectionUtils.isEmpty(productPurchaseOrderProductRequestRequestList)){
            throw new BadRequestException("产品采购单产品信息不能为空!");
        }

        for(ProductPurchaseOrderProductRequest productPurchaseOrderProductRequest : productPurchaseOrderProductRequestRequestList){
            ProductPurchaseOrderProduct productPurchaseOrderProduct = new ProductPurchaseOrderProduct();
            BeanUtils.copyProperties(productPurchaseOrderProductRequest, productPurchaseOrderProduct);
            productPurchaseOrderProduct.setStatus(true);
            productPurchaseOrderProduct.setProductPurchaseOrderId(productPurchaseOrder.getId());
            productPurchaseOrderProductRepository.save(productPurchaseOrderProduct);
        }


        ProductPurchaseOrderDTO productPurchaseOrderDTO = productPurchaseOrderMapper.toDto(productPurchaseOrder);

        List<ProductPurchaseOrderProduct> productPurchaseOrderProductList = productPurchaseOrderProductRepository.queryByProductPurchaseOrderIdAndStatusTrue(productPurchaseOrder.getId());
        if(!CollectionUtils.isEmpty(productPurchaseOrderProductList)){
            List<ProductPurchaseOrderProductDTO> productPurchaseOrderProductDTOList = new ArrayList<>();
            for(ProductPurchaseOrderProduct productPurchaseOrderProduct : productPurchaseOrderProductList){
                ProductPurchaseOrderProductDTO productPurchaseOrderProductDTO = new ProductPurchaseOrderProductDTO();
                BeanUtils.copyProperties(productPurchaseOrderProduct, productPurchaseOrderProductDTO);
                productPurchaseOrderProductDTOList.add(productPurchaseOrderProductDTO);
            }
            productPurchaseOrderDTO.setProductPurchaseOrderProductList(productPurchaseOrderProductDTOList);
        }


        /**
         * 新增消息通知
         */
        try {
            UserQueryCriteria userQueryCriteria = new UserQueryCriteria();
            List<User> userList =(List<User>)userRepository.findByEnabled(true);
            if(!CollectionUtils.isEmpty(userList)){
                List<Message> messageList = new ArrayList<>();
                for(User user : userList){
                    Message message = new Message();
                    message.setUserIdAccept(user.getId());
                    String messageContent = MessageModuleType.PRODUCT_PURCHASE.getName() + "(" + productPurchaseOrderCode  + ")" + "新录入,请查看";
                    message.setMessContent(messageContent);
                    message.setModulePath(MessageModulePath.PRODUCT_PURCHASE_LIST.getCode());
                    message.setModuleTypeName(MessageModuleType.PRODUCT_PURCHASE.getName());
                    message.setReadStatus(MessageReadStatus.NO_READ.getStatus());
                    message.setInitCode(productPurchaseOrderCode);
                    message.setModuleTypeCode(productPurchaseOrderCode);
                    message.setStatus(true);
                    message.setUserIdSend(SecurityUtils.getUserId());
                    message.setUserNameSend(SecurityUtils.getUsername());
                    message.setInitCode(productPurchaseOrderCode);
                    messageList.add(message);
                }
                messageRepository.saveAll(messageList);
            }
        }catch (Exception e){
            log.error("单据编号:插入消息失败!");
        }
        return productPurchaseOrderDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateProductPurchaseOrderRequest updateProductPurchaseOrderRequest) {
        Long productPurchaseOrderId = updateProductPurchaseOrderRequest.getId();
        Optional<ProductPurchaseOrder> productPurchaseOrderOptional = productPurchaseOrderRepository.findById(productPurchaseOrderId);
        ProductPurchaseOrder productPurchaseOrder = productPurchaseOrderOptional.get();

        // 修改产品信息之前，查询该采购中原来的产品信息
        List<ProductPurchaseOrderProduct> productPurchaseOrderProductListBeforeUpdate = productPurchaseOrderProductRepository.queryByProductPurchaseOrderIdAndStatusTrue(productPurchaseOrder.getId());
        // 将修改前产品采购单中的产品放到map中，key为产品code
        Map<String, ProductPurchaseOrderProduct> productPurchaseOrderProductMapBefore = productPurchaseOrderProductListBeforeUpdate.stream().collect(Collectors.toMap(ProductPurchaseOrderProduct::getProductCode, Function.identity()));

        List<ProductPurchaseOrderProductDTO> productPurchaseOrderProductRequestList = updateProductPurchaseOrderRequest.getProductPurchaseOrderProductList();
        if(CollectionUtils.isEmpty(productPurchaseOrderProductRequestList)){
            throw new BadRequestException("产品采购单产品不能为空!");
        }

        Map<String, ProductPurchaseOrderProductDTO> productPurchaseOrderProductMapAfter = productPurchaseOrderProductRequestList.stream().collect(Collectors.toMap(ProductPurchaseOrderProductDTO::getProductCode, Function.identity()));

        //需要将订单中原来订单对应的产品删除了的数据
        List<String> deleteTargetList = new ArrayList<>();
        //比较量个map中，key不一样的数据
        for(Map.Entry<String, ProductPurchaseOrderProduct> entry:productPurchaseOrderProductMapBefore.entrySet()){
            String consumablesCode = entry.getKey();
            //修改后的map记录对应的key在原来中是否存在
            ProductPurchaseOrderProductDTO productPurchaseOrderProductDTOTemp = productPurchaseOrderProductMapAfter.get(consumablesCode);
            if(null == productPurchaseOrderProductDTOTemp){
                deleteTargetList.add(entry.getKey());
            }

        }


        List<ProductPurchaseOrderProduct> productPurchaseOrderProductList = new ArrayList<>();
        for(ProductPurchaseOrderProductDTO productPurchaseOrderProductDTO : productPurchaseOrderProductRequestList){
            ProductPurchaseOrderProduct productPurchaseOrderProduct = new ProductPurchaseOrderProduct();
            BeanUtils.copyProperties(productPurchaseOrderProductDTO, productPurchaseOrderProduct);
            productPurchaseOrderProduct.setProductPurchaseOrderId(productPurchaseOrder.getId());
            productPurchaseOrderProduct.setStatus(true);

            if(!(!CollectionUtils.isEmpty(deleteTargetList) && deleteTargetList.contains(productPurchaseOrderProductDTO.getId()))){
                productPurchaseOrderProductList.add(productPurchaseOrderProduct);
            }
        }
        productPurchaseOrderProductRepository.saveAll(productPurchaseOrderProductList);

        /**
         * 场景描述:
         * 1.刚开始新增了 a b c三种产品
         * 2.修改的时候删除了 a c两种产品
         * 3.所以需要查修改前数据库中有的产品，再比较修改传过来的产品数据，如果修改后的在原来里面没有，需要将原来里面对应的删除
         */
        if(!CollectionUtils.isEmpty(deleteTargetList)){
            for(String prductCode : deleteTargetList){
                productPurchaseOrderProductRepository.deleteByProductCodeAndProductPurchaseOrderId(prductCode, productPurchaseOrder.getId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        productPurchaseOrderRepository.deleteById(id);
    }

    /**
     * 审核产品采购单
     * @param auditProductPurchaseOrderRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditProductPurchaseOrder(AuditProductPurchaseOrderRequest auditProductPurchaseOrderRequest) {
        Long id = auditProductPurchaseOrderRequest.getId();
        Long auditUserId = auditProductPurchaseOrderRequest.getAuditUserId();
        String auditUserName = auditProductPurchaseOrderRequest.getAuditUserName();
        String auditOpinion = auditProductPurchaseOrderRequest.getAuditOpinion();
        String auditStatus = auditProductPurchaseOrderRequest.getAuditStatus();

        Optional<ProductPurchaseOrder> optionalProductPurchaseOrder = productPurchaseOrderRepository.findById(id);

        ValidationUtil.isNull( optionalProductPurchaseOrder,"ProductPurchaseOrder","id",id);
        ProductPurchaseOrder productPurchaseOrder = optionalProductPurchaseOrder.get();
        productPurchaseOrder.setAuditUserId(auditUserId);
        productPurchaseOrder.setAuditUserName(auditUserName);
        productPurchaseOrder.setAuditOpinion(auditOpinion);
        Date auditDate = new Date();
        productPurchaseOrder.setAuditTime(auditDate);
        productPurchaseOrder.setAuditStatus(auditStatus);
        productPurchaseOrderRepository.save(productPurchaseOrder);


    }
}