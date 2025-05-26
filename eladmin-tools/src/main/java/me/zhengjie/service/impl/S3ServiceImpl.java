/*
 *  Copyright 2019-2025 Zheng Jie
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
package me.zhengjie.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.domain.S3Config;
import me.zhengjie.domain.S3Content;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.repository.S3ConfigRepository;
import me.zhengjie.repository.S3ContentRepository;
import me.zhengjie.service.S3Service;
import me.zhengjie.service.dto.S3QueryCriteria;
import me.zhengjie.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author Zheng Jie
 * @date 2018-12-31
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "s3")
public class S3ServiceImpl implements S3Service {

    private final S3ConfigRepository s3ConfigRepository;
    private final S3ContentRepository s3ContentRepository;

    @Value("${s3.max-size}")
    private Long maxSize;

    @Override
    @Cacheable(key = "'config'")
    public S3Config find() {
        Optional<S3Config> s3Config = s3ConfigRepository.findById(1L);
        return s3Config.orElseGet(S3Config::new);
    }

    @Override
    @CachePut(key = "'config'")
    @Transactional(rollbackFor = Exception.class)
    public S3Config config(S3Config s3Config) {
        s3Config.setId(1L);
        String http = "http://", https = "https://";
        if (!(s3Config.getHost().toLowerCase().startsWith(http)||s3Config.getHost().toLowerCase().startsWith(https))) {
            throw new BadRequestException("External link domain must start with http:// or https://");
        }
        return s3ConfigRepository.save(s3Config);
    }

    @Override
    public PageResult<S3Content> queryAll(S3QueryCriteria criteria, Pageable pageable){
        return PageUtil.toPage(s3ContentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable));
    }

    @Override
    public List<S3Content> queryAll(S3QueryCriteria criteria) {
        return s3ContentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public S3Content upload(MultipartFile file, S3Config s3Config) {
        return null;
    }

    @Override
    public S3Content findByContentId(Long id) {
        S3Content s3Content = s3ContentRepository.findById(id).orElseGet(S3Content::new);
        ValidationUtil.isNull(s3Content.getId(),"S3Content", "id",id);
        return s3Content;
    }

    @Override
    public String download(S3Content content,S3Config config){
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(S3Content content, S3Config config) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void synchronize(S3Config config) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Long[] ids, S3Config config) {
        for (Long id : ids) {
            delete(findByContentId(id), config);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String type) {
        s3ConfigRepository.update(type);
    }

    @Override
    public void downloadList(List<S3Content> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (S3Content content : queryAll) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("File name", content.getKey());
            map.put("File type", content.getSuffix());
            map.put("Space name", content.getBucket());
            map.put("File size", content.getSize());
            map.put("Space type", content.getType());
            map.put("Creation date", content.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
