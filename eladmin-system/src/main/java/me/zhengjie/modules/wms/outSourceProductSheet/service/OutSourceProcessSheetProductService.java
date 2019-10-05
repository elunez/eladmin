package me.zhengjie.modules.wms.outSourceProductSheet.service;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductDTO;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
* @author jie
* @date 2019-08-17
*/
//@CacheConfig(cacheNames = "sOutSourceProcessSheetProduct")
public interface OutSourceProcessSheetProductService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(OutSourceProcessSheetProductQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(OutSourceProcessSheetProductQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    OutSourceProcessSheetProductDTO findById(Long id);


    /**
     * 查询委外加工单产品信息
     * @param outSourceProcessSheetProductId
     * @return
     */
    List<OutSourceProcessSheetProductDTO> findByOutSourceProcessSheetProductId(Long outSourceProcessSheetProductId);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    OutSourceProcessSheetProductDTO create(OutSourceProcessSheetProduct resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(OutSourceProcessSheetProduct resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}