package ${package}.service;

import ${package}.domain.${className};
import ${package}.service.dto.${className}DTO;
import ${package}.service.dto.${className}QueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;

/**
* @author ${author}
* @date ${date}
*/
//@CacheConfig(cacheNames = "${changeClassName}")
public interface ${className}Service {

    /**
    * 查询数据分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable
    Map<String,Object> queryAll(${className}QueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria
    * @return
    */
    //@Cacheable
    List<${className}DTO> queryAll(${className}QueryCriteria criteria);

    /**
     * 根据ID查询
     * @param ${pkChangeColName}
     * @return
     */
    //@Cacheable(key = "#p0")
    ${className}DTO findById(${pkColumnType} ${pkChangeColName});

    /**
     * 创建
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ${className}DTO create(${className} resources);

    /**
     * 编辑
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(${className} resources);

    /**
     * 删除
     * @param ${pkChangeColName}
     */
    //@CacheEvict(allEntries = true)
    void delete(${pkColumnType} ${pkChangeColName});
}