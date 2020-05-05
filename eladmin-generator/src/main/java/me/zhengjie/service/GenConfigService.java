/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.service;

import me.zhengjie.domain.GenConfig;

/**
 * @author Zheng Jie
 * @date 2019-01-14
 */
public interface GenConfigService {

    /**
     * 查询表配置
     * @param tableName 表名
     * @return 表配置
     */
    GenConfig find(String tableName);

    /**
     * 更新表配置
     * @param tableName 表名
     * @param genConfig 表配置
     * @return 表配置
     */
    GenConfig update(String tableName, GenConfig genConfig);
}
