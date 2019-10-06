package me.zhengjie.modules.wms.purchase.service;

import me.zhengjie.modules.wms.purchase.domain.ConsumablesPurchaseOrder;
import me.zhengjie.modules.wms.purchase.request.AuditConsumablesPurchaseOrderRequest;
import me.zhengjie.modules.wms.purchase.service.dto.ConsumablesPurchaseOrderDTO;
import me.zhengjie.modules.wms.purchase.service.dto.ConsumablesPurchaseOrderQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author jie
* @date 2019-10-06
*/
//@CacheConfig(cacheNames = "consumablesPurchaseOrder")
public interface ConsumablesPurchaseOrderService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(ConsumablesPurchaseOrderQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ConsumablesPurchaseOrderQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ConsumablesPurchaseOrderDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ConsumablesPurchaseOrderDTO create(ConsumablesPurchaseOrder resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(ConsumablesPurchaseOrder resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);


    void auditConsumablesPurchaseOrder(AuditConsumablesPurchaseOrderRequest auditConsumablesPurchaseOrderRequest);
}