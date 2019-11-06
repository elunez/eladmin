package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.FileSort;
import me.zhengjie.modules.system.service.dto.FileSortDTO;
import me.zhengjie.modules.system.service.dto.FileSortQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@CacheConfig(cacheNames = "fileSort")
public interface FileSortService {

    /**
     * queryAll
     * @param criteria
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    List<FileSortDTO> queryAll(FileSortQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    FileSortDTO findById(String id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    FileSortDTO create(FileSort resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(FileSort resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(String id);

    /**
     * buildTree
     * @param fileSortDTOS
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Object buildTree(List<FileSortDTO> fileSortDTOS);

    /**
     * findByPid
     * @param pid
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    List<FileSort> findByPid(String pid);

//    Set<Dept> findByRoleIds(Long id);
}