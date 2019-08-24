package me.zhengjie.modules.wms.bd.service;

import me.zhengjie.modules.wms.bd.domain.CustomerInfo;
import me.zhengjie.modules.wms.bd.request.CreateCustomerInfoRequest;
import me.zhengjie.modules.wms.bd.request.UpdateCustomerInfoRequest;
import me.zhengjie.modules.wms.bd.service.dto.CustomerInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.CustomerInfoDetailDTO;
import me.zhengjie.modules.wms.bd.service.dto.CustomerInfoQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author jie
* @date 2019-08-03
*/
//@CacheConfig(cacheNames = "bdCustomerInfo")
public interface CustomerInfoService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(CustomerInfoQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(CustomerInfoQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    CustomerInfoDetailDTO findById(Long id);

    /**
     * create
     * @param createCustomerInfoRequest
     * @return
     */
    //@CacheEvict(allEntries = true)
    CustomerInfoDTO create(CreateCustomerInfoRequest createCustomerInfoRequest);

    /**
     * update
     * @param updateCustomerInfoRequest
     */
    //@CacheEvict(allEntries = true)
    void update(UpdateCustomerInfoRequest updateCustomerInfoRequest);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}