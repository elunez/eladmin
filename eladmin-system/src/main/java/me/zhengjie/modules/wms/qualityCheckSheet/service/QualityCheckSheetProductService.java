package me.zhengjie.modules.wms.qualityCheckSheet.service;

import me.zhengjie.modules.wms.qualityCheckSheet.domain.QualityCheckSheetProduct;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetProductDTO;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetProductQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author huangxingxing
* @date 2019-11-12
*/
//@CacheConfig(cacheNames = "qualityCheckSheetProduct")
public interface QualityCheckSheetProductService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(QualityCheckSheetProductQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(QualityCheckSheetProductQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    QualityCheckSheetProductDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    QualityCheckSheetProductDTO create(QualityCheckSheetProduct resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(QualityCheckSheetProduct resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}