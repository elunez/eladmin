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
package me.zhengjie.service;

import me.zhengjie.domain.QiniuConfig;
import me.zhengjie.domain.QiniuContent;
import me.zhengjie.service.dto.QiniuQueryCriteria;
import me.zhengjie.utils.PageResult;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-31
 */
public interface QiNiuService {

    /**
     * Query configuration
     * @return QiniuConfig
     */
    QiniuConfig find();

    /**
     * Update configuration
     * @param qiniuConfig configuration
     * @return QiniuConfig
     */
    QiniuConfig config(QiniuConfig qiniuConfig);

    /**
     * Paginated query
     * @param criteria criteria
     * @param pageable pagination parameters
     * @return /
     */
    PageResult<QiniuContent> queryAll(QiniuQueryCriteria criteria, Pageable pageable);

    /**
     * Query all
     * @param criteria criteria
     * @return /
     */
    List<QiniuContent> queryAll(QiniuQueryCriteria criteria);

    /**
     * Upload file
     * @param file file
     * @param qiniuConfig configuration
     * @return QiniuContent
     */
    QiniuContent upload(MultipartFile file, QiniuConfig qiniuConfig);

    /**
     * Query file
     * @param id file ID
     * @return QiniuContent
     */
    QiniuContent findByContentId(Long id);

    /**
     * Download file
     * @param content file information
     * @param config configuration
     * @return String
     */
    String download(QiniuContent content, QiniuConfig config);

    /**
     * Delete file
     * @param content file
     * @param config configuration
     */
    void delete(QiniuContent content, QiniuConfig config);

    /**
     * Sync data
     * @param config configuration
     */
    void synchronize(QiniuConfig config);

    /**
     * Delete file
     * @param ids file ID array
     * @param config configuration
     */
    void deleteAll(Long[] ids, QiniuConfig config);

    /**
     * Update data
     * @param type type
     */
    void update(String type);

    /**
     * Export data
     * @param queryAll /
     * @param response /
     * @throws IOException /
     */
    void downloadList(List<QiniuContent> queryAll, HttpServletResponse response) throws IOException;
}
