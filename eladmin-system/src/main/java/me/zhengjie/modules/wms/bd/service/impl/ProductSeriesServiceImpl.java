package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.modules.wms.bd.domain.ProductSeries;
import me.zhengjie.modules.wms.bd.service.dto.ProductSeriesDTO;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.wms.bd.repository.ProductSeriesRepository;
import me.zhengjie.modules.wms.bd.service.ProductSeriesService;
import me.zhengjie.modules.wms.bd.service.dto.ProductSeriesDTO;
import me.zhengjie.modules.wms.bd.service.dto.ProductSeriesQueryCriteria;
import me.zhengjie.modules.wms.bd.service.mapper.ProductSeriesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

/**
* @author huangxingxing
* @date 2020-01-04
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProductSeriesServiceImpl implements ProductSeriesService {

    @Autowired
    private ProductSeriesRepository productSeriesRepository;

    @Autowired
    private ProductSeriesMapper productSeriesMapper;

    @Override
    public Object queryAll(ProductSeriesQueryCriteria criteria, Pageable pageable){
        Page<ProductSeries> page = productSeriesRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(productSeriesMapper::toDto));
    }

    @Override
    public Object queryAll(ProductSeriesQueryCriteria criteria){
        return productSeriesMapper.toDto(productSeriesRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ProductSeriesDTO findById(Long id) {
        Optional<ProductSeries> bdProductSeries = productSeriesRepository.findById(id);
        ValidationUtil.isNull(bdProductSeries,"BdProductSeries","id",id);
        return productSeriesMapper.toDto(bdProductSeries.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductSeriesDTO create(ProductSeries resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId());
        resources.setStatus(true);
        return productSeriesMapper.toDto(productSeriesRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductSeries resources) {
        Optional<ProductSeries> optionalBdProductSeries = productSeriesRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalBdProductSeries,"BdProductSeries","id",resources.getId());
        ProductSeries productSeries = optionalBdProductSeries.get();
        productSeries.copy(resources);
        productSeriesRepository.save(productSeries);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        productSeriesRepository.deleteById(id);
    }
}