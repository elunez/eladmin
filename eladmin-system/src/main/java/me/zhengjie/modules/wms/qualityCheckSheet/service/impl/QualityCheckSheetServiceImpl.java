package me.zhengjie.modules.wms.qualityCheckSheet.service.impl;

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
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificate;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificateProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.request.OutSourceProcessSheetProductRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateProductDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductDTO;
import me.zhengjie.modules.wms.qualityCheckSheet.domain.QualityCheckSheet;
import me.zhengjie.modules.wms.qualityCheckSheet.domain.QualityCheckSheetProduct;
import me.zhengjie.modules.wms.qualityCheckSheet.repository.QualityCheckSheetProductRepository;
import me.zhengjie.modules.wms.qualityCheckSheet.request.CreateQualityCheckSheetRequest;
import me.zhengjie.modules.wms.qualityCheckSheet.request.QualityCheckSheetProductRequest;
import me.zhengjie.modules.wms.qualityCheckSheet.request.UpdateQualityCheckSheetRequest;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetProductDTO;
import me.zhengjie.modules.wms.qualityCheckSheet.service.mapper.QualityCheckSheetProductMapper;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.qualityCheckSheet.repository.QualityCheckSheetRepository;
import me.zhengjie.modules.wms.qualityCheckSheet.service.QualityCheckSheetService;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetDTO;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetQueryCriteria;
import me.zhengjie.modules.wms.qualityCheckSheet.service.mapper.QualityCheckSheetMapper;
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
* @author huangxingxing
* @date 2019-11-12
*/
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QualityCheckSheetServiceImpl implements QualityCheckSheetService {

    @Autowired
    private QualityCheckSheetRepository qualityCheckSheetRepository;

    @Autowired
    private QualityCheckSheetMapper qualityCheckSheetMapper;

    @Autowired
    private QualityCheckSheetProductRepository qualityCheckSheetProductRepository;

    @Autowired
    private QualityCheckSheetProductMapper qualityCheckSheetProductMapper;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Override
    public Object queryAll(QualityCheckSheetQueryCriteria criteria, Pageable pageable){
        Specification<QualityCheckSheet> specification = new Specification<QualityCheckSheet>() {
            @Override
            public Predicate toPredicate(Root<QualityCheckSheet> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        Page<QualityCheckSheet> page = qualityCheckSheetRepository.findAll(specification,pageable);

        Page<QualityCheckSheetDTO> qualityCheckSheetDTOPage = page.map(qualityCheckSheetMapper::toDto);
        if(null != qualityCheckSheetDTOPage){
            List<QualityCheckSheetDTO> qualityCheckSheetDTOList = qualityCheckSheetDTOPage.getContent();
            if(!CollectionUtils.isEmpty(qualityCheckSheetDTOList)){
                for(QualityCheckSheetDTO qualityCheckSheetDTO : qualityCheckSheetDTOList){
                    Timestamp createTime = qualityCheckSheetDTO.getCreateTime();
                    qualityCheckSheetDTO.setCreateTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(createTime));

                    Timestamp updateTime = qualityCheckSheetDTO.getUpdateTime();
                    qualityCheckSheetDTO.setUpdateTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(updateTime));

                    // 查询对应的委外加工单的产品信息
                    List<QualityCheckSheetProduct> outSourceProcessSheetProductList = qualityCheckSheetProductRepository.queryByQualityCheckSheetIdAndStatusTrue(qualityCheckSheetDTO.getId());
                    if(!CollectionUtils.isEmpty(outSourceProcessSheetProductList)){
                        List<QualityCheckSheetProductDTO> qualityCheckSheetProductDTOList = qualityCheckSheetProductMapper.toDto(outSourceProcessSheetProductList);
                        qualityCheckSheetDTO.setQualityCheckSheetProductList(qualityCheckSheetProductDTOList);
                    }
                }
            }
        }
        Map map = PageUtil.toPage(qualityCheckSheetDTOPage);
        return map;
    }

    @Override
    public Object queryAll(QualityCheckSheetQueryCriteria criteria){
        Specification<QualityCheckSheetQueryCriteria> specification = new Specification<QualityCheckSheetQueryCriteria>() {
            @Override
            public Predicate toPredicate(Root<QualityCheckSheetQueryCriteria> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        return qualityCheckSheetMapper.toDto(qualityCheckSheetRepository.findAll(specification));
    }

    @Override
    public QualityCheckSheetDTO findById(Long id) {
        Optional<QualityCheckSheet> invoiceOptional = qualityCheckSheetRepository.findById(id);
        QualityCheckSheet qualityCheckSheet = invoiceOptional.get();
        QualityCheckSheetDTO qualityCheckSheetDTO = qualityCheckSheetMapper.toDto(qualityCheckSheet);


        List<QualityCheckSheetProduct> qualityCheckSheetProductList = qualityCheckSheetProductRepository.queryByQualityCheckSheetIdAndStatusTrue(id);
        if(!CollectionUtils.isEmpty(qualityCheckSheetProductList)){
            List<QualityCheckSheetProductDTO> qualityCheckSheetProductDTOList = qualityCheckSheetProductMapper.toDto(qualityCheckSheetProductList);
            qualityCheckSheetDTO.setQualityCheckSheetProductList(qualityCheckSheetProductDTOList);
        }
        return qualityCheckSheetDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QualityCheckSheetDTO create(CreateQualityCheckSheetRequest createQualityCheckSheetRequest) {
        QualityCheckSheet qualityCheckSheet = new QualityCheckSheet();
        BeanUtils.copyProperties(createQualityCheckSheetRequest, qualityCheckSheet);

        String qualityCheekSheetCode = createQualityCheckSheetRequest.getQualityCheekSheetCode();
        if(!StringUtils.hasLength(qualityCheekSheetCode)){
            throw new BadRequestException("质量检验单单据编号不能为空!");
        }

        qualityCheckSheet.setStatus(true);
        // 新增质量检验单
        qualityCheckSheetRepository.save(qualityCheckSheet);

        qualityCheckSheet = qualityCheckSheetRepository.findByQualityCheekSheetCode(qualityCheekSheetCode);

        // 新增质量检验单产品信息
        List<QualityCheckSheetProductRequest> qualityCheckSheetProductRequestList = createQualityCheckSheetRequest.getQualityCheckSheetProductList();
        if(CollectionUtils.isEmpty(qualityCheckSheetProductRequestList)){
            throw new BadRequestException("质量检验单产品信息不能为空!");
        }

        for(QualityCheckSheetProductRequest qualityCheckSheetProductRequest : qualityCheckSheetProductRequestList){
            QualityCheckSheetProduct qualityCheckSheetProduct = new QualityCheckSheetProduct();
            BeanUtils.copyProperties(qualityCheckSheetProductRequest, qualityCheckSheetProduct);
            qualityCheckSheetProduct.setStatus(true);
            qualityCheckSheetProduct.setQualityCheckSheetId(qualityCheckSheet.getId());
            qualityCheckSheetProductRepository.save(qualityCheckSheetProduct);
        }


        QualityCheckSheetDTO qualityCheckSheetDTO = qualityCheckSheetMapper.toDto(qualityCheckSheet);

        List<QualityCheckSheetProduct> qualityCheckSheetProductList = qualityCheckSheetProductRepository.queryByQualityCheckSheetIdAndStatusTrue(qualityCheckSheet.getId());
        if(!CollectionUtils.isEmpty(qualityCheckSheetProductList)){
            List<QualityCheckSheetProductDTO> qualityCheckSheetProductDTOList = new ArrayList<>();
            for(QualityCheckSheetProduct qualityCheckSheetProduct : qualityCheckSheetProductList){
                QualityCheckSheetProductDTO qualityCheckSheetProductDTO = new QualityCheckSheetProductDTO();
                BeanUtils.copyProperties(qualityCheckSheetProduct, qualityCheckSheetProductDTO);
                qualityCheckSheetProductDTOList.add(qualityCheckSheetProductDTO);
            }
            qualityCheckSheetDTO.setQualityCheckSheetProductList(qualityCheckSheetProductDTOList);
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
                    String messageContent = MessageModuleType.QUALITY_CHECK_SHEET.getName() + "(" + qualityCheekSheetCode + ")";
                    message.setMessContent(messageContent);
                    message.setModulePath(MessageModulePath.QUALITY_CHECKSHEET_LIST.getCode());
                    message.setModuleTypeName(MessageModuleType.QUALITY_CHECK_SHEET.getCode());
                    message.setReadStatus(MessageReadStatus.NO_READ.getStatus());
                    message.setInitCode(qualityCheekSheetCode);
                    messageList.add(message);
                }
                messageRepository.saveAll(messageList);
            }
        }catch (Exception e){
            log.error("单据编号:插入消息失败!");
        }

        return qualityCheckSheetDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateQualityCheckSheetRequest updateQualityCheckSheetRequest) {
        Long qualityCheckSheetId = updateQualityCheckSheetRequest.getId();
        Optional<QualityCheckSheet> qualityCheckSheetOptional = qualityCheckSheetRepository.findById(qualityCheckSheetId);
        QualityCheckSheet qualityCheckSheet = qualityCheckSheetOptional.get();
        BeanUtils.copyProperties(updateQualityCheckSheetRequest, qualityCheckSheet);

        qualityCheckSheet.setStatus(true);

        qualityCheckSheetRepository.save(qualityCheckSheet);

        // 修改产品信息之前，查询该订单中原来的产品信息，key为产品code
        List<QualityCheckSheetProduct> qualityCheckSheetProductListBeforeUpdate = qualityCheckSheetProductRepository.queryByQualityCheckSheetIdAndStatusTrue(qualityCheckSheet.getId());
        Map<String, QualityCheckSheetProduct> qualityCheckSheetProductMapBefore = qualityCheckSheetProductListBeforeUpdate.stream().collect(Collectors.toMap(QualityCheckSheetProduct::getProductCode, Function.identity()));

        List<QualityCheckSheetProductDTO> qualityCheckSheetProducRequestList = updateQualityCheckSheetRequest.getQualityCheckSheetProductList();
        if(CollectionUtils.isEmpty(qualityCheckSheetProducRequestList)){
            throw new BadRequestException("质量检验单产品信息不能为空!");
        }

        Map<String, QualityCheckSheetProductDTO> qualityCheckSheetProductMapAfter = qualityCheckSheetProducRequestList.stream().collect(Collectors.toMap(QualityCheckSheetProductDTO::getProductCode, Function.identity()));

        //需要将订单中原来订单对应的产品删除了的数据
        List<String> deleteTargetList = new ArrayList<>();
        //比较量个map中，key不一样的数据
        for(Map.Entry<String, QualityCheckSheetProduct> entry:qualityCheckSheetProductMapBefore.entrySet()){
            String productCode = entry.getKey();
            //修改后的map记录对应的key在原来中是否存在
            QualityCheckSheetProductDTO qualityCheckSheetProductDTOTemp = qualityCheckSheetProductMapAfter.get(productCode);
            if(null == qualityCheckSheetProductDTOTemp){
                deleteTargetList.add(entry.getKey());
            }

        }


        List<QualityCheckSheetProduct> qualityCheckSheetProductList = new ArrayList<>();
        for(QualityCheckSheetProductDTO qualityCheckSheetProductDTO : qualityCheckSheetProducRequestList){
            QualityCheckSheetProduct qualityCheckSheetProduct = new QualityCheckSheetProduct();
            BeanUtils.copyProperties(qualityCheckSheetProductDTO, qualityCheckSheetProduct);
            qualityCheckSheetProduct.setQualityCheckSheetId(qualityCheckSheet.getId());
            qualityCheckSheetProduct.setStatus(true);

            if(!(!CollectionUtils.isEmpty(deleteTargetList) && deleteTargetList.contains(qualityCheckSheetProductDTO.getId()))){
                qualityCheckSheetProductList.add(qualityCheckSheetProduct);
            }
        }
        qualityCheckSheetProductRepository.saveAll(qualityCheckSheetProductList);

        /**
         * 场景描述:
         * 1.刚开始新增了 a b c三种产品
         * 2.修改的时候删除了 a c两种产品
         * 3.所以需要查修改前数据库中有的产品，再比较修改传过来的产品数据，如果修改后的在原来里面没有，需要将原来里面对应的删除
         */
        if(!CollectionUtils.isEmpty(deleteTargetList)){
            for(String prductCode : deleteTargetList){
                qualityCheckSheetProductRepository.deleteByProductCodeAndQualityCheckSheetId(prductCode, qualityCheckSheet.getId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        qualityCheckSheetRepository.deleteById(id);
    }
}