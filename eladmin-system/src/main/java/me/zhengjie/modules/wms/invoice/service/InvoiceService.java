package me.zhengjie.modules.wms.invoice.service;

import me.zhengjie.modules.wms.invoice.domain.Invoice;
import me.zhengjie.modules.wms.invoice.request.CreateInvoiceRequest;
import me.zhengjie.modules.wms.invoice.request.UpdateInvoiceRequest;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceDTO;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceDetailDTO;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author jie
* @date 2019-08-27
*/
//@CacheConfig(cacheNames = "sInvoice")
public interface InvoiceService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(InvoiceQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(InvoiceQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    InvoiceDetailDTO findById(Long id);

    /**
     * create
     * @param createInvoiceRequest
     * @return
     */
    //@CacheEvict(allEntries = true)
    InvoiceDetailDTO create(CreateInvoiceRequest createInvoiceRequest);

    /**
     * update
     * @param updateInvoiceRequest
     */
    //@CacheEvict(allEntries = true)
    void update(UpdateInvoiceRequest updateInvoiceRequest);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}