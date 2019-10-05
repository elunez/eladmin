package me.zhengjie.modules.wms.bd.service;

import me.zhengjie.modules.wms.bd.domain.ConsumablesInfo;
import me.zhengjie.modules.wms.bd.service.dto.ConsumablesInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.ConsumablesInfoQueryCriteria;
import org.springframework.data.domain.Pageable;

/**
* @author jie
* @date 2019-10-05
*/
//@CacheConfig(cacheNames = "bdConsumablesInfo")
public interface ConsumablesInfoService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(ConsumablesInfoQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ConsumablesInfoQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ConsumablesInfoDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ConsumablesInfoDTO create(ConsumablesInfo resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(ConsumablesInfo resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}