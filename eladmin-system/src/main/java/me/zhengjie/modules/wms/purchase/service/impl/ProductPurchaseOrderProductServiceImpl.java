package me.zhengjie.modules.wms.purchase.service.impl;

import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrderProduct;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.purchase.repository.ProductPurchaseOrderProductRepository;
import me.zhengjie.modules.wms.purchase.service.ProductPurchaseOrderProductService;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderProductDTO;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderProductQueryCriteria;
import me.zhengjie.modules.wms.purchase.service.mapper.ProductPurchaseOrderProductMapper;
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
* @author jie
* @date 2019-10-06
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProductPurchaseOrderProductServiceImpl implements ProductPurchaseOrderProductService {

    @Autowired
    private ProductPurchaseOrderProductRepository productPurchaseOrderProductRepository;

    @Autowired
    private ProductPurchaseOrderProductMapper productPurchaseOrderProductMapper;

    @Override
    public Object queryAll(ProductPurchaseOrderProductQueryCriteria criteria, Pageable pageable){
        Page<ProductPurchaseOrderProduct> page = productPurchaseOrderProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(productPurchaseOrderProductMapper::toDto));
    }

    @Override
    public Object queryAll(ProductPurchaseOrderProductQueryCriteria criteria){
        return productPurchaseOrderProductMapper.toDto(productPurchaseOrderProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ProductPurchaseOrderProductDTO findById(Long id) {
        Optional<ProductPurchaseOrderProduct> productPurchaseOrderProduct = productPurchaseOrderProductRepository.findById(id);
        ValidationUtil.isNull(productPurchaseOrderProduct,"ProductPurchaseOrderProduct","id",id);
        return productPurchaseOrderProductMapper.toDto(productPurchaseOrderProduct.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductPurchaseOrderProductDTO create(ProductPurchaseOrderProduct resources) {
        return productPurchaseOrderProductMapper.toDto(productPurchaseOrderProductRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductPurchaseOrderProduct resources) {
        Optional<ProductPurchaseOrderProduct> optionalProductPurchaseOrderProduct = productPurchaseOrderProductRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalProductPurchaseOrderProduct,"ProductPurchaseOrderProduct","id",resources.getId());
        ProductPurchaseOrderProduct productPurchaseOrderProduct = optionalProductPurchaseOrderProduct.get();
        productPurchaseOrderProduct.copy(resources);
        productPurchaseOrderProductRepository.save(productPurchaseOrderProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        productPurchaseOrderProductRepository.deleteById(id);
    }
}