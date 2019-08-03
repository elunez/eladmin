package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.MaterialInfo;
import me.zhengjie.modules.wms.bd.domain.ProductInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.bd.repository.ProductInfoRepository;
import me.zhengjie.modules.wms.bd.service.ProductInfoService;
import me.zhengjie.modules.wms.bd.service.dto.ProductInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.ProductInfoQueryCriteria;
import me.zhengjie.modules.wms.bd.service.mapper.ProductInfoMapper;
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
    public ProductInfoDTO findById(Long id) {
        Optional<ProductInfo> bdProductInfo = productInfoRepository.findById(id);
        ValidationUtil.isNull(bdProductInfo,"BdProductInfo","id",id);
        return productInfoMapper.toDto(bdProductInfo.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductInfoDTO create(ProductInfo resources) {
        productInfoRepository.save(resources);
        return productInfoMapper.toDto(productInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductInfo resources) {
        Optional<ProductInfo> optionalBdProductInfo = productInfoRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalBdProductInfo,"productInfo","id",resources.getId());
        ProductInfo productInfo = optionalBdProductInfo.get();
        productInfo.copy(resources);
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