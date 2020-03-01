package me.zhengjie.modules.wms.outSourceProductSheet.service.impl;

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
import me.zhengjie.modules.wms.invoice.domain.Invoice;
import me.zhengjie.modules.wms.invoice.domain.InvoiceProduct;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceDTO;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceDetailDTO;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceProductDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.repository.OutSourceProcessSheetProductRepository;
import me.zhengjie.modules.wms.outSourceProductSheet.request.CreateOutSourceProcessSheetRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.request.OutSourceProcessSheetProductRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.request.QueryOutSourceProcessSheetProductRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.request.UpdateOutSourceProcessSheetRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.mapper.OutSourceProcessSheetProductMapper;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.outSourceProductSheet.repository.OutSourceProcessSheetRepository;
import me.zhengjie.modules.wms.outSourceProductSheet.service.OutSourceProcessSheetService;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetQueryCriteria;
import me.zhengjie.modules.wms.outSourceProductSheet.service.mapper.OutSourceProcessSheetMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
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
 * 委外加工单服务累
* @author jie
* @date 2019-08-17
*/
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OutSourceProcessSheetServiceImpl implements OutSourceProcessSheetService {

    @Autowired
    private OutSourceProcessSheetRepository outSourceProcessSheetRepository;

    @Autowired
    private OutSourceProcessSheetProductRepository outSourceProcessSheetProductRepository;

    @Autowired
    private OutSourceProcessSheetMapper outSourceProcessSheetMapper;

    @Autowired
    private OutSourceProcessSheetProductMapper outSourceProcessSheetProductMapper;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Object queryAll(OutSourceProcessSheetQueryCriteria criteria, Pageable pageable){
        Specification<OutSourceProcessSheet> specification = new Specification<OutSourceProcessSheet>() {
            @Override
            public Predicate toPredicate(Root<OutSourceProcessSheet> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        Page<OutSourceProcessSheet> page = outSourceProcessSheetRepository.findAll(specification,pageable);

        Page<OutSourceProcessSheetDTO> outSourceProcessSheetDTOPage = page.map(outSourceProcessSheetMapper::toDto);
        if(null != outSourceProcessSheetDTOPage){
            List<OutSourceProcessSheetDTO> outSourceProcessSheetDTOList = outSourceProcessSheetDTOPage.getContent();
            if(!CollectionUtils.isEmpty(outSourceProcessSheetDTOList)){
                for(OutSourceProcessSheetDTO outSourceProcessSheetDTO : outSourceProcessSheetDTOList){
                    Timestamp createTime = outSourceProcessSheetDTO.getCreateTime();
                    outSourceProcessSheetDTO.setCreateTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(createTime));

                    // 查询对应的委外加工单的产品信息
                    List<OutSourceProcessSheetProduct> outSourceProcessSheetProductList = outSourceProcessSheetProductRepository.queryByOutSourceProcessSheetIdAndStatusTrue(outSourceProcessSheetDTO.getId());
                    if(!CollectionUtils.isEmpty(outSourceProcessSheetProductList)){
                        List<OutSourceProcessSheetProductDTO> outSourceProcessSheetProductDTOList = outSourceProcessSheetProductMapper.toDto(outSourceProcessSheetProductList);
                        outSourceProcessSheetDTO.setOutSourceProcessSheetProductList(outSourceProcessSheetProductDTOList);
                    }
                }
            }
        }
        Map map = PageUtil.toPage(outSourceProcessSheetDTOPage);
        return map;
    }

    @Override
    public Object queryAll(OutSourceProcessSheetQueryCriteria criteria){
        Specification<OutSourceProcessSheet> specification = new Specification<OutSourceProcessSheet>() {
            @Override
            public Predicate toPredicate(Root<OutSourceProcessSheet> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        return outSourceProcessSheetMapper.toDto(outSourceProcessSheetRepository.findAll(specification));
    }

    @Override
    public OutSourceProcessSheetDTO findById(Long id) {
//        Optional<OutSourceProcessSheet> sOutSourceProcessSheet = outSourceProcessSheetRepository.findById(id);
//        ValidationUtil.isNull(sOutSourceProcessSheet,"SOutSourceProcessSheet","id",id);
//        return outSourceProcessSheetMapper.toDto(sOutSourceProcessSheet.get());


        Optional<OutSourceProcessSheet> invoiceOptional = outSourceProcessSheetRepository.findById(id);
        OutSourceProcessSheet outSourceProcessSheet = invoiceOptional.get();
        OutSourceProcessSheetDTO outSourceProcessSheetDTO = outSourceProcessSheetMapper.toDto(outSourceProcessSheet);


        List<OutSourceProcessSheetProduct> outSourceProcessSheetProductList = outSourceProcessSheetProductRepository.queryByOutSourceProcessSheetIdAndStatusTrue(id);
        if(!CollectionUtils.isEmpty(outSourceProcessSheetProductList)){
            List<OutSourceProcessSheetProductDTO> outSourceProcessSheetProductDTOList = outSourceProcessSheetProductMapper.toDto(outSourceProcessSheetProductList);
            outSourceProcessSheetDTO.setOutSourceProcessSheetProductList(outSourceProcessSheetProductDTOList);
        }
        return outSourceProcessSheetDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutSourceProcessSheetDTO create(CreateOutSourceProcessSheetRequest createOutSourceProcessSheetRequest) {
        OutSourceProcessSheet outSourceProcessSheet = new OutSourceProcessSheet();
        BeanUtils.copyProperties(createOutSourceProcessSheetRequest, outSourceProcessSheet);

        String outSourceProcessSheetCode = outSourceProcessSheet.getOutSourceProcessSheetCode();
        if(!StringUtils.hasLength(outSourceProcessSheetCode)){
            throw new BadRequestException("委外加工单单据编号不能为空!");
        }

        outSourceProcessSheet.setStatus(true);
        // 新增委外加工单
        outSourceProcessSheetRepository.save(outSourceProcessSheet);

        outSourceProcessSheet = outSourceProcessSheetRepository.findByOutSourceProcessSheetCode(outSourceProcessSheetCode);

        // 新增委外加工单产品信息
        List<OutSourceProcessSheetProductRequest> outSourceProcessSheetProductRequestList = createOutSourceProcessSheetRequest.getOutSourceProcessSheetProductList();
        if(CollectionUtils.isEmpty(outSourceProcessSheetProductRequestList)){
            throw new BadRequestException("委外加工单产品信息不能为空!");
        }

        for(OutSourceProcessSheetProductRequest outSourceProcessSheetProductRequest : outSourceProcessSheetProductRequestList){
            OutSourceProcessSheetProduct outSourceProcessSheetProduct = new OutSourceProcessSheetProduct();
            BeanUtils.copyProperties(outSourceProcessSheetProductRequest, outSourceProcessSheetProduct);
            outSourceProcessSheetProduct.setStatus(true);
            outSourceProcessSheetProduct.setOutSourceProcessSheetId(outSourceProcessSheet.getId());
            outSourceProcessSheetProductRepository.save(outSourceProcessSheetProduct);
        }


        OutSourceProcessSheetDTO outSourceProcessSheetDTO = outSourceProcessSheetMapper.toDto(outSourceProcessSheet);

        List<OutSourceProcessSheetProduct> outSourceProcessSheetProductList = outSourceProcessSheetProductRepository.queryByOutSourceProcessSheetIdAndStatusTrue(outSourceProcessSheet.getId());
        if(!CollectionUtils.isEmpty(outSourceProcessSheetProductList)){
            List<OutSourceProcessSheetProductDTO> outSourceProcessSheetProductDTOList = new ArrayList<>();
            for(OutSourceProcessSheetProduct outSourceProcessSheetProduct : outSourceProcessSheetProductList){
                OutSourceProcessSheetProductDTO outSourceProcessSheetProductDTO = new OutSourceProcessSheetProductDTO();
                BeanUtils.copyProperties(outSourceProcessSheetProduct, outSourceProcessSheetProductDTO);
                outSourceProcessSheetProductDTOList.add(outSourceProcessSheetProductDTO);
            }
            outSourceProcessSheetDTO.setOutSourceProcessSheetProductList(outSourceProcessSheetProductDTOList);
        }

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
                    String messageContent = MessageModuleType.OUT_SOURCE_PROCESS.getName() + "(" + outSourceProcessSheetCode + ")" + "新录入,请查看";
                    message.setMessContent(messageContent);
                    message.setModulePath(MessageModulePath.OUT_SOURCE_LIST.getCode());
                    message.setModuleTypeName(MessageModuleType.OUT_SOURCE_PROCESS.getName());
                    message.setReadStatus(MessageReadStatus.NO_READ.getStatus());
                    message.setModuleTypeCode(outSourceProcessSheetCode);
                    message.setStatus(true);
                    message.setUserIdSend(SecurityUtils.getUserId());
                    message.setUserNameSend(SecurityUtils.getUsername());
                    message.setInitCode(outSourceProcessSheetCode);
                    messageList.add(message);
                }
                messageRepository.saveAll(messageList);
            }
        }catch (Exception e){
            log.error("单据编号:插入消息失败!");
        }

        return outSourceProcessSheetDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateOutSourceProcessSheetRequest updateOutSourceProcessSheetRequest) {
        Long outSourceProcessSheetId = updateOutSourceProcessSheetRequest.getId();
        Optional<OutSourceProcessSheet> outSourceProcessSheetOptional = outSourceProcessSheetRepository.findById(outSourceProcessSheetId);
        OutSourceProcessSheet outSourceProcessSheet = outSourceProcessSheetOptional.get();
        BeanUtils.copyProperties(updateOutSourceProcessSheetRequest, outSourceProcessSheet);

        outSourceProcessSheet.setStatus(true);

        outSourceProcessSheetRepository.save(outSourceProcessSheet);

        // 修改产品信息之前，查询该订单中原来的产品信息，key为产品code
        List<OutSourceProcessSheetProduct> outSourceProcessSheetProductListBeforeUpdate = outSourceProcessSheetProductRepository.queryByOutSourceProcessSheetIdAndStatusTrue(outSourceProcessSheet.getId());
        Map<String, OutSourceProcessSheetProduct> outSourceProcessSheetProductMapBefore = outSourceProcessSheetProductListBeforeUpdate.stream().collect(Collectors.toMap(OutSourceProcessSheetProduct::getProductCode, Function.identity()));

        List<OutSourceProcessSheetProductDTO> outSourceProcessSheetProductRequestList = updateOutSourceProcessSheetRequest.getOutSourceProcessSheetProductList();
        if(CollectionUtils.isEmpty(outSourceProcessSheetProductRequestList)){
            throw new BadRequestException("委外加工单产品不能为空!");
        }

        Map<String, OutSourceProcessSheetProductDTO> invoiceProductMapAfter = outSourceProcessSheetProductRequestList.stream().collect(Collectors.toMap(OutSourceProcessSheetProductDTO::getProductCode, Function.identity()));

        //需要将订单中原来订单对应的产品删除了的数据
        List<String> deleteTargetList = new ArrayList<>();
        //比较量个map中，key不一样的数据
        for(Map.Entry<String, OutSourceProcessSheetProduct> entry:outSourceProcessSheetProductMapBefore.entrySet()){
            String productCode = entry.getKey();
            //修改后的map记录对应的key在原来中是否存在
            OutSourceProcessSheetProductDTO outSourceProcessSheetProductDTOTemp = invoiceProductMapAfter.get(productCode);
            if(null == outSourceProcessSheetProductDTOTemp){
                deleteTargetList.add(entry.getKey());
            }

        }


        List<OutSourceProcessSheetProduct> outSourceProcessSheetProductList = new ArrayList<>();
        for(OutSourceProcessSheetProductDTO outSourceProcessSheetProductDTO : outSourceProcessSheetProductRequestList){
            OutSourceProcessSheetProduct outSourceProcessSheetProduct = new OutSourceProcessSheetProduct();
            BeanUtils.copyProperties(outSourceProcessSheetProductDTO, outSourceProcessSheetProduct);
            outSourceProcessSheetProduct.setOutSourceProcessSheetId(outSourceProcessSheet.getId());
            outSourceProcessSheetProduct.setStatus(true);

            if(!(!CollectionUtils.isEmpty(deleteTargetList) && deleteTargetList.contains(outSourceProcessSheetProductDTO.getId()))){
                outSourceProcessSheetProductList.add(outSourceProcessSheetProduct);
            }
        }
        outSourceProcessSheetProductRepository.saveAll(outSourceProcessSheetProductList);

        /**
         * 场景描述:
         * 1.刚开始新增了 a b c三种产品
         * 2.修改的时候删除了 a c两种产品
         * 3.所以需要查修改前数据库中有的产品，再比较修改传过来的产品数据，如果修改后的在原来里面没有，需要将原来里面对应的删除
         */
        if(!CollectionUtils.isEmpty(deleteTargetList)){
            for(String prductCode : deleteTargetList){
                outSourceProcessSheetProductRepository.deleteByProductCodeAndOutSourceProcessSheetId(prductCode, outSourceProcessSheet.getId());
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        outSourceProcessSheetRepository.deleteById(id);
    }

}