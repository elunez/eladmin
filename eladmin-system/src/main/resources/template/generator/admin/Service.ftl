package ${package}.service;

import ${package}.domain.${className};
import ${package}.service.dto.${className}DTO;
import ${package}.service.dto.${className}QueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author ${author}
* @date ${date}
*/
//@CacheConfig(cacheNames = "${changeClassName}")
public interface ${className}Service {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(${className}QueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(${className}QueryCriteria criteria);

    /**
     * findById
     * @param ${pkChangeColName}
     * @return
     */
    //@Cacheable(key = "#p0")
    ${className}DTO findById(${pkColumnType} ${pkChangeColName});

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ${className}DTO create(${className} resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(${className} resources);

    /**
     * delete
     * @param ${pkChangeColName}
     */
    //@CacheEvict(allEntries = true)
    void delete(${pkColumnType} ${pkChangeColName});
}