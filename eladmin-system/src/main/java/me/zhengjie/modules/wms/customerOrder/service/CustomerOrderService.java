package me.zhengjie.modules.wms.customerOrder.service;

//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import me.zhengjie.modules.wms.customerOrder.service.dto.CustomerOrderQueryCriteria;
import me.zhengjie.modules.wms.customerOrder.request.CreateCustomerOrderRequest;
import me.zhengjie.modules.wms.customerOrder.request.UpdateCustomerOrderRequest;
import me.zhengjie.modules.wms.customerOrder.service.dto.CustomerOrderDTO;
import org.springframework.data.domain.Pageable;

/**
* @author jie
* @date 2019-08-03
*/
//@CacheConfig(cacheNames = "sCustomerOrder")
public interface CustomerOrderService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(CustomerOrderQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(CustomerOrderQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    CustomerOrderDTO findById(Long id);

    /**
     * create
     * @param createCustomerOrderRequest
     * @return
     */
    //@CacheEvict(allEntries = true)
    CustomerOrderDTO create(CreateCustomerOrderRequest createCustomerOrderRequest);

    /**
     * update
     * @param updateCustomerOrderRequest
     */
    //@CacheEvict(allEntries = true)
    void update(UpdateCustomerOrderRequest updateCustomerOrderRequest);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}