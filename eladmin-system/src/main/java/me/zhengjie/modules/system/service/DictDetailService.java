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
package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.DictDetail;
import me.zhengjie.modules.system.service.dto.DictDetailDto;
import me.zhengjie.modules.system.service.dto.DictDetailQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
public interface DictDetailService {

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    DictDetailDto findById(Long id);

    /**
     * 创建
     * @param resources /
     * @return /
     */
    DictDetailDto create(DictDetail resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(DictDetail resources);

    /**
     * 删除
     * @param id /
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String,Object> queryAll(DictDetailQueryCriteria criteria, Pageable pageable);
}