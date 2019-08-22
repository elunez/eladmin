package me.zhengjie.modules.wms.bd.service;

import me.zhengjie.modules.wms.bd.domain.ProductInfo;
import me.zhengjie.modules.wms.bd.request.CreateProductInfoRequest;
import me.zhengjie.modules.wms.bd.request.UpdateProductInfoRequest;
import me.zhengjie.modules.wms.bd.service.dto.ProductInfoDTO;
import me.zhengjie.modules.wms.bd.service.dto.ProductInfoDetailDTO;
import me.zhengjie.modules.wms.bd.service.dto.ProductInfoQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author 黄星星
* @date 2019-07-27
*/
//@CacheConfig(cacheNames = "bdProductInfo")
public interface ProductInfoService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(ProductInfoQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(ProductInfoQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    ProductInfoDetailDTO findById(Long id);

    /**
     * create
     * @param createProductInfoRequest
     * @return
     */
    //@CacheEvict(allEntries = true)
    ProductInfoDetailDTO create(CreateProductInfoRequest createProductInfoRequest);

    /**
     * update
     * @param updateProductInfoRequest
     */
    //@CacheEvict(allEntries = true)
    void update(UpdateProductInfoRequest updateProductInfoRequest);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Integer id);
}