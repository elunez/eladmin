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

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.config.AmzS3Config;
import me.zhengjie.domain.S3Storage;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.repository.S3StorageRepository;
import me.zhengjie.service.S3StorageService;
import me.zhengjie.service.dto.S3StorageQueryCriteria;
import me.zhengjie.utils.*;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
* @description 服务实现
* @author Zheng Jie
* @date 2025-06-25
**/
@Slf4j
@Service
@RequiredArgsConstructor
public class S3StorageServiceImpl implements S3StorageService {

    private final S3Client s3Client;
    private final AmzS3Config amzS3Config;
    private final S3StorageRepository s3StorageRepository;

    @Override
    public S3Storage getById(Long id) {
        return s3StorageRepository.findById(id).orElse(null);
    }

    @Override
    public PageResult<S3Storage> queryAll(S3StorageQueryCriteria criteria, Pageable pageable){
        Page<S3Storage> page = s3StorageRepository.findAll((root, criteriaQuery, criteriaBuilder)
                -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page);
    }

    @Override
    public List<S3Storage> queryAll(S3StorageQueryCriteria criteria){
        return s3StorageRepository.findAll((root, criteriaQuery, criteriaBuilder)
                -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        // 检查桶是否存在
        String bucketName = amzS3Config.getDefaultBucket();
        if (!bucketExists(bucketName)) {
            throw new BadRequestException("存储桶不存在，请检查配置或权限。");
        }
        // 遍历 ID 列表，删除对应的文件和数据库记录
        for (Long id : ids) {
            String filePath = s3StorageRepository.selectFilePathById(id);
            if (filePath == null) {
                System.err.println("未找到 ID 为 " + id + " 的文件记录，无法删除。");
                continue;
            }
            try {
                // 创建 DeleteObjectRequest，指定存储桶和文件键
                DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(filePath)
                        .build();
                // 调用 deleteObject 方法
                s3Client.deleteObject(deleteObjectRequest);
                // 删除数据库数据
                s3StorageRepository.deleteById(id);
            } catch (S3Exception e) {
                // 处理 AWS 特定的异常
                log.error("从 S3 删除文件时出错: {}", e.awsErrorDetails().errorMessage(), e);
            }
        }
    }

    @Override
    public S3Storage upload(MultipartFile file) {
        String bucketName = amzS3Config.getDefaultBucket();
        // 检查存储桶是否存在
        if (!bucketExists(bucketName)) {
            log.warn("存储桶 {} 不存在，尝试创建...", bucketName);
            if (createBucket(bucketName)){
                log.info("存储桶 {} 创建成功。", bucketName);
            } else {
                throw new BadRequestException("存储桶创建失败，请检查配置或权限。");
            }
        }
        // 获取文件名
        String originalName = file.getOriginalFilename();
        if (StringUtils.isBlank(originalName)) {
            throw new IllegalArgumentException("文件名不能为空");
        }
        // 生成存储路径和文件名
        String folder = DateUtil.format(new Date(), amzS3Config.getTimeformat());
        String fileName = IdUtil.simpleUUID() + "." + FileUtil.getExtensionName(originalName);
        String filePath = folder + "/" + fileName;
        // 构建上传请求
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(amzS3Config.getDefaultBucket())
                .key(filePath)
                .build();
        // 创建 S3Storage 实例
        S3Storage s3Storage = new S3Storage();
        try {
            // 上传文件到 S3
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            // 设置 S3Storage 属性
            s3Storage.setFileMimeType(FileUtil.getMimeType(originalName));
            s3Storage.setFileName(originalName);
            s3Storage.setFileRealName(fileName);
            s3Storage.setFileSize(FileUtil.getSize(file.getSize()));
            s3Storage.setFileType(FileUtil.getExtensionName(originalName));
            s3Storage.setFilePath(filePath);
            // 保存入库
            s3StorageRepository.save(s3Storage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 设置地址
        return s3Storage;
    }

    @Override
    public void download(List<S3Storage> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (S3Storage s3Storage : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("文件名称", s3Storage.getFileName());
            map.put("真实存储的名称", s3Storage.getFileRealName());
            map.put("文件大小", s3Storage.getFileSize());
            map.put("文件MIME 类型", s3Storage.getFileMimeType());
            map.put("文件类型", s3Storage.getFileType());
            map.put("文件路径", s3Storage.getFilePath());
            map.put("创建者", s3Storage.getCreateBy());
            map.put("更新者", s3Storage.getUpdateBy());
            map.put("创建日期", s3Storage.getCreateTime());
            map.put("更新时间", s3Storage.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    public Map<String, String> privateDownload(Long id) {
        S3Storage storage = s3StorageRepository.findById(id).orElse(null);
        if (storage == null) {
            throw new BadRequestException("文件不存在或已被删除");
        }
        // 创建 GetObjectRequest，指定存储桶和文件键
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(amzS3Config.getDefaultBucket())
                .key(storage.getFilePath())
                .build();
        String base64Data;
        // 使用 try-with-resources 确保流能被自动关闭
        // s3Client.getObject() 返回一个 ResponseInputStream，它是一个包含S3对象数据的输入流
        try (ResponseInputStream<GetObjectResponse> s3InputStream = s3Client.getObject(getObjectRequest)) {
            // 使用 IOUtils.toByteArray 将输入流直接转换为字节数组
            byte[] fileBytes = IOUtils.toByteArray(s3InputStream);
            // 使用 Java 内置的 Base64 编码器将字节数组转换为 Base64 字符串
            base64Data = Base64.getEncoder().encodeToString(fileBytes);
        } catch (S3Exception e) {
            // 处理 AWS 特定的异常
            throw new BadRequestException("从 S3 下载文件时出错: " + e.awsErrorDetails().errorMessage());
        } catch (IOException e) {
            // 处理通用的 IO 异常 (IOUtils.toByteArray 可能会抛出)
            throw new BadRequestException("读取 S3 输入流时出错: " + e.getMessage());
        }
        // 构造返回数据
        Map<String, String> responseData = new HashMap<>();
        // 文件名
        responseData.put("fileName", storage.getFileName());
        // 文件类型
        responseData.put("fileMimeType", storage.getFileMimeType());
        // 文件内容
        responseData.put("base64Data", base64Data);
        return responseData;
    }

    /**
     * 检查云存储桶是否存在
     * @param bucketName 存储桶名称
     */
    @SuppressWarnings({"all"})
    private boolean bucketExists(String bucketName) {
        try {
            HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            s3Client.headBucket(headBucketRequest);
            return true;
        } catch (S3Exception e) {
            // 如果状态码是 404 (Not Found), 说明存储桶不存在
            if (e.statusCode() == 404) {
                log.error("存储桶 '{}' 不存在。", bucketName);
                return false;
            }
            // 其他异常 (如 403 Forbidden) 说明存在问题，但不能断定它不存在
            throw new BadRequestException("检查存储桶时出错: " + e.awsErrorDetails().errorMessage());
        }
    }

    /**
     * 创建云存储桶
     * @param bucketName 存储桶名称
     */
    private boolean createBucket(String bucketName) {
        try {
            // 使用 S3Waiter 等待存储桶创建完成
            S3Waiter s3Waiter = s3Client.waiter();
            CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .acl(BucketCannedACL.PRIVATE)
                    .build();
            s3Client.createBucket(bucketRequest);
            // 等待直到存储桶创建完成
            HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            // 使用 WaiterResponse 等待存储桶存在
            WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(bucketRequestWait);
            waiterResponse.matched().response().ifPresent(response ->
                    log.info("存储桶 '{}' 创建成功，状态: {}", bucketName, response.sdkHttpResponse().statusCode())
            );
        } catch (BucketAlreadyOwnedByYouException e) {
            log.warn("存储桶 '{}' 已经被您拥有，无需重复创建。", bucketName);
        } catch (S3Exception e) {
            throw new BadRequestException("创建存储桶时出错: " + e.awsErrorDetails().errorMessage());
        }
        return true;
    }
}