package me.zhengjie.modules.wms.outSourceProductSheet.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificate;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificateProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.repository.OutSourceInspectionCertificateProductRepository;
import me.zhengjie.modules.wms.outSourceProductSheet.request.CreateOutSourceInspectionCertificateRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.request.CreateOutSourceProcessSheetRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.request.OutSourceInspectionCertificateProductRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.request.OutSourceProcessSheetProductRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.*;
import me.zhengjie.modules.wms.outSourceProductSheet.service.mapper.OutSourceInspectionCertificateProductMapper;
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
import java.util.Optional;
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

    @Override
    public Object queryAll(OutSourceInspectionCertificateQueryCriteria criteria, Pageable pageable){
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
            outSourceInspectionCertificateDTO.setOutSourceInspectionCertificateDTOList(outSourceInspectionCertificateProductDTOList);
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
            outSourceInspectionCertificateDTO.setOutSourceInspectionCertificateDTOList(outSourceInspectionCertificateProductDTOList);
        }

        return outSourceInspectionCertificateDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OutSourceInspectionCertificate resources) {
        Optional<OutSourceInspectionCertificate> optionalSOutSourceInspectionCertificate = outSourceInspectionCertificateRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalSOutSourceInspectionCertificate,"SOutSourceInspectionCertificate","id",resources.getId());
        OutSourceInspectionCertificate outSourceInspectionCertificate = optionalSOutSourceInspectionCertificate.get();
        outSourceInspectionCertificate.copy(resources);
        outSourceInspectionCertificateRepository.save(outSourceInspectionCertificate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        outSourceInspectionCertificateRepository.deleteById(id);
    }
}