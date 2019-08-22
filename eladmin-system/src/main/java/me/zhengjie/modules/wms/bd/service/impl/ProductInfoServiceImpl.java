package me.zhengjie.modules.wms.bd.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.*;
import me.zhengjie.modules.wms.bd.repository.MeasureUnitRepository;
import me.zhengjie.modules.wms.bd.repository.ProductCategoryRepository;
import me.zhengjie.modules.wms.bd.request.*;
import me.zhengjie.modules.wms.bd.service.dto.*;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.bd.repository.ProductInfoRepository;
import me.zhengjie.modules.wms.bd.service.ProductInfoService;
import me.zhengjie.modules.wms.bd.service.mapper.ProductInfoMapper;
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
* @author 黄星星
* @date 2019-07-27
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private MeasureUnitRepository measureUnitRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public Object queryAll(ProductInfoQueryCriteria criteria, Pageable pageable){
        Specification<ProductInfo> specification = new Specification<ProductInfo>() {
            @Override
            public Predicate toPredicate(Root<ProductInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        Page<ProductInfo> page  = productInfoRepository.findAll(specification,pageable);

        return PageUtil.toPage(page.map(productInfoMapper::toDto));
    }

    @Override
    public Object queryAll(ProductInfoQueryCriteria criteria){
        Specification<ProductInfo> specification = new Specification<ProductInfo>() {
            @Override
            public Predicate toPredicate(Root<ProductInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

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
        List<ProductInfo> productInfoList = productInfoRepository.findAll(specification);
        return productInfoMapper.toDto(productInfoList);
    }

    @Override
    public ProductInfoDetailDTO findById(Long id) {
        ProductInfoDetailDTO productInfoDetailDTO = new ProductInfoDetailDTO();

        Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(id);
        ProductInfo productInfo = productInfoOptional.get();
        ProductInfoDTO productInfoDTO = productInfoMapper.toDto(productInfo);
        if(null != productInfoDTO){
            BeanUtils.copyProperties( productInfoDTO, productInfoDetailDTO);
            String productInventoryWarningStr = productInfo.getProductInventoryWarning();
            if(StringUtils.hasLength(productInventoryWarningStr)){
                List<ProductInventoryWarning> productInventoryWarningList = new Gson().fromJson(productInventoryWarningStr,new TypeToken<ArrayList<ProductInventoryWarning>>() {}.getType());
                productInfoDetailDTO.setProductInventoryWarningList(productInventoryWarningList);
            }


            String productInitialSetupStr = productInfo.getProductInitialSetup();
            if(StringUtils.hasLength(productInitialSetupStr)){
                List<ProductInitialSetup> productInitialSetupList = new Gson().fromJson(productInitialSetupStr,new TypeToken<ArrayList<SupplierContact>>() {}.getType());
                productInfoDetailDTO.setProductInitialSetupList(productInitialSetupList);
            }
        }
        return productInfoDetailDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductInfoDetailDTO create(CreateProductInfoRequest createProductInfoRequest) {
        Long measureUnitId = createProductInfoRequest.getMeasureUnitId();
        if(null == measureUnitId){
            throw new BadRequestException("计量单位不能为空!");
        }
        Optional<MeasureUnit> measureUnitOptional = measureUnitRepository.findById(measureUnitId);
        MeasureUnit measureUnit = measureUnitOptional.get();
        if(null == measureUnit){
            throw new BadRequestException("计量单位不存在!");
        }

        Long productCategoryId = createProductInfoRequest.getProductCategoryId();
        if(null == productCategoryId){
            throw new BadRequestException("产品类别不能为空!");
        }
        Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(productCategoryId);
        ProductCategory productCategory = productCategoryOptional.get();
        if(null == productCategory){
            throw new BadRequestException("产品类别不存在!");
        }



        ProductInfoDetailDTO productInfoDetailDTO = new ProductInfoDetailDTO();

        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(createProductInfoRequest, productInfo);
        productInfo.setStatus(true);
        List<ProductInventoryWarning> productInventoryWarningList = createProductInfoRequest.getProductInventoryWarningList();
        if(!CollectionUtils.isEmpty(productInventoryWarningList)){
            String productInventoryWarningStr = new Gson().toJson(productInventoryWarningList);
            productInfo.setProductInventoryWarning(productInventoryWarningStr);
            productInfoDetailDTO.setProductInventoryWarningList(productInventoryWarningList);
        }
        List<ProductInitialSetup> productInitialSetupList = createProductInfoRequest.getProductInitialSetupList();
        if(!CollectionUtils.isEmpty(productInitialSetupList)){
            String productInitialSetupStr = new Gson().toJson(productInitialSetupList);
            productInfo.setProductInitialSetup(productInitialSetupStr);
            productInfoDetailDTO.setProductInitialSetupList(productInitialSetupList);
        }

        productInfo.setProductCategoryName(productCategory.getName());
        productInfo.setMeasureUnitName(measureUnit.getName());

        productInfo = productInfoRepository.save(productInfo);
        ProductInfoDTO productInfoDTO = productInfoMapper.toDto(productInfo);
        BeanUtils.copyProperties(productInfoDTO, productInfoDetailDTO);
        return productInfoDetailDTO;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateProductInfoRequest updateProductInfoRequest) {
        Long productInfoId = updateProductInfoRequest.getId();
        if(null == productInfoId){
            throw new BadRequestException("产品信息主键不能为空!");
        }

        Long measureUnitId = updateProductInfoRequest.getMeasureUnitId();
        if(null == measureUnitId){
            throw new BadRequestException("计量单位不能为空!");
        }
        Optional<MeasureUnit> measureUnitOptional = measureUnitRepository.findById(measureUnitId);
        MeasureUnit measureUnit = measureUnitOptional.get();
        if(null == measureUnit){
            throw new BadRequestException("计量单位不存在!");
        }

        Long productCategoryId = updateProductInfoRequest.getProductCategoryId();
        if(null == productCategoryId){
            throw new BadRequestException("产品类别不能为空!");
        }
        Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(productCategoryId);
        ProductCategory productCategory = productCategoryOptional.get();
        if(null == productCategory){
            throw new BadRequestException("产品类别不存在!");
        }



        // 产品资料-仓库预警修改目标
        List<ProductInventoryWarning> productInventoryWarningListTarget = updateProductInfoRequest.getProductInventoryWarningList();
        // 产品资料-期初设置修改目标
        List<ProductInitialSetup> productInitialSetupListTarget = updateProductInfoRequest.getProductInitialSetupList();

        ProductInfo productInfo = productInfoRepository.findByIdAndStatusTrue(productInfoId);

        if(null == productInfo){
            throw new BadRequestException("产品信息不存在");
        }

        Timestamp createTime = productInfo.getCreateTime();

        // 将需要修改的值复制到数据库对象中
        BeanUtils.copyProperties(updateProductInfoRequest, productInfo);

        // 判断提前获取的供应商联系地址和联系方式是否是空
        if(CollectionUtils.isEmpty(productInventoryWarningListTarget)){
            productInfo.setProductInventoryWarning(null);
        }else{
            String productInventoryWarningStr = new Gson().toJson(productInventoryWarningListTarget);
            productInfo.setProductInventoryWarning(productInventoryWarningStr);
        }

        if(CollectionUtils.isEmpty(productInitialSetupListTarget)){
            productInfo.setProductInitialSetup(null);
        }else{
            String productInitialSetupStr = new Gson().toJson(productInitialSetupListTarget);
            productInfo.setProductInitialSetup(productInitialSetupStr);
        }

        productInfo.setCreateTime(createTime);
        productInfo.setStatus(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        productInfo.setUpdateTime(Timestamp.valueOf(sdf.format(new Date())));
        productInfo.setProductCategoryName(productCategory.getName());
        // 修改客户资料
        productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        ProductInfo productInfo = productInfoRepository.findByIdAndStatusTrue(id);
        if (null == productInfo) {
            throw new BadRequestException("产品资料不存在!");
        }
        productInfoRepository.deleteProductInfo(id);
    }
}