package me.zhengjie.modules.wms.bd.service;

import me.zhengjie.modules.wms.bd.domain.OutSourceCompanyInfo;
import me.zhengjie.modules.wms.bd.request.CreateOutSourceCompanyInfoRequest;
import me.zhengjie.modules.wms.bd.service.dto.OutSourceCompanyInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.OutSourceCompanyInfoQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author jie
* @date 2019-08-03
*/
//@CacheConfig(cacheNames = "bdOutSourceCompanyInfo")
public interface OutSourceCompanyInfoService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(OutSourceCompanyInfoQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(OutSourceCompanyInfoQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    OutSourceCompanyInfoDTO findById(Long id);

    /**
     * create
     * @param createOutSourceCompanyInfoRequest
     * @return
     */
    //@CacheEvict(allEntries = true)
    OutSourceCompanyInfoDTO create(CreateOutSourceCompanyInfoRequest createOutSourceCompanyInfoRequest);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(OutSourceCompanyInfo resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}