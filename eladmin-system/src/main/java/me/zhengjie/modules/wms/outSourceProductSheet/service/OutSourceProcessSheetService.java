package me.zhengjie.modules.wms.outSourceProductSheet.service;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import me.zhengjie.modules.wms.outSourceProductSheet.request.CreateOutSourceProcessSheetRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.request.QueryOutSourceProcessSheetProductRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.request.UpdateOutSourceProcessSheetRequest;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
* @author jie
* @date 2019-08-17
*/
//@CacheConfig(cacheNames = "sOutSourceProcessSheet")
public interface OutSourceProcessSheetService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(OutSourceProcessSheetQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(OutSourceProcessSheetQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    OutSourceProcessSheetDTO findById(Long id);

    /**
     * create
     * @param createOutSourceProcessSheetRequest
     * @return
     */
    //@CacheEvict(allEntries = true)
    OutSourceProcessSheetDTO create(CreateOutSourceProcessSheetRequest createOutSourceProcessSheetRequest);

    /**
     * update
     * @param updateOutSourceProcessSheetRequest
     */
    //@CacheEvict(allEntries = true)
    void update(UpdateOutSourceProcessSheetRequest updateOutSourceProcessSheetRequest);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);

}