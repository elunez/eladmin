package me.zhengjie.service;

import me.zhengjie.domain.GenConfig;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author jie
 * @date 2019-01-14
 */
@CacheConfig(cacheNames = "genConfig")
public interface GenConfigService {

    /**
     * find
     * @return
     */
    @Cacheable(key = "'1'")
    GenConfig find();

    /**
     * update
     * @param genConfig
     */
    @CachePut(key = "'1'")
    GenConfig update(GenConfig genConfig);
}
