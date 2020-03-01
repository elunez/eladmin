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
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificate;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificateProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.repository.OutSourceInspectionCertificateProductRepository;
import me.zhengjie.modules.wms.outSourceProductSheet.request.*;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.*;
import me.zhengjie.modules.wms.outSourceProductSheet.service.mapper.OutSourceInspectionCertificateProductMapper;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.outSourceProductSheet.repository.OutSourceInspectionCertificateRepository;
import me.zhengjie.modules.wms.outSourceProductSheet.service.OutSourceInspectionCertificateService;
import me.zhengjie.modules.wms.outSourceProductSheet.service.mapper.OutSourceInspectionCertificateMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
* @date 2019-10-01
*/
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OutSourceInspectionCertificateServiceImpl implements OutSourceInspectionCertificateService {

    @Autowired
    private OutSourceInspectionCertificateRepository outSourceInspectionCertificateRepository;

    @Autowired
    private OutSourceInspectionCertificateMapper outSourceInspectionCertificateMapper;


    @Autowired
    private OutSourceInspectionCertificateProductRepository outSourceInspectionCertificateProductRepository;

    @Autowired
    private OutSourceInspectionCertificateProductMapper outSourceInspectionCertificateProductMapper;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Object queryAll(OutSourceInspectionCertificateQueryCriteria criteria, Pageable pageable){
        Specification<OutSourceInspectionCertificate> specification = new Specification<OutSourceInspectionCertificate>() {
            @Override
            public Predicate toPredicate(Root<OutSourceInspectionCertificate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);


                String outSourceProcessSheetCode = criteria.getOutSourceProcessSheetCode();
                if(!StringUtils.isEmpty(outSourceProcessSheetCode)){
                    Predicate outSourceProcessSheetCodePredicate = criteriaBuilder.like(root.get("outSourceProcessSheetCode"), "%"+ outSourceProcessSheetCode + "%");
                    targetPredicateList.add(outSourceProcessSheetCodePredicate);
                }

                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };
        Page<OutSourceInspectionCertificate> page = outSourceInspectionCertificateRepository.findAll(specification,pageable);
        return PageUtil.toPage(page.map(outSourceInspectionCertificateMapper::toDto));
    }

    @Override
    public Object queryAll(OutSourceInspectionCertificateQueryCriteria criteria){
        Specification<OutSourceInspectionCertificate> specification = new Specification<OutSourceInspectionCertificate>() {
            @Override
            public Predicate toPredicate(Root<OutSourceInspectionCertificate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        return outSourceInspectionCertificateMapper.toDto(outSourceInspectionCertificateRepository.findAll(specification));
    }

    @Override
    public OutSourceInspectionCertificateDTO findById(Long id) {
        Optional<OutSourceInspectionCertificate> invoiceOptional = outSourceInspectionCertificateRepository.findById(id);
        OutSourceInspectionCertificate outSourceInspectionCertificate = invoiceOptional.get();
        OutSourceInspectionCertificateDTO outSourceInspectionCertificateDTO = outSourceInspectionCertificateMapper.toDto(outSourceInspectionCertificate);


        List<OutSourceInspectionCertificateProduct> outSourceInspectionCertificateProductList = outSourceInspectionCertificateProductRepository.queryByOutSourceInspectionCertificateIdAndStatusTrue(id);
        if(!CollectionUtils.isEmpty(outSourceInspectionCertificateProductList)){
            List<OutSourceInspectionCertificateProductDTO> outSourceInspectionCertificateProductDTOList = outSourceInspectionCertificateProductMapper.toDto(outSourceInspectionCertificateProductList);
            outSourceInspectionCertificateDTO.setOutSourceInspectionCertificateProductList(outSourceInspectionCertificateProductDTOList);
        }
        return outSourceInspectionCertificateDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutSourceInspectionCertificateDTO create(CreateOutSourceInspectionCertificateRequest createOutSourceInspectionCertificateRequest) {
        OutSourceInspectionCertificate outSourceInspectionCertificate = new OutSourceInspectionCertificate();
        BeanUtils.copyProperties(createOutSourceInspectionCertificateRequest, outSourceInspectionCertificate);

        String outSourceInspectionCertificateCode = outSourceInspectionCertificate.getOutSourceInspectionCertificateCode();
        if(!StringUtils.hasLength(outSourceInspectionCertificateCode)){
            throw new BadRequestException("委外验收单单据编号不能为空!");
        }

        outSourceInspectionCertificate.setStatus(true);
        // 新增委外验收单
        outSourceInspectionCertificateRepository.save(outSourceInspectionCertificate);

        outSourceInspectionCertificate = outSourceInspectionCertificateRepository.findByOutSourceInspectionCertificateCode(outSourceInspectionCertificateCode);

        // 新增委外验收单产品信息
        List<OutSourceInspectionCertificateProductRequest> outSourceInspectionCertificateProductRequestList = createOutSourceInspectionCertificateRequest.getOutSourceInspectionCertificateProductList();
        if(CollectionUtils.isEmpty(outSourceInspectionCertificateProductRequestList)){
            throw new BadRequestException("委外验收单产品信息不能为空!");
        }

        for(OutSourceInspectionCertificateProductRequest outSourceInspectionCertificateProductRequest : outSourceInspectionCertificateProductRequestList){
            OutSourceInspectionCertificateProduct outSourceInspectionCertificateProduct = new OutSourceInspectionCertificateProduct();
            BeanUtils.copyProperties(outSourceInspectionCertificateProductRequest, outSourceInspectionCertificateProduct);
            outSourceInspectionCertificateProduct.setStatus(true);
            outSourceInspectionCertificateProduct.setOutSourceInspectionCertificateId(outSourceInspectionCertificate.getId());
            outSourceInspectionCertificateProductRepository.save(outSourceInspectionCertificateProduct);
        }


        OutSourceInspectionCertificateDTO outSourceInspectionCertificateDTO = outSourceInspectionCertificateMapper.toDto(outSourceInspectionCertificate);

        List<OutSourceInspectionCertificateProduct> outSourceInspectionCertificateProductList = outSourceInspectionCertificateProductRepository.queryByOutSourceInspectionCertificateIdAndStatusTrue(outSourceInspectionCertificate.getId());
        if(!CollectionUtils.isEmpty(outSourceInspectionCertificateProductList)){
            List<OutSourceInspectionCertificateProductDTO> outSourceInspectionCertificateProductDTOList = new ArrayList<>();
            for(OutSourceInspectionCertificateProduct outSourceInspectionCertificateProduct : outSourceInspectionCertificateProductList){
                OutSourceInspectionCertificateProductDTO outSourceInspectionCertificateProductDTO = new OutSourceInspectionCertificateProductDTO();
                BeanUtils.copyProperties(outSourceInspectionCertificateProduct, outSourceInspectionCertificateProductDTO);
                outSourceInspectionCertificateProductDTOList.add(outSourceInspectionCertificateProductDTO);
            }
            outSourceInspectionCertificateDTO.setOutSourceInspectionCertificateProductList(outSourceInspectionCertificateProductDTOList);
        }

        /**
         * 新增消息通知
         */
        try {
            // 查看所有用户
            UserQueryCriteria userQueryCriteria = new UserQueryCriteria();
            List<User> userList =(List<User>)userRepository.findByEnabled(true);
            if(!CollectionUtils.isEmpty(userList)){
                List<Message> messageList = new ArrayList<>();
                for(User user : userList){
                    Message message = new Message();
                    message.setUserIdAccept(user.getId());
                    String messageContent = MessageModuleType.OUT_SOURCE_INSPECTION_CERTIFICATE.getName() + "(" + outSourceInspectionCertificateCode + ")";
                    message.setMessContent(messageContent);
                    message.setModulePath(MessageModulePath.OUT_SOURCE_INSPECTION_CERTIFICATE_LIST.getCode());
                    message.setModuleTypeName(MessageModuleType.OUT_SOURCE_INSPECTION_CERTIFICATE.getName());
                    message.setReadStatus(MessageReadStatus.NO_READ.getStatus());
                    message.setModuleTypeCode(outSourceInspectionCertificateCode);
                    message.setStatus(true);
                    message.setUserIdSend(SecurityUtils.getUserId());
                    message.setUserNameSend(SecurityUtils.getUsername());
                    message.setInitCode(outSourceInspectionCertificateCode);
                    messageList.add(message);
                }
                messageRepository.saveAll(messageList);
            }
        }catch (Exception e){
            log.error("单据编号:插入消息失败!");
        }
        return outSourceInspectionCertificateDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateOutSourceInspectionCertificateRequest updateOutSourceInspectionCertificateRequest) {
        OutSourceInspectionCertificate outSourceInspectionCertificate = new OutSourceInspectionCertificate();
        BeanUtils.copyProperties(updateOutSourceInspectionCertificateRequest, outSourceInspectionCertificate);

        outSourceInspectionCertificate.setStatus(true);

        outSourceInspectionCertificateRepository.save(outSourceInspectionCertificate);

        // 修改产品信息之前，查询该订单中原来的产品信息，key为产品code
        List<OutSourceInspectionCertificateProduct> outSourceInspectionCertificateProductListBeforeUpdate = outSourceInspectionCertificateProductRepository.queryByOutSourceInspectionCertificateIdAndStatusTrue(outSourceInspectionCertificate.getId());
        Map<String, OutSourceInspectionCertificateProduct> outSourceInspectionCertificateProductMapBefore = outSourceInspectionCertificateProductListBeforeUpdate.stream().collect(Collectors.toMap(OutSourceInspectionCertificateProduct::getProductCode, Function.identity()));

        List<OutSourceInspectionCertificateProductDTO> outSourceInspectionCertificateProductRequestList = updateOutSourceInspectionCertificateRequest.getOutSourceInspectionCertificateProductList();
        if(CollectionUtils.isEmpty(outSourceInspectionCertificateProductRequestList)){
            throw new BadRequestException("委外验收单产品不能为空!");
        }

        Map<String, OutSourceInspectionCertificateProductDTO> invoiceProductMapAfter = outSourceInspectionCertificateProductRequestList.stream().collect(Collectors.toMap(OutSourceInspectionCertificateProductDTO::getProductCode, Function.identity()));

        //需要将订单中原来订单对应的产品删除了的数据
        List<String> deleteTargetList = new ArrayList<>();
        //比较量个map中，key不一样的数据
        for(Map.Entry<String, OutSourceInspectionCertificateProduct> entry:outSourceInspectionCertificateProductMapBefore.entrySet()){
            String productCode = entry.getKey();
            //修改后的map记录对应的key在原来中是否存在
            OutSourceInspectionCertificateProductDTO outSourceInspectionCertificateProductDTOTemp = invoiceProductMapAfter.get(productCode);
            if(null == outSourceInspectionCertificateProductDTOTemp){
                deleteTargetList.add(entry.getKey());
            }

        }


        List<OutSourceInspectionCertificateProduct> outSourceInspectionCertificateProductList = new ArrayList<>();
        for(OutSourceInspectionCertificateProductDTO outSourceInspectionCertificateProductDTO : outSourceInspectionCertificateProductRequestList){
            OutSourceInspectionCertificateProduct outSourceInspectionCertificateProduct = new OutSourceInspectionCertificateProduct();
            BeanUtils.copyProperties(outSourceInspectionCertificateProductDTO, outSourceInspectionCertificateProduct);
            outSourceInspectionCertificateProduct.setOutSourceInspectionCertificateId(outSourceInspectionCertificate.getId());
            outSourceInspectionCertificateProduct.setStatus(true);

            if(!(!CollectionUtils.isEmpty(deleteTargetList) && deleteTargetList.contains(outSourceInspectionCertificateProductDTO.getId()))){
                outSourceInspectionCertificateProductList.add(outSourceInspectionCertificateProduct);
            }
        }
        outSourceInspectionCertificateProductRepository.saveAll(outSourceInspectionCertificateProductList);

        /**
         * 场景描述:
         * 1.刚开始新增了 a b c三种产品
         * 2.修改的时候删除了 a c两种产品
         * 3.所以需要查修改前数据库中有的产品，再比较修改传过来的产品数据，如果修改后的在原来里面没有，需要将原来里面对应的删除
         */
        if(!CollectionUtils.isEmpty(deleteTargetList)){
            for(String prductCode : deleteTargetList){
                outSourceInspectionCertificateProductRepository.deleteByProductCodeAndOutSourceInspectionCertificateId(prductCode, outSourceInspectionCertificate.getId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        outSourceInspectionCertificateRepository.deleteById(id);
    }
}