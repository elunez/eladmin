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

import com.alibaba.fastjson2.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import lombok.RequiredArgsConstructor;
import me.zhengjie.domain.S3Content;
import me.zhengjie.domain.S3Config;
import me.zhengjie.repository.S3ConfigRepository;
import me.zhengjie.repository.S3ContentRepository;
import me.zhengjie.service.dto.S3QueryCriteria;
import me.zhengjie.utils.*;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.service.S3Service;
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
        FileUtil.checkSize(maxSize, file.getSize());
        if(s3Config.getId() == null){
            throw new BadRequestException("Please add the corresponding configuration first, then operate");
        }
        // Construct a configuration class with the specified Zone object
        Configuration cfg = new Configuration(S3Util.getRegion(s3Config.getZone()));
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(s3Config.getAccessKey(), s3Config.getSecretKey());
        String upToken = auth.uploadToken(s3Config.getBucket());
        try {
            String key = file.getOriginalFilename();
            if(s3ContentRepository.findByKey(key) != null) {
                key = S3Util.getKey(key);
            }
            Response response = uploadManager.put(file.getBytes(), key, upToken);
            // Parse the result of successful upload
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            S3Content content = s3ContentRepository.findByKey(FileUtil.getFileNameNoEx(putRet.key));
            if(content == null){
                // Store in database
                S3Content s3Content = new S3Content();
                s3Content.setSuffix(FileUtil.getExtensionName(putRet.key));
                s3Content.setBucket(s3Config.getBucket());
                s3Content.setType(s3Config.getType());
                s3Content.setKey(FileUtil.getFileNameNoEx(putRet.key));
                s3Content.setUrl(s3Config.getHost()+"/"+putRet.key);
                s3Content.setSize(FileUtil.getSize(Integer.parseInt(String.valueOf(file.getSize()))));
                return s3ContentRepository.save(s3Content);
            }
            return content;
        } catch (Exception e) {
           throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public S3Content findByContentId(Long id) {
        S3Content s3Content = s3ContentRepository.findById(id).orElseGet(S3Content::new);
        ValidationUtil.isNull(s3Content.getId(),"S3Content", "id",id);
        return s3Content;
    }

    @Override
    public String download(S3Content content,S3Config config){
        String finalUrl;
        String type = "Public";
        if(type.equals(content.getType())){
            finalUrl  = content.getUrl();
        } else {
            Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
            // 1 hour, can customize link expiration time
            long expireInSeconds = 3600;
            finalUrl = auth.privateDownloadUrl(content.getUrl(), expireInSeconds);
        }
        return finalUrl;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(S3Content content, S3Config config) {
        // Construct a configuration class with the specified Zone object
        Configuration cfg = new Configuration(S3Util.getRegion(config.getZone()));
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(content.getBucket(), content.getKey() + "." + content.getSuffix());
            s3ContentRepository.delete(content);
        } catch (QiniuException ex) {
            s3ContentRepository.delete(content);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void synchronize(S3Config config) {
        if(config.getId() == null){
            throw new BadRequestException("Please add the corresponding configuration first, then operate");
        }
        // Construct a configuration class with the specified Zone object
        Configuration cfg = new Configuration(S3Util.getRegion(config.getZone()));
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        // File name prefix
        String prefix = "";
        // Length limit for each iteration, maximum 1000, recommended value 1000
        int limit = 1000;
        // Specify directory separator, list all common prefixes (simulate directory listing effect). Default value is empty string
        String delimiter = "";
        // List space file list
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(config.getBucket(), prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            // Process the obtained file list result
            S3Content s3Content;
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                if(s3ContentRepository.findByKey(FileUtil.getFileNameNoEx(item.key)) == null){
                    s3Content = new S3Content();
                    s3Content.setSize(FileUtil.getSize(Integer.parseInt(String.valueOf(item.fsize))));
                    s3Content.setSuffix(FileUtil.getExtensionName(item.key));
                    s3Content.setKey(FileUtil.getFileNameNoEx(item.key));
                    s3Content.setType(config.getType());
                    s3Content.setBucket(config.getBucket());
                    s3Content.setUrl(config.getHost()+"/"+item.key);
                    s3ContentRepository.save(s3Content);
                }
            }
        }
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
