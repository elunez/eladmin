package me.zhengjie.service;

import me.zhengjie.domain.LocalStorage;
import me.zhengjie.service.dto.LocalStorageDTO;
import me.zhengjie.service.dto.LocalStorageQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@CacheConfig(cacheNames = "localStorage")
public interface LocalStorageService {

    /**
    * queryAll 分页
    * @param criteria 条件参数
    * @param pageable 分页参数
    * @return Object
    */
    @Cacheable
    Object queryAll(LocalStorageQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria 条件参数
    * @return Object
    */
    @Cacheable
    Object queryAll(LocalStorageQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return LocalStorageDTO
     */
    @Cacheable(key = "#p0")
    LocalStorageDTO findById(Long id);

    /**
     * create
     * @param name 文件名称
     * @param file 文件资源
     * @return LocalStorageDTO
     */
    @CacheEvict(allEntries = true)
    LocalStorageDTO create(String name, MultipartFile file);

    /**
     * update
     * @param resources 资源实体
     */
    @CacheEvict(allEntries = true)
    void update(LocalStorage resources);

    /**
     * delete
     * @param id 文件ID
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * 多文件删除
     * @param ids 文件数组
     */
    @CacheEvict(allEntries = true)
    void deleteAll(Long[] ids);
}