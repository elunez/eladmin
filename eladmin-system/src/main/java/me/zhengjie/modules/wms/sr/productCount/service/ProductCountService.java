package me.zhengjie.modules.wms.sr.productCount.service;

import me.zhengjie.modules.wms.sr.productCount.domain.ProductCount;
import me.zhengjie.modules.wms.sr.productCount.service.dto.ProductCountDTO;
import me.zhengjie.modules.wms.sr.productCount.service.dto.ProductCountQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author jie
* @date 2019-08-29
*/
//@CacheConfig(cacheNames = "srProductCount")
public interface ProductCountService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(ProductCountQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ProductCountQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ProductCountDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ProductCountDTO create(ProductCount resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(ProductCount resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}