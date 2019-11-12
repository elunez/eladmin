package me.zhengjie.modules.wms.qualityCheckSheet.service;

import me.zhengjie.modules.wms.qualityCheckSheet.domain.QualityCheckSheet;
import me.zhengjie.modules.wms.qualityCheckSheet.request.CreateQualityCheckSheetRequest;
import me.zhengjie.modules.wms.qualityCheckSheet.request.UpdateQualityCheckSheetRequest;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetDTO;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author huangxingxing
* @date 2019-11-12
*/
//@CacheConfig(cacheNames = "qualityCheckSheet")
public interface QualityCheckSheetService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(QualityCheckSheetQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(QualityCheckSheetQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    QualityCheckSheetDTO findById(Long id);

    /**
     * create
     * @param createQualityCheckSheetRequest
     * @return
     */
    //@CacheEvict(allEntries = true)
    QualityCheckSheetDTO create(CreateQualityCheckSheetRequest createQualityCheckSheetRequest);

    /**
     * update
     * @param updateQualityCheckSheetRequest
     */
    //@CacheEvict(allEntries = true)
    void update(UpdateQualityCheckSheetRequest updateQualityCheckSheetRequest);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}