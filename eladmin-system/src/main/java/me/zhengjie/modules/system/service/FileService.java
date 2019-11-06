package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.FileModel;
import me.zhengjie.modules.system.service.dto.FileDTO;
import me.zhengjie.modules.system.service.dto.FileQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@CacheConfig(cacheNames = "file")
public interface FileService {

    /**
     * queryAll
     * @param criteria
     * @param pageable
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(FileQueryCriteria criteria, Pageable pageable);

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    FileDTO findById(String id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    FileDTO create(FileModel resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(FileModel resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(String id);

    /**
     * buildTree
     * @param fileDTOS
     * @return
     */
//    @Cacheable(keyGenerator = "keyGenerator")
//    Object buildTree(List<FileDTO> fileDTOS);
//
//    /**
//     * findByPid
//     * @param pid
//     * @return
//     */
//    @Cacheable(keyGenerator = "keyGenerator")
//    List<FileModel> findByPid(String pid);

//    Set<Dept> findByRoleIds(Long id);
}