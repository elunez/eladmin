package me.zhengjie.modules.wms.purchase.service;

import me.zhengjie.modules.wms.purchase.domain.ConsumablesPurchaseOrderProduct;
import me.zhengjie.modules.wms.purchase.service.dto.ConsumablesPurchaseOrderProductDTO;
import me.zhengjie.modules.wms.purchase.service.dto.ConsumablesPurchaseOrderProductQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author jie
* @date 2019-10-06
*/
//@CacheConfig(cacheNames = "consumablesPurchaseOrderProduct")
public interface ConsumablesPurchaseOrderProductService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(ConsumablesPurchaseOrderProductQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ConsumablesPurchaseOrderProductQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ConsumablesPurchaseOrderProductDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ConsumablesPurchaseOrderProductDTO create(ConsumablesPurchaseOrderProduct resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(ConsumablesPurchaseOrderProduct resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}