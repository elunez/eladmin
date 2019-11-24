package me.zhengjie.service;

import me.zhengjie.domain.GenConfig;

/**
 * @author Zheng Jie
 * @date 2019-01-14
 */
public interface GenConfigService {

    GenConfig find(String tableName);

    GenConfig update(String tableName, GenConfig genConfig);
}
