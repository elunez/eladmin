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

    Object queryAll(PictureQueryCriteria criteria, Pageable pageable);
    
    List<Picture> queryAll(PictureQueryCriteria criteria);

    Picture upload(MultipartFile file, String username);

    Picture findById(Long id);

    void delete(Picture picture);

    void deleteAll(Long[] ids);

    void download(List<Picture> queryAll, HttpServletResponse response) throws IOException;
}
