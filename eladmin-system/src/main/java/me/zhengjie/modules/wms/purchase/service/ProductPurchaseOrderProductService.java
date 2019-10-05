package me.zhengjie.modules.wms.purchase.service;

import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrderProduct;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderProductDTO;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderProductQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author jie
* @date 2019-10-06
*/
//@CacheConfig(cacheNames = "productPurchaseOrderProduct")
public interface ProductPurchaseOrderProductService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(ProductPurchaseOrderProductQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ProductPurchaseOrderProductQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ProductPurchaseOrderProductDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ProductPurchaseOrderProductDTO create(ProductPurchaseOrderProduct resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(ProductPurchaseOrderProduct resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}