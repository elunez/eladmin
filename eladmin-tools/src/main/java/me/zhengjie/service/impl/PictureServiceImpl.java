package me.zhengjie.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.domain.Picture;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.repository.PictureRepository;
import me.zhengjie.service.PictureService;
import me.zhengjie.service.dto.PictureQueryCriteria;
import me.zhengjie.utils.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
@Service(value = "pictureService")
@CacheConfig(cacheNames = "picture")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    private static final String SUCCESS = "success";

    private static final String CODE = "code";

    private static final String MSG = "message";

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    @Cacheable
    public Object queryAll(PictureQueryCriteria criteria, Pageable pageable){
        return PageUtil.toPage(pictureRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable));
    }

    @Override
    public List<Picture> queryAll(PictureQueryCriteria criteria) {
        return pictureRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
    }

    @Override
    @CacheEvict(allEntries = true)
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
        String result= HttpUtil.post(ElAdminConstant.Url.SM_MS_URL, paramMap);
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
    @Cacheable(key = "#p0")
    public Picture findById(Long id) {
        Picture picture = pictureRepository.findById(id).orElseGet(Picture::new);
        ValidationUtil.isNull(picture.getId(),"Picture","id",id);
        return picture;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Picture picture) {
        try {
            HttpUtil.get(picture.getDelete());
            pictureRepository.delete(picture);
        } catch(Exception e){
            pictureRepository.delete(picture);
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            delete(findById(id));
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
