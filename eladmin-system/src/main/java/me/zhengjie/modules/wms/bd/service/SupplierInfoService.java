package me.zhengjie.modules.wms.bd.service;

import me.zhengjie.modules.wms.bd.domain.SupplierInfo;
import me.zhengjie.modules.wms.bd.request.CreateSupplierInfoRequest;
import me.zhengjie.modules.wms.bd.request.UpdateSupplierInfoRequest;
import me.zhengjie.modules.wms.bd.service.dto.SupplierInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.SupplierInfoDetailDTO;
import me.zhengjie.modules.wms.bd.service.dto.SupplierInfoQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author jie
* @date 2019-08-03
*/
//@CacheConfig(cacheNames = "bdSupplierInfo")
public interface SupplierInfoService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(SupplierInfoQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(SupplierInfoQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    SupplierInfoDetailDTO findById(Long id);

    /**
     * create
     * @param createSupplierInfoRequest
     * @return
     */
    //@CacheEvict(allEntries = true)
    SupplierInfoDTO create(CreateSupplierInfoRequest createSupplierInfoRequest);

    /**
     * update
     * @param updateSupplierInfoRequest
     */
    //@CacheEvict(allEntries = true)
    void updateSupplierInfo(UpdateSupplierInfoRequest updateSupplierInfoRequest);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}