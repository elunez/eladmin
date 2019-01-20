package ${package}.service;

import ${package}.domain.${className};
import ${package}.service.dto.${className}DTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
* @author ${author}
* @date ${date}
*/
@CacheConfig(cacheNames = "${changeClassName}")
public interface ${className}Service {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    ${className}DTO findById(${pkColumnType} id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    ${className}DTO create(${className} resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(${className} resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);
}