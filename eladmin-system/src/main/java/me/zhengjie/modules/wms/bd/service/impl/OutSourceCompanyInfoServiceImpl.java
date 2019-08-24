package me.zhengjie.modules.wms.bd.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.CustomerInfo;
import me.zhengjie.modules.wms.bd.domain.OutSourceCompanyInfo;
import me.zhengjie.modules.wms.bd.domain.SupplierInfo;
import me.zhengjie.modules.wms.bd.request.*;
import me.zhengjie.modules.wms.bd.service.dto.*;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.bd.repository.OutSourceCompanyInfoRepository;
import me.zhengjie.modules.wms.bd.service.OutSourceCompanyInfoService;
import me.zhengjie.modules.wms.bd.service.mapper.OutSourceCompanyInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
* @date 2019-08-03
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OutSourceCompanyInfoServiceImpl implements OutSourceCompanyInfoService {

    @Autowired
    private OutSourceCompanyInfoRepository outSourceCompanyInfoRepository;

    @Autowired
    private OutSourceCompanyInfoMapper outSourceCompanyInfoMapper;

    @Override
    public Object queryAll(OutSourceCompanyInfoQueryCriteria criteria, Pageable pageable){
        Specification<OutSourceCompanyInfo> specification = new Specification<OutSourceCompanyInfo>() {
            @Override
            public Predicate toPredicate(Root<OutSourceCompanyInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);

                //委外公司名称
                String outSourceCompanyName = criteria.getOutSourceCompanyName();
                if (!StringUtils.isEmpty(outSourceCompanyName)) {
                    Predicate outSourceCompanyNamePredicate = criteriaBuilder.like(root.get("outSourceCompanyName"), "%" + outSourceCompanyName + "%");
                    targetPredicateList.add(outSourceCompanyNamePredicate);
                }

                //委外公司名称
                String outSourceCompanyCode = criteria.getOutSourceCompanyCode();
                if (!StringUtils.isEmpty(outSourceCompanyName)) {
                    Predicate outSourceCompanyCodePredicate = criteriaBuilder.like(root.get("outSourceCompanyCode"), "%" + outSourceCompanyCode + "%");
                    targetPredicateList.add(outSourceCompanyCodePredicate);
                }

                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };
        Page<OutSourceCompanyInfo> page = outSourceCompanyInfoRepository.findAll(specification, pageable);
        Page<OutSourceCompanyInfoDTO> outSourceCompanyInfoDTOPage = page.map(outSourceCompanyInfoMapper::toDto);
        if(null != outSourceCompanyInfoDTOPage){
            List<OutSourceCompanyInfoDTO> outSourceCompanyInfoDTOList = outSourceCompanyInfoDTOPage.getContent();
            if(!CollectionUtils.isEmpty(outSourceCompanyInfoDTOList)){
                for(OutSourceCompanyInfoDTO outSourceCompanyInfoDTO : outSourceCompanyInfoDTOList){
                    Long outSourceCompanyInfoDTOId = outSourceCompanyInfoDTO.getId();
                    Optional<OutSourceCompanyInfo> outSourceCompanyInfoOptional = outSourceCompanyInfoRepository.findById(outSourceCompanyInfoDTOId);
                    if(null != outSourceCompanyInfoOptional){
                        OutSourceCompanyInfo outSourceCompanyInfo = outSourceCompanyInfoOptional.get();
                        if(null != outSourceCompanyInfo){
                            String outSourceCompanyJsonStr = outSourceCompanyInfo.getOutSourceCompanyContact();
                            List<OutSourceCompanyContact> outSourceCompanyContactList = new Gson().fromJson(outSourceCompanyJsonStr,new TypeToken<ArrayList<OutSourceCompanyContact>>() {}.getType());
                            if(!CollectionUtils.isEmpty(outSourceCompanyContactList)){
                                for(OutSourceCompanyContact outSourceCompanyContact : outSourceCompanyContactList){
                                    if(outSourceCompanyContact.getFirstTag() == 1){
                                        outSourceCompanyInfoDTO.setFirstContactMobile(outSourceCompanyContact.getMobile());
                                        outSourceCompanyInfoDTO.setFirstContactName(outSourceCompanyContact.getName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return PageUtil.toPage(outSourceCompanyInfoDTOPage);
    }

    @Override
    public Object queryAll(OutSourceCompanyInfoQueryCriteria criteria){
        Specification<OutSourceCompanyInfo> specification = new Specification<OutSourceCompanyInfo>() {
            @Override
            public Predicate toPredicate(Root<OutSourceCompanyInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);

                //委外公司名称
                String outSourceCompanyName = criteria.getOutSourceCompanyName();
                if (!StringUtils.isEmpty(outSourceCompanyName)) {
                    Predicate outSourceCompanyNamePredicate = criteriaBuilder.like(root.get("outSourceCompanyName"), "%" + outSourceCompanyName + "%");
                    targetPredicateList.add(outSourceCompanyNamePredicate);
                }

                //委外公司名称
                String outSourceCompanyCode = criteria.getOutSourceCompanyCode();
                if (!StringUtils.isEmpty(outSourceCompanyName)) {
                    Predicate outSourceCompanyCodePredicate = criteriaBuilder.like(root.get("outSourceCompanyCode"), "%" + outSourceCompanyCode + "%");
                    targetPredicateList.add(outSourceCompanyCodePredicate);
                }

                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };
        return outSourceCompanyInfoMapper.toDto(outSourceCompanyInfoRepository.findAll(specification));
    }

    @Override
    public OutSourceCompanyInfoDetailDTO findById(Long id) {
        OutSourceCompanyInfoDetailDTO outSourceCompanyInfoDetailDTO = new OutSourceCompanyInfoDetailDTO();

        Optional<OutSourceCompanyInfo> outSourceCompanyInfoOptional = outSourceCompanyInfoRepository.findById(id);
        OutSourceCompanyInfo outSourceCompanyInfo = outSourceCompanyInfoOptional.get();
        OutSourceCompanyInfoDTO outSourceCompanyInfoDTO = outSourceCompanyInfoMapper.toDto(outSourceCompanyInfo);
        if(null != outSourceCompanyInfoDTO){
            BeanUtils.copyProperties( outSourceCompanyInfoDTO, outSourceCompanyInfoDetailDTO);
            String outSourceCompanyAddressJsonStr = outSourceCompanyInfo.getOutSourceCompanyAddress();
            if(StringUtils.hasLength(outSourceCompanyAddressJsonStr)){
                List<OutSourceCompanyAddress> outSourceCompanyAddressList = new Gson().fromJson(outSourceCompanyAddressJsonStr,new TypeToken<ArrayList<OutSourceCompanyAddress>>() {}.getType());
                outSourceCompanyInfoDetailDTO.setOutSourceCompanyAddress(outSourceCompanyAddressList);
            }


            String outSourceCompanyContactJsonStr = outSourceCompanyInfo.getOutSourceCompanyContact();
            if(StringUtils.hasLength(outSourceCompanyContactJsonStr)){
                List<OutSourceCompanyContact> outSourceCompanyContactList = new Gson().fromJson(outSourceCompanyContactJsonStr,new TypeToken<ArrayList<OutSourceCompanyContact>>() {}.getType());
                outSourceCompanyInfoDetailDTO.setOutSourceCompanyContact(outSourceCompanyContactList);
            }
        }
        return outSourceCompanyInfoDetailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutSourceCompanyInfoDetailDTO create(CreateOutSourceCompanyInfoRequest createOutSourceCompanyInfoRequest) {
        OutSourceCompanyInfoDetailDTO outSourceCompanyInfoDetailDTO = new OutSourceCompanyInfoDetailDTO();

        OutSourceCompanyInfo outSourceCompanyInfo = new OutSourceCompanyInfo();
        BeanUtils.copyProperties(createOutSourceCompanyInfoRequest, outSourceCompanyInfo);
        outSourceCompanyInfo.setStatus(true);
        List<OutSourceCompanyAddress> outSourceCompanyAddressList = createOutSourceCompanyInfoRequest.getOutSourceCompanyAddress();
        if(!CollectionUtils.isEmpty(outSourceCompanyAddressList)){
            String outSourceCompanyAddressStr = new Gson().toJson(outSourceCompanyAddressList);
            outSourceCompanyInfo.setOutSourceCompanyAddress(outSourceCompanyAddressStr);
            outSourceCompanyInfoDetailDTO.setOutSourceCompanyAddress(outSourceCompanyAddressList);
        }
        List<OutSourceCompanyContact> outSourceCompanyContactList = createOutSourceCompanyInfoRequest.getOutSourceCompanyContact();
        if(!CollectionUtils.isEmpty(outSourceCompanyContactList)){
            String outSourceCompanyContactStr = new Gson().toJson(outSourceCompanyContactList);
            outSourceCompanyInfo.setOutSourceCompanyContact(outSourceCompanyContactStr);
            outSourceCompanyInfoDetailDTO.setOutSourceCompanyContact(outSourceCompanyContactList);
        }

        outSourceCompanyInfo = outSourceCompanyInfoRepository.save(outSourceCompanyInfo);
        OutSourceCompanyInfoDTO outSourceCompanyInfoDTO = outSourceCompanyInfoMapper.toDto(outSourceCompanyInfo);
        BeanUtils.copyProperties(outSourceCompanyInfoDTO, outSourceCompanyInfoDetailDTO);
        return outSourceCompanyInfoDetailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateOutSourceCompanyInfoRequest updateOutSourceCompanyInfoRequest) {
        Long outSourceCompanyInfoId = updateOutSourceCompanyInfoRequest.getId();
        if(null == outSourceCompanyInfoId){
            throw new BadRequestException("委外公司主键不能为空!");
        }

        // 委外公司资料-客户联系地址修改目标
        List<OutSourceCompanyAddress> outSourceCompanyInfoAddressListUpdateTarget = updateOutSourceCompanyInfoRequest.getOutSourceCompanyAddress();
        // 委外公司资料-供应商联系方式修改目标
        List<OutSourceCompanyContact> outSourceCompanyInfoContactListUpdateTarget = updateOutSourceCompanyInfoRequest.getOutSourceCompanyContact();

        OutSourceCompanyInfo outSourceCompanyInfo = outSourceCompanyInfoRepository.findByIdAndStatusTrue(outSourceCompanyInfoId);

        if(null == outSourceCompanyInfo){
            throw new BadRequestException("委外公司不存在");
        }

        Timestamp createTime = outSourceCompanyInfo.getCreateTime();

        // 将需要修改的值复制到数据库对象中
        BeanUtils.copyProperties(updateOutSourceCompanyInfoRequest, outSourceCompanyInfo);

        // 判断提前获取的供应商联系地址和联系方式是否是空
        if(CollectionUtils.isEmpty(outSourceCompanyInfoAddressListUpdateTarget)){
            outSourceCompanyInfo.setOutSourceCompanyAddress(null);
        }else{
            String supplierAddressStr = new Gson().toJson(outSourceCompanyInfoAddressListUpdateTarget);
            outSourceCompanyInfo.setOutSourceCompanyAddress(supplierAddressStr);
        }

        if(CollectionUtils.isEmpty(outSourceCompanyInfoContactListUpdateTarget)){
            outSourceCompanyInfo.setOutSourceCompanyContact(null);
        }else{
            String supplierContactStr = new Gson().toJson(outSourceCompanyInfoContactListUpdateTarget);
            outSourceCompanyInfo.setOutSourceCompanyContact(supplierContactStr);
        }

        outSourceCompanyInfo.setCreateTime(createTime);
        outSourceCompanyInfo.setStatus(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        outSourceCompanyInfo.setUpdateTime(Timestamp.valueOf(sdf.format(new Date())));
        // 修改委外资料
        outSourceCompanyInfoRepository.save(outSourceCompanyInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        OutSourceCompanyInfo outSourceCompanyInfo = outSourceCompanyInfoRepository.findByIdAndStatusTrue(id);
        if (null == outSourceCompanyInfo) {
            throw new BadRequestException("委外公司资料不存在!");
        }
        outSourceCompanyInfoRepository.deleteOutSourceCompanyInfo(id);
    }
}