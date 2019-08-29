package me.zhengjie.modules.wms.sr.productCount.service.impl;

import me.zhengjie.modules.wms.sr.productCount.domain.ProductCount;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.sr.productCount.repository.ProductCountRepository;
import me.zhengjie.modules.wms.sr.productCount.service.ProductCountService;
import me.zhengjie.modules.wms.sr.productCount.service.dto.ProductCountDTO;
import me.zhengjie.modules.wms.sr.productCount.service.dto.ProductCountQueryCriteria;
import me.zhengjie.modules.wms.sr.productCount.service.mapper.ProductCountMapper;
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
* @date 2019-08-29
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProductCountServiceImpl implements ProductCountService {

    @Autowired
    private ProductCountRepository productCountRepository;

    @Autowired
    private ProductCountMapper productCountMapper;

    @Override
    public Object queryAll(ProductCountQueryCriteria criteria, Pageable pageable){
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
    @Transactional(rollbackFor = Exception.class)
    public ProductCountDTO create(ProductCount resources) {
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