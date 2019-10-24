package me.zhengjie.service;

import me.zhengjie.domain.GenConfig;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author Zheng Jie
 * @date 2019-01-14
 */
public interface GenConfigService {

    GenConfig find();

    GenConfig update(GenConfig genConfig);
}
