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
package me.zhengjie.fin.stock.service;

import me.zhengjie.fin.stock.domain.Stock;
import me.zhengjie.fin.stock.service.dto.StockDto;
import me.zhengjie.fin.stock.service.dto.StockQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author zhangjc
 * @date 2023-02-15
 */
public interface StockService {

    /**
     * 分页查询
     * 
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(StockQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     * 
     * @param criteria 条件
     * @return /
     */
    List<StockDto> queryAll(StockQueryCriteria criteria);

    /**
     * 根据ID查询
     * 
     * @param id /
     * @return /
     */
    StockDto findById(Long id);

    /**
     * 创建
     * 
     * @param resources /
     */
    void create(Stock resources);

    /**
     * 编辑
     * 
     * @param resources /
     */
    void update(Stock resources);

    /**
     * 删除
     * 
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 根据IP查询
     * 
     * @param ip /
     * @return /
     */
    StockDto findByIp(String ip);

    /**
     * 测试登录服务器
     * 
     * @param resources /
     * @return /
     */
    Boolean testConnect(Stock resources);

    /**
     * 导出数据
     * 
     * @param queryAll /
     * @param response /
     * @throws IOException /
     */
    void download(List<StockDto> queryAll, HttpServletResponse response) throws IOException;
}
