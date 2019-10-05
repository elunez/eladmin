package me.zhengjie.modules.wms.outSourceProductSheet.service;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificate;
import me.zhengjie.modules.wms.outSourceProductSheet.request.CreateOutSourceInspectionCertificateRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author jie
* @date 2019-10-01
*/
//@CacheConfig(cacheNames = "sOutSourceInspectionCertificate")
public interface OutSourceInspectionCertificateService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(OutSourceInspectionCertificateQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(OutSourceInspectionCertificateQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    OutSourceInspectionCertificateDTO findById(Long id);

    /**
     * create
     * @param createOutSourceInspectionCertificateRequest
     * @return
     */
    //@CacheEvict(allEntries = true)
    OutSourceInspectionCertificateDTO create(CreateOutSourceInspectionCertificateRequest createOutSourceInspectionCertificateRequest);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(OutSourceInspectionCertificate resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}