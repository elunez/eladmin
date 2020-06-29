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
package me.zhengjie.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import lombok.RequiredArgsConstructor;
import me.zhengjie.domain.QiniuConfig;
import me.zhengjie.domain.QiniuContent;
import me.zhengjie.repository.QiniuContentRepository;
import me.zhengjie.service.dto.QiniuQueryCriteria;
import me.zhengjie.utils.QiNiuUtil;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.repository.QiNiuConfigRepository;
import me.zhengjie.service.QiNiuService;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
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
@CacheConfig(cacheNames = "qiNiu")
public class QiNiuServiceImpl implements QiNiuService {

    private final QiNiuConfigRepository qiNiuConfigRepository;
    private final QiniuContentRepository qiniuContentRepository;

    @Value("${qiniu.max-size}")
    private Long maxSize;

    @Override
    @Cacheable(key = "'config'")
    public QiniuConfig find() {
        Optional<QiniuConfig> qiniuConfig = qiNiuConfigRepository.findById(1L);
        return qiniuConfig.orElseGet(QiniuConfig::new);
    }

    @Override
    @CachePut(key = "'config'")
    @Transactional(rollbackFor = Exception.class)
    public QiniuConfig config(QiniuConfig qiniuConfig) {
        qiniuConfig.setId(1L);
        String http = "http://", https = "https://";
        if (!(qiniuConfig.getHost().toLowerCase().startsWith(http)||qiniuConfig.getHost().toLowerCase().startsWith(https))) {
            throw new BadRequestException("外链域名必须以http://或者https://开头");
        }
        return qiNiuConfigRepository.save(qiniuConfig);
    }

    @Override
    public Object queryAll(QiniuQueryCriteria criteria, Pageable pageable){
        return PageUtil.toPage(qiniuContentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable));
    }

    @Override
    public List<QiniuContent> queryAll(QiniuQueryCriteria criteria) {
        return qiniuContentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QiniuContent upload(MultipartFile file, QiniuConfig qiniuConfig) {
        FileUtil.checkSize(maxSize, file.getSize());
        if(qiniuConfig.getId() == null){
            throw new BadRequestException("请先添加相应配置，再操作");
        }
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(qiniuConfig.getZone()));
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        String upToken = auth.uploadToken(qiniuConfig.getBucket());
        try {
            String key = file.getOriginalFilename();
            if(qiniuContentRepository.findByKey(key) != null) {
                key = QiNiuUtil.getKey(key);
            }
            Response response = uploadManager.put(file.getBytes(), key, upToken);
            //解析上传成功的结果

            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            QiniuContent content = qiniuContentRepository.findByKey(FileUtil.getFileNameNoEx(putRet.key));
            if(content == null){
                //存入数据库
                QiniuContent qiniuContent = new QiniuContent();
                qiniuContent.setSuffix(FileUtil.getExtensionName(putRet.key));
                qiniuContent.setBucket(qiniuConfig.getBucket());
                qiniuContent.setType(qiniuConfig.getType());
                qiniuContent.setKey(FileUtil.getFileNameNoEx(putRet.key));
                qiniuContent.setUrl(qiniuConfig.getHost()+"/"+putRet.key);
                qiniuContent.setSize(FileUtil.getSize(Integer.parseInt(file.getSize()+"")));
                return qiniuContentRepository.save(qiniuContent);
            }
            return content;
        } catch (Exception e) {
           throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public QiniuContent findByContentId(Long id) {
        QiniuContent qiniuContent = qiniuContentRepository.findById(id).orElseGet(QiniuContent::new);
        ValidationUtil.isNull(qiniuContent.getId(),"QiniuContent", "id",id);
        return qiniuContent;
    }

    @Override
    public String download(QiniuContent content,QiniuConfig config){
        String finalUrl;
        String type = "公开";
        if(type.equals(content.getType())){
            finalUrl  = content.getUrl();
        } else {
            Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
            // 1小时，可以自定义链接过期时间
            long expireInSeconds = 3600;
            finalUrl = auth.privateDownloadUrl(content.getUrl(), expireInSeconds);
        }
        return finalUrl;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(QiniuContent content, QiniuConfig config) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(config.getZone()));
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(content.getBucket(), content.getKey() + "." + content.getSuffix());
            qiniuContentRepository.delete(content);
        } catch (QiniuException ex) {
            qiniuContentRepository.delete(content);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void synchronize(QiniuConfig config) {
        if(config.getId() == null){
            throw new BadRequestException("请先添加相应配置，再操作");
        }
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(config.getZone()));
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(config.getBucket(), prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            QiniuContent qiniuContent;
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                if(qiniuContentRepository.findByKey(FileUtil.getFileNameNoEx(item.key)) == null){
                    qiniuContent = new QiniuContent();
                    qiniuContent.setSize(FileUtil.getSize(Integer.parseInt(item.fsize+"")));
                    qiniuContent.setSuffix(FileUtil.getExtensionName(item.key));
                    qiniuContent.setKey(FileUtil.getFileNameNoEx(item.key));
                    qiniuContent.setType(config.getType());
                    qiniuContent.setBucket(config.getBucket());
                    qiniuContent.setUrl(config.getHost()+"/"+item.key);
                    qiniuContentRepository.save(qiniuContent);
                }
            }
        }
    }

    @Override
    public void deleteAll(Long[] ids, QiniuConfig config) {
        for (Long id : ids) {
            delete(findByContentId(id), config);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String type) {
        qiNiuConfigRepository.update(type);
    }

    @Override
    public void downloadList(List<QiniuContent> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QiniuContent content : queryAll) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("文件名", content.getKey());
            map.put("文件类型", content.getSuffix());
            map.put("空间名称", content.getBucket());
            map.put("文件大小", content.getSize());
            map.put("空间类型", content.getType());
            map.put("创建日期", content.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
