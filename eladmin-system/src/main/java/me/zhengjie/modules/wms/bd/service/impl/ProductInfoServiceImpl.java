package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.modules.wms.bd.domain.ProductInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.bd.repository.ProductInfoRepository;
import me.zhengjie.modules.wms.bd.service.ProductInfoService;
import me.zhengjie.modules.wms.bd.service.dto.ProductInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.ProductInfoQueryCriteria;
import me.zhengjie.modules.wms.bd.service.mapper.ProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

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
        Page<ProductInfo> page = productInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(productInfoMapper::toDto));
    }

    @Override
    public Object queryAll(ProductInfoQueryCriteria criteria){
        return productInfoMapper.toDto(productInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ProductInfoDTO findById(Integer id) {
        Optional<ProductInfo> bdProductInfo = productInfoRepository.findById(id);
        ValidationUtil.isNull(bdProductInfo,"BdProductInfo","id",id);
        return productInfoMapper.toDto(bdProductInfo.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductInfoDTO create(ProductInfo resources) {
        return productInfoMapper.toDto(productInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductInfo resources) {
        Optional<ProductInfo> optionalBdProductInfo = productInfoRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalBdProductInfo,"BdProductInfo","id",resources.getId());
        ProductInfo productInfo = optionalBdProductInfo.get();
        productInfo.copy(resources);
        productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        productInfoRepository.deleteById(id);
    }
}