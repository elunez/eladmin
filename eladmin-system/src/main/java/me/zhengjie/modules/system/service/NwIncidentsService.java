package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.NwIncidents;
import me.zhengjie.modules.system.service.dto.NwIncidentsDTO;
import me.zhengjie.modules.system.service.dto.NwIncidentsQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author Bobby Kimutai
* @date 2019-08-05
*/
//@CacheConfig(cacheNames = "nwIncidents")
public interface NwIncidentsService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(NwIncidentsQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(NwIncidentsQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    NwIncidentsDTO findById(Integer id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    NwIncidentsDTO create(NwIncidents resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(NwIncidents resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Integer id);
}