package me.zhengjie.service;

import me.zhengjie.domain.Picture;
import me.zhengjie.service.dto.PictureQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zheng Jie
 * @date 2018-12-27
 */
public interface PictureService {

    Object queryAll(PictureQueryCriteria criteria, Pageable pageable);

    Picture upload(MultipartFile file, String username);

    Picture findById(Long id);

    void delete(Picture picture);

    void deleteAll(Long[] ids);
}
