package me.zhengjie.modules.wms.outSourceProductSheet.service;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificateProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateProductDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateProductQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author jie
* @date 2019-10-01
*/
//@CacheConfig(cacheNames = "sOutSourceInspectionCertificateProduct")
public interface OutSourceInspectionCertificateProductService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(OutSourceInspectionCertificateProductQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(OutSourceInspectionCertificateProductQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    OutSourceInspectionCertificateProductDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    OutSourceInspectionCertificateProductDTO create(OutSourceInspectionCertificateProduct resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(OutSourceInspectionCertificateProduct resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}