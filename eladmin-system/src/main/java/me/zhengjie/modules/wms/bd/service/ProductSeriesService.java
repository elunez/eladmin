package me.zhengjie.modules.wms.bd.service;

import me.zhengjie.modules.wms.bd.domain.ProductSeries;
import me.zhengjie.modules.wms.bd.service.dto.ProductSeriesDTO;
import me.zhengjie.modules.wms.bd.service.dto.ProductSeriesQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author huangxingxing
* @date 2020-01-04
*/
//@CacheConfig(cacheNames = "bdProductSeries")
public interface ProductSeriesService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(ProductSeriesQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ProductSeriesQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ProductSeriesDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ProductSeriesDTO create(ProductSeries resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(ProductSeries resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}