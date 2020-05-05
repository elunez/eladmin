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
package me.zhengjie.service;

import me.zhengjie.domain.Picture;
import me.zhengjie.service.dto.PictureQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-27
 */
public interface PictureService {

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(PictureQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     * @param criteria 条件
     * @return /
     */
    List<Picture> queryAll(PictureQueryCriteria criteria);

    /**
     * 上传文件
     * @param file /
     * @param username /
     * @return /
     */
    Picture upload(MultipartFile file, String username);

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    Picture findById(Long id);

    /**
     * 多选删除
     * @param ids /
     */
    void deleteAll(Long[] ids);

    /**
     * 导出
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<Picture> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 同步数据
     */
    void synchronize();
}
