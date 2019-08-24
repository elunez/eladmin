package me.zhengjie.modules.wms.bd.service;

import me.zhengjie.modules.wms.bd.domain.MaterialInfo;
import me.zhengjie.modules.wms.bd.request.CreateMaterialInfoRequest;
import me.zhengjie.modules.wms.bd.service.dto.MaterialInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.MaterialInfoDetailDTO;
import me.zhengjie.modules.wms.bd.service.dto.MaterialInfoQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author 黄星星
* @date 2019-07-27
*/
//@CacheConfig(cacheNames = "bdMaterialInfo")
public interface MaterialInfoService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(MaterialInfoQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(MaterialInfoQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    MaterialInfoDetailDTO findById(Long id);

    /**
     * create
     * @param createMaterialInfoRequest
     * @return
     */
    //@CacheEvict(allEntries = true)
    MaterialInfoDetailDTO create(CreateMaterialInfoRequest createMaterialInfoRequest);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(MaterialInfo resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(long id);
}