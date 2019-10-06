package me.zhengjie.modules.wms.purchase.service;

import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrder;
import me.zhengjie.modules.wms.purchase.request.AuditProductPurchaseOrderRequest;
import me.zhengjie.modules.wms.purchase.request.CreateProductPurchaseOrderRequest;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderDTO;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author jie
* @date 2019-10-06
*/
//@CacheConfig(cacheNames = "productPurchaseOrder")
public interface ProductPurchaseOrderService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(ProductPurchaseOrderQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ProductPurchaseOrderQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ProductPurchaseOrderDTO findById(Long id);

    /**
     * create
     * @param createProductPurchaseOrderRequest
     * @return
     */
    //@CacheEvict(allEntries = true)
    ProductPurchaseOrderDTO create(CreateProductPurchaseOrderRequest createProductPurchaseOrderRequest);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(ProductPurchaseOrder resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     *
     * @param auditProductPurchaseOrderRequest
     */
    void auditProductPurchaseOrder(AuditProductPurchaseOrderRequest auditProductPurchaseOrderRequest);
}