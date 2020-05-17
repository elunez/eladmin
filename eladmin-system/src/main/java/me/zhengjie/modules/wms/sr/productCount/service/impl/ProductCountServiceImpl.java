package me.zhengjie.modules.wms.sr.productCount.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.wms.bd.domain.ProductInfo;
import me.zhengjie.modules.wms.bd.repository.ProductInfoRepository;
import me.zhengjie.modules.wms.bd.service.mapper.ProductInfoMapper;
import me.zhengjie.modules.wms.customerOrder.domain.CustomerOrder;
import me.zhengjie.modules.wms.sr.productCount.domain.ProductCount;
import me.zhengjie.utils.StringUtils;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.sr.productCount.repository.ProductCountRepository;
import me.zhengjie.modules.wms.sr.productCount.service.ProductCountService;
import me.zhengjie.modules.wms.sr.productCount.service.dto.ProductCountDTO;
import me.zhengjie.modules.wms.sr.productCount.service.dto.ProductCountQueryCriteria;
import me.zhengjie.modules.wms.sr.productCount.service.mapper.ProductCountMapper;
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
* @author jie
* @date 2019-08-29
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProductCountServiceImpl implements ProductCountService {

    @Autowired
    private ProductCountRepository productCountRepository;

    @Autowired
    private ProductCountMapper productCountMapper;

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public Object queryAll(ProductCountQueryCriteria criteria, Pageable pageable){

        Specification<ProductCount> specification = new Specification<ProductCount>() {
            @Override
            public Predicate toPredicate(Root<ProductCount> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> targetPredicateList = new ArrayList<>();

                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), 1);
                targetPredicateList.add(statusPredicate);


                if(!StringUtils.isEmpty(criteria.getProductName())){
                    Predicate customerOrderCodePredicate = criteriaBuilder.like(root.get("productName").as(String.class), "%" + criteria.getProductName() + "%");
                    targetPredicateList.add(customerOrderCodePredicate);
                }

                if(!StringUtils.isEmpty(criteria.getProductCategoryName())){
                    Predicate customerNamePredicate = criteriaBuilder.like(root.get("productCategoryName").as(String.class), "%" + criteria.getProductCategoryName() + "%");
                    targetPredicateList.add(customerNamePredicate);
                }

                if(!StringUtils.isEmpty(criteria.getProductSeriesName())){
                    Predicate customerNamePredicate = criteriaBuilder.like(root.get("productSeriesName").as(String.class), "%" + criteria.getProductSeriesName() + "%");
                    targetPredicateList.add(customerNamePredicate);
                }

                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("gmtUpdate")));

                if(CollectionUtils.isEmpty(targetPredicateList)){
                    return null;
                }else{
                    return criteriaBuilder.and(targetPredicateList.toArray(new Predicate[targetPredicateList.size()]));
                }
            }
        };

        Page<ProductCount> page = productCountRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(productCountMapper::toDto));
    }

    @Override
    public Object queryAll(ProductCountQueryCriteria criteria){
        return productCountMapper.toDto(productCountRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ProductCountDTO findById(Long id) {
        Optional<ProductCount> srProductCount = productCountRepository.findById(id);
        ValidationUtil.isNull(srProductCount,"SrProductCount","id",id);
        return productCountMapper.toDto(srProductCount.get());
    }

    @Override
    public ProductCountDTO findByProductId(Long productId) {

        ProductCount productCount = productCountMapper.findByProductId(productId);
        return productCountMapper.toDto(productCount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductCountDTO create(ProductCount resources) {
        Long productId = resources.getProductId();
        if(null == productId){
            throw new BadRequestException("产品主键不能为空!");
        }
        Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(productId);
        if(null == productInfoOptional){
            throw new BadRequestException("产品不存在 !");
        }
        ProductInfo productInfo = productInfoOptional.get();
        if(null == productInfo){
            throw new BadRequestException("产品不存在 !");
        }
        resources.setProductName(productInfo.getName());
        //验证产品库存统计是否存在

        ProductCount productCountTemp = productCountRepository.findByProductId(productId);
        if(null != productCountTemp){
            throw new BadRequestException("该产品统计记录已经存在 !");
        }
        return productCountMapper.toDto(productCountRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductCount resources) {
        Optional<ProductCount> optionalSrProductCount = productCountRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalSrProductCount,"SrProductCount","id",resources.getId());
        ProductCount productCount = optionalSrProductCount.get();
        productCount.copy(resources);
        productCountRepository.save(productCount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        productCountRepository.deleteById(id);
    }
}