package me.zhengjie.modules.wms.invoice.service;

import me.zhengjie.modules.wms.invoice.domain.InvoiceProduct;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceProductDTO;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceProductQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author jie
* @date 2019-08-27
*/
//@CacheConfig(cacheNames = "sInvoiceProduct")
public interface InvoiceProductService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(InvoiceProductQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(InvoiceProductQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    InvoiceProductDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    InvoiceProductDTO create(InvoiceProduct resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(InvoiceProduct resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}