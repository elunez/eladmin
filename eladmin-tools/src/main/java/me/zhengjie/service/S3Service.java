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

import me.zhengjie.domain.S3Content;
import me.zhengjie.domain.S3Config;
import me.zhengjie.service.dto.S3QueryCriteria;
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
public interface S3Service {

    /**
     * Query configuration
     * @return S3Config
     */
    S3Config find();

    /**
     * Update configuration
     * @param s3Config configuration
     * @return S3Config
     */
    S3Config config(S3Config s3Config);

    /**
     * Paginated query
     * @param criteria criteria
     * @param pageable pagination parameters
     * @return /
     */
    PageResult<S3Content> queryAll(S3QueryCriteria criteria, Pageable pageable);

    /**
     * Query all
     * @param criteria criteria
     * @return /
     */
    List<S3Content> queryAll(S3QueryCriteria criteria);

    /**
     * Upload file
     * @param file file
     * @param s3Config configuration
     * @return S3Content
     */
    S3Content upload(MultipartFile file, S3Config s3Config);

    /**
     * Query file
     * @param id file ID
     * @return S3Content
     */
    S3Content findByContentId(Long id);

    /**
     * Download file
     * @param content file information
     * @param config configuration
     * @return String
     */
    String download(S3Content content, S3Config config);

    /**
     * Delete file
     * @param content file
     * @param config configuration
     */
    void delete(S3Content content, S3Config config);

    /**
     * Sync data
     * @param config configuration
     */
    void synchronize(S3Config config);

    /**
     * Delete file
     * @param ids file ID array
     * @param config configuration
     */
    void deleteAll(Long[] ids, S3Config config);

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
    void downloadList(List<S3Content> queryAll, HttpServletResponse response) throws IOException;
}
