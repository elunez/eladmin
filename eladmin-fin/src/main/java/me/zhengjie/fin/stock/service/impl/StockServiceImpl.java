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
package me.zhengjie.fin.stock.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.fin.stock.domain.Stock;
import me.zhengjie.fin.stock.repository.StockRepository;
import me.zhengjie.fin.stock.service.StockService;
import me.zhengjie.fin.stock.service.dto.StockDto;
import me.zhengjie.fin.stock.service.dto.StockQueryCriteria;
import me.zhengjie.fin.stock.service.mapstruct.StockMapper;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author zhangjc
 * @date 2023-02-15
 */
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    @Override
    public Object queryAll(StockQueryCriteria criteria, Pageable pageable) {
        Page<Stock> page = stockRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder),
                pageable);
        return PageUtil.toPage(page.map(stockMapper::toDto));
    }

    @Override
    public List<StockDto> queryAll(StockQueryCriteria criteria) {
        return stockMapper.toDto(stockRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public StockDto findById(Long id) {
        Stock server = stockRepository.findById(id).orElseGet(Stock::new);
        ValidationUtil.isNull(server.getId(), "Stock", "id", id);
        return stockMapper.toDto(server);
    }

    @Override
    public StockDto findByIp(String ip) {
        Stock deploy = stockRepository.findByIp(ip);
        return stockMapper.toDto(deploy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Stock resources) {
        stockRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Stock resources) {
        Stock stock = stockRepository.findById(resources.getId()).orElseGet(Stock::new);
        ValidationUtil.isNull(stock.getId(), "Stock", "id", resources.getId());
        stock.copy(resources);
        stockRepository.save(stock);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            stockRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StockDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StockDto deployDto : queryAll) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("服务器名称", deployDto.getName());
            map.put("服务器IP", deployDto.getIp());
            map.put("端口", deployDto.getPort());
            map.put("账号", deployDto.getAccount());
            map.put("创建日期", deployDto.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public Boolean testConnect(Stock resources) {
        // TODO Auto-generated method stub
        return null;
    }
}
