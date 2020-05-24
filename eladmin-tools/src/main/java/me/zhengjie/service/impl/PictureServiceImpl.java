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

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.domain.Picture;
import me.zhengjie.repository.PictureRepository;
import me.zhengjie.service.PictureService;
import me.zhengjie.service.dto.PictureQueryCriteria;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Zheng Jie
 * @date 2018-12-27
 */
@Slf4j
@RequiredArgsConstructor
@Service(value = "pictureService")
public class PictureServiceImpl implements PictureService {

    @Value("${smms.token}")
    private String token;
    private final PictureRepository pictureRepository;
    private static final String SUCCESS = "success";
    private static final String CODE = "code";
    private static final String MSG = "message";

    @Override
    public Object queryAll(PictureQueryCriteria criteria, Pageable pageable){
        return PageUtil.toPage(pictureRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable));
    }

    @Override
    public List<Picture> queryAll(PictureQueryCriteria criteria) {
        return pictureRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Picture upload(MultipartFile multipartFile, String username) {
        File file = FileUtil.toFile(multipartFile);
        // 验证是否重复上传
        Picture picture = pictureRepository.findByMd5Code(FileUtil.getMd5(file));
        if(picture != null){
           return picture;
        }
        HashMap<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("smfile", file);
        // 上传文件
        String result= HttpRequest.post(ElAdminConstant.Url.SM_MS_URL + "/v2/upload")
                .header("Authorization", token)
                .form(paramMap)
                .timeout(20000)
                .execute().body();
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if(!jsonObject.get(CODE).toString().equals(SUCCESS)){
            throw new BadRequestException(TranslatorUtil.translate(jsonObject.get(MSG).toString()));
        }
        picture = JSON.parseObject(jsonObject.get("data").toString(), Picture.class);
        picture.setSize(FileUtil.getSize(Integer.parseInt(picture.getSize())));
        picture.setUsername(username);
        picture.setMd5Code(FileUtil.getMd5(file));
        picture.setFilename(FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename())+"."+FileUtil.getExtensionName(multipartFile.getOriginalFilename()));
        pictureRepository.save(picture);
        //删除临时文件
        FileUtil.del(file);
        return picture;

    }

    @Override
    public Picture findById(Long id) {
        Picture picture = pictureRepository.findById(id).orElseGet(Picture::new);
        ValidationUtil.isNull(picture.getId(),"Picture","id",id);
        return picture;
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            Picture picture = findById(id);
            try {
                HttpUtil.get(picture.getDelete());
                pictureRepository.delete(picture);
            } catch(Exception e){
                pictureRepository.delete(picture);
            }
        }
    }

    @Override
    public void synchronize() {
        //链式构建请求
        String result = HttpRequest.get(ElAdminConstant.Url.SM_MS_URL + "/v2/upload_history")
                //头信息，多个头信息多次调用此方法即可
                .header("Authorization", token)
                .timeout(20000)
                .execute().body();
        JSONObject jsonObject = JSONUtil.parseObj(result);
        List<Picture> pictures = JSON.parseArray(jsonObject.get("data").toString(), Picture.class);
        for (Picture picture : pictures) {
            if(!pictureRepository.existsByUrl(picture.getUrl())){
                picture.setSize(FileUtil.getSize(Integer.parseInt(picture.getSize())));
                picture.setUsername("System Sync");
                picture.setMd5Code(null);
                pictureRepository.save(picture);
            }
        }
    }

    @Override
    public void download(List<Picture> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Picture picture : queryAll) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("文件名", picture.getFilename());
            map.put("图片地址", picture.getUrl());
            map.put("文件大小", picture.getSize());
            map.put("操作人", picture.getUsername());
            map.put("高度", picture.getHeight());
            map.put("宽度", picture.getWidth());
            map.put("删除地址", picture.getDelete());
            map.put("创建日期", picture.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
