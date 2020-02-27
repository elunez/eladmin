package me.zhengjie.modules.wms.purchase.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.cons.MessageModulePath;
import me.zhengjie.modules.system.cons.MessageModuleType;
import me.zhengjie.modules.system.cons.MessageReadStatus;
import me.zhengjie.modules.system.domain.Message;
import me.zhengjie.modules.system.repository.MessageRepository;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.UserDTO;
import me.zhengjie.modules.system.service.dto.UserQueryCriteria;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductDTO;
import me.zhengjie.modules.wms.purchase.cons.AuditStatus;
import me.zhengjie.modules.wms.purchase.domain.ConsumablesPurchaseOrder;
import me.zhengjie.modules.wms.purchase.domain.ConsumablesPurchaseOrderProduct;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrder;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrderProduct;
import me.zhengjie.modules.wms.purchase.repository.ConsumablesPurchaseOrderProductRepository;
import me.zhengjie.modules.wms.purchase.request.*;
import me.zhengjie.modules.wms.purchase.service.dto.*;
import me.zhengjie.modules.wms.purchase.service.mapper.ConsumablesPurchaseOrderProductMapper;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.purchase.repository.ConsumablesPurchaseOrderRepository;
import me.zhengjie.modules.wms.purchase.service.ConsumablesPurchaseOrderService;
import me.zhengjie.modules.wms.purchase.service.mapper.ConsumablesPurchaseOrderMapper;
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
public class ConsumablesPurchaseOrderServiceImpl implements ConsumablesPurchaseOrderService {

    @Autowired
    private ConsumablesPurchaseOrderRepository consumablesPurchaseOrderRepository;

    @Autowired
    private ConsumablesPurchaseOrderMapper consumablesPurchaseOrderMapper;

    @Autowired
    private ConsumablesPurchaseOrderProductRepository consumablesPurchaseOrderProductRepository;

    @Autowired
    private ConsumablesPurchaseOrderProductMapper consumablesPurchaseOrderProductMapper;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Override
    public Object queryAll(ConsumablesPurchaseOrderQueryCriteria criteria, Pageable pageable){
        Specification<ConsumablesPurchaseOrder> specification = new Specification<ConsumablesPurchaseOrder>() {
            @Override
            public Predicate toPredicate(Root<ConsumablesPurchaseOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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

        Page<ConsumablesPurchaseOrder> page = consumablesPurchaseOrderRepository.findAll(specification, pageable);

        Page<ConsumablesPurchaseOrderDTO> consumablesPurchaseOrderDTOPage = page.map(consumablesPurchaseOrderMapper::toDto);
        if(null != consumablesPurchaseOrderDTOPage){
            List<ConsumablesPurchaseOrderDTO> consumablesPurchaseOrderDTOList = consumablesPurchaseOrderDTOPage.getContent();
            if(!CollectionUtils.isEmpty(consumablesPurchaseOrderDTOList)){
                for(ConsumablesPurchaseOrderDTO consumablesPurchaseOrderDTO : consumablesPurchaseOrderDTOList){
                    String auditStatusName = AuditStatus.getName(consumablesPurchaseOrderDTO.getAuditStatus());
                    consumablesPurchaseOrderDTO.setAuditStatusName(auditStatusName);

                    Timestamp createTime = consumablesPurchaseOrderDTO.getCreateTime();
                    consumablesPurchaseOrderDTO.setCreateTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(createTime));
                }
            }
        }

        Map map = PageUtil.toPage(consumablesPurchaseOrderDTOPage);
        return map;
    }

    @Override
    public Object queryAll(ConsumablesPurchaseOrderQueryCriteria criteria){
        Specification<ConsumablesPurchaseOrder> specification = new Specification<ConsumablesPurchaseOrder>() {
            @Override
            public Predicate toPredicate(Root<ConsumablesPurchaseOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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

        List<ConsumablesPurchaseOrder> consumablesPurchaseOrderList = consumablesPurchaseOrderRepository.findAll(specification);
        return consumablesPurchaseOrderMapper.toDto(consumablesPurchaseOrderList);
    }

    @Override
    public ConsumablesPurchaseOrderDTO findById(Long id) {
        Optional<ConsumablesPurchaseOrder> consumablesPurchaseOrderOptional = consumablesPurchaseOrderRepository.findById(id);
        ConsumablesPurchaseOrder consumablesPurchaseOrder = consumablesPurchaseOrderOptional.get();
        ConsumablesPurchaseOrderDTO consumablesPurchaseOrderDTO = consumablesPurchaseOrderMapper.toDto(consumablesPurchaseOrder);
        consumablesPurchaseOrderDTO.setAuditStatusName(AuditStatus.getName(consumablesPurchaseOrder.getAuditStatus()));


        List<ConsumablesPurchaseOrderProduct> consumablesPurchaseOrderProductList = consumablesPurchaseOrderProductRepository.queryByConsumablesPurchaseOrderIdAndStatusTrue(id);
        if(!CollectionUtils.isEmpty(consumablesPurchaseOrderProductList)){
            List<ConsumablesPurchaseOrderProductDTO> consumablesPurchaseOrderProductDTOList = consumablesPurchaseOrderProductMapper.toDto(consumablesPurchaseOrderProductList);
            consumablesPurchaseOrderDTO.setConsumablesPurchaseOrderProductList(consumablesPurchaseOrderProductDTOList);
        }
        return consumablesPurchaseOrderDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ConsumablesPurchaseOrderDTO create(CreateConsumablesPurchaseOrderRequest createConsumablesPurchaseOrderRequest) {
        ConsumablesPurchaseOrder consumablesPurchaseOrder = new ConsumablesPurchaseOrder();
        BeanUtils.copyProperties(createConsumablesPurchaseOrderRequest, consumablesPurchaseOrder);

        String consumablesPurchaseOrderCode = consumablesPurchaseOrder.getConsumablesPurchaseOrderCode();
        if(!StringUtils.hasLength(consumablesPurchaseOrderCode)){
            throw new BadRequestException("耗材采购单单据编号不能为空!");
        }

        consumablesPurchaseOrder.setStatus(true);
        // 新增耗材采购单
        consumablesPurchaseOrder.setAuditStatus(AuditStatus.AUDIT_STATUS_NO_AUDIT.getCode());
        consumablesPurchaseOrderRepository.save(consumablesPurchaseOrder);

        consumablesPurchaseOrder = consumablesPurchaseOrderRepository.findByConsumablesPurchaseOrderCode(consumablesPurchaseOrder.getConsumablesPurchaseOrderCode());

        // 新增产品采购单产品信息
        List<ConsumablesPurchaseOrderProductRequest> consumablesPurchaseOrderProductRequestList = createConsumablesPurchaseOrderRequest.getConsumablesPurchaseOrderProductList();
        if(CollectionUtils.isEmpty(consumablesPurchaseOrderProductRequestList)){
            throw new BadRequestException("耗材采购单产品信息不能为空!");
        }

        for(ConsumablesPurchaseOrderProductRequest consumablesPurchaseOrderProductRequest : consumablesPurchaseOrderProductRequestList){
            ConsumablesPurchaseOrderProduct consumablesPurchaseOrderProduct = new ConsumablesPurchaseOrderProduct();
            BeanUtils.copyProperties(consumablesPurchaseOrderProductRequest, consumablesPurchaseOrderProduct);
            consumablesPurchaseOrderProduct.setStatus(true);
            consumablesPurchaseOrderProduct.setConsumablesPurchaseOrderId(consumablesPurchaseOrder.getId());
            consumablesPurchaseOrderProductRepository.save(consumablesPurchaseOrderProduct);
        }


        ConsumablesPurchaseOrderDTO consumablesPurchaseOrderDTO = consumablesPurchaseOrderMapper.toDto(consumablesPurchaseOrder);

        List<ConsumablesPurchaseOrderProduct> consumablesPurchaseOrderProductList = consumablesPurchaseOrderProductRepository.queryByConsumablesPurchaseOrderIdAndStatusTrue(consumablesPurchaseOrder.getId());
        if(!CollectionUtils.isEmpty(consumablesPurchaseOrderProductList)){
            List<ConsumablesPurchaseOrderProductDTO> consumablesPurchaseOrderProductDTOList = new ArrayList<>();
            for(ConsumablesPurchaseOrderProduct consumablesPurchaseOrderProduct : consumablesPurchaseOrderProductList){
                ConsumablesPurchaseOrderProductDTO consumablesPurchaseOrderProductDTO = new ConsumablesPurchaseOrderProductDTO();
                BeanUtils.copyProperties(consumablesPurchaseOrderProduct, consumablesPurchaseOrderProductDTO);
                consumablesPurchaseOrderProductDTOList.add(consumablesPurchaseOrderProductDTO);
            }
            consumablesPurchaseOrderDTO.setConsumablesPurchaseOrderProductList(consumablesPurchaseOrderProductDTOList);
        }


        /**
         * 新增消息通知
         */
        try {
            // 查看所有用户
            UserQueryCriteria userQueryCriteria = new UserQueryCriteria();
            List<UserDTO> userDTOList =(List<UserDTO>)userService.queryAll(userQueryCriteria);
            if(!CollectionUtils.isEmpty(userDTOList)){
                List<Message> messageList = new ArrayList<>();
                for(UserDTO userDTO : userDTOList){
                    Message message = new Message();
                    message.setUserIdAccept(userDTO.getId());
                    String messageContent = MessageModuleType.CONSUMABLES_PURCHASE.getName() + "(" + consumablesPurchaseOrderCode + ")";
                    message.setMessContent(messageContent);
                    message.setModulePath(MessageModulePath.CONSUMABLES_PURCHASE_LIST.getCode());
                    message.setModuleTypeName(MessageModuleType.CONSUMABLES_PURCHASE.getCode());
                    message.setReadStatus(MessageReadStatus.NO_READ.getStatus());
                    message.setInitCode(consumablesPurchaseOrderCode);
                    messageList.add(message);
                }
                messageRepository.saveAll(messageList);
            }
        }catch (Exception e){
            log.error("单据编号:插入消息失败!");
        }
        return consumablesPurchaseOrderDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateConsumablesPurchaseOrderRequest updateConsumablesPurchaseOrderRequest) {
        Long consumablesPurchaseOrderId = updateConsumablesPurchaseOrderRequest.getId();
        Optional<ConsumablesPurchaseOrder> consumablesPurchaseOrderOptional = consumablesPurchaseOrderRepository.findById(consumablesPurchaseOrderId);
        ConsumablesPurchaseOrder consumablesPurchaseOrder = consumablesPurchaseOrderOptional.get();

        // 修改产品信息之前，查询该订单中原来的产品信息，key为产品code
        List<ConsumablesPurchaseOrderProduct> consumablesPurchaseOrderProductListBeforeUpdate = consumablesPurchaseOrderProductRepository.queryByConsumablesPurchaseOrderIdAndStatusTrue(consumablesPurchaseOrder.getId());
        Map<String, ConsumablesPurchaseOrderProduct> consumablesPurchaseOrderProductMapBefore = consumablesPurchaseOrderProductListBeforeUpdate.stream().collect(Collectors.toMap(ConsumablesPurchaseOrderProduct::getConsumablesCode, Function.identity()));

        List<ConsumablesPurchaseOrderProductDTO> consumablesPurchaseOrderProductRequestList = updateConsumablesPurchaseOrderRequest.getConsumablesPurchaseOrderProductList();
        if(CollectionUtils.isEmpty(consumablesPurchaseOrderProductRequestList)){
            throw new BadRequestException("耗材采购单产品不能为空!");
        }

        Map<String, ConsumablesPurchaseOrderProductDTO> invoiceProductMapAfter = consumablesPurchaseOrderProductRequestList.stream().collect(Collectors.toMap(ConsumablesPurchaseOrderProductDTO::getConsumablesCode, Function.identity()));

        //需要将订单中原来订单对应的产品删除了的数据
        List<String> deleteTargetList = new ArrayList<>();
        //比较量个map中，key不一样的数据
        for(Map.Entry<String, ConsumablesPurchaseOrderProduct> entry:consumablesPurchaseOrderProductMapBefore.entrySet()){
            String consumablesCode = entry.getKey();
            //修改后的map记录对应的key在原来中是否存在
            ConsumablesPurchaseOrderProductDTO consumablesPurchaseOrderProductDTOTemp = invoiceProductMapAfter.get(consumablesCode);
            if(null == consumablesPurchaseOrderProductDTOTemp){
                deleteTargetList.add(entry.getKey());
            }

        }


        List<ConsumablesPurchaseOrderProduct> consumablesPurchaseOrderProductList = new ArrayList<>();
        for(ConsumablesPurchaseOrderProductDTO consumablesPurchaseOrderProductDTO : consumablesPurchaseOrderProductRequestList){
            ConsumablesPurchaseOrderProduct consumablesPurchaseOrderProduct = new ConsumablesPurchaseOrderProduct();
            BeanUtils.copyProperties(consumablesPurchaseOrderProductDTO, consumablesPurchaseOrderProduct);
            consumablesPurchaseOrderProduct.setConsumablesPurchaseOrderId(consumablesPurchaseOrder.getId());
            consumablesPurchaseOrderProduct.setStatus(true);

            if(!(!CollectionUtils.isEmpty(deleteTargetList) && deleteTargetList.contains(consumablesPurchaseOrderProductDTO.getId()))){
                consumablesPurchaseOrderProductList.add(consumablesPurchaseOrderProduct);
            }
        }
        consumablesPurchaseOrderProductRepository.saveAll(consumablesPurchaseOrderProductList);

        /**
         * 场景描述:
         * 1.刚开始新增了 a b c三种产品
         * 2.修改的时候删除了 a c两种产品
         * 3.所以需要查修改前数据库中有的产品，再比较修改传过来的产品数据，如果修改后的在原来里面没有，需要将原来里面对应的删除
         */
        if(!CollectionUtils.isEmpty(deleteTargetList)){
            for(String prductCode : deleteTargetList){
                consumablesPurchaseOrderProductRepository.deleteByProductCodeAndConsumablesPurchaseOrderId(prductCode, consumablesPurchaseOrder.getId());
            }
        }
    }


    /**
     * 审核耗材采购单
     * @param auditConsumablesPurchaseOrderRequest
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditConsumablesPurchaseOrder(AuditConsumablesPurchaseOrderRequest auditConsumablesPurchaseOrderRequest) {
        Long id = auditConsumablesPurchaseOrderRequest.getId();
        Long auditUserId = auditConsumablesPurchaseOrderRequest.getAuditUserId();
        String auditUserName = auditConsumablesPurchaseOrderRequest.getAuditUserName();
        String auditOpinion = auditConsumablesPurchaseOrderRequest.getAuditOpinion();
        String auditStatus = auditConsumablesPurchaseOrderRequest.getAuditStatus();

        Optional<ConsumablesPurchaseOrder> optionalProductPurchaseOrder = consumablesPurchaseOrderRepository.findById(id);

        ValidationUtil.isNull( optionalProductPurchaseOrder,"ProductPurchaseOrder","id",id);
        ConsumablesPurchaseOrder consumablesPurchaseOrder = optionalProductPurchaseOrder.get();
        consumablesPurchaseOrder.setAuditUserId(auditUserId);
        consumablesPurchaseOrder.setAuditUserName(auditUserName);
        consumablesPurchaseOrder.setAuditOpinion(auditOpinion);
        Date date = new Date();
        consumablesPurchaseOrder.setAuditTime(date);
        consumablesPurchaseOrder.setAuditStatus(auditStatus);
        consumablesPurchaseOrderRepository.save(consumablesPurchaseOrder);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        consumablesPurchaseOrderRepository.deleteById(id);
    }
}