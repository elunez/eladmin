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

import me.zhengjie.domain.LocalStorage;
import me.zhengjie.service.dto.LocalStorageDto;
import me.zhengjie.service.dto.LocalStorageQueryCriteria;
import me.zhengjie.utils.PageResult;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
public interface LocalStorageService {

    /**
     * Paginated query
     * @param criteria criteria
     * @param pageable pagination parameters
     * @return /
     */
    PageResult<LocalStorageDto> queryAll(LocalStorageQueryCriteria criteria, Pageable pageable);

    /**
     * Query all data
     * @param criteria criteria
     * @return /
     */
    List<LocalStorageDto> queryAll(LocalStorageQueryCriteria criteria);

    /**
     * Query by ID
     * @param id /
     * @return /
     */
    LocalStorageDto findById(Long id);

    /**
     * Upload
     * @param name file name
     * @param file file
     * @return /
     */
    LocalStorage create(String name, MultipartFile file);

    /**
     * Edit
     * @param resources file information
     */
    void update(LocalStorage resources);

    /**
     * Multi-select delete
     * @param ids /
     */
    void deleteAll(Long[] ids);

    /**
     * Export data
     * @param localStorageDtos data to be exported
     * @param response /
     * @throws IOException /
     */
    void download(List<LocalStorageDto> localStorageDtos, HttpServletResponse response) throws IOException;
}