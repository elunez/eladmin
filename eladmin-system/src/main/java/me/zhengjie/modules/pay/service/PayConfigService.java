package me.zhengjie.modules.pay.service;

import me.zhengjie.modules.pay.domain.PayConfig;
import me.zhengjie.modules.pay.service.dto.PayConfigDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xpay
 * @date 2019-06-02
 */
@CacheConfig(cacheNames = "payConfig")
public interface PayConfigService {

    /**
     * findById
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    PayConfigDTO findById(Long id);

    /**
     * create
     *
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    PayConfigDTO create(PayConfig resources);

    /**
     * update
     *
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(PayConfig resources);

    /**
     * delete
     *
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * 上传图片
     *
     * @param file
     * @param uid
     * @param type
     * @return
     */
    @CacheEvict(allEntries = true)
    PayConfig upload(MultipartFile file, Long uid, String type);
}