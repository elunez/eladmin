package me.zhengjie.service.impl;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.domain.Picture;
import me.zhengjie.repository.PictureRepository;
import me.zhengjie.service.PictureService;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * @author jie
 * @date 2018-12-27
 */
@Slf4j
@Service(value = "pictureService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    public static final String SUCCESS = "success";

    public static final String CODE = "code";

    public static final String MSG = "msg";

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.static.path}")
    private String staticPath;
    @Value("${server.url}")
    private String serverUrl;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Picture upload(MultipartFile multipartFile, String username) {


        //File file = FileUtil.toFile(multipartFile);
        String fileServerPath = FileUtil.toFile(multipartFile, serverUrl + staticPath, uploadPath);

//        HashMap<String, Object> paramMap = new HashMap<>();

//        paramMap.put("smfile", file);
//        String result = HttpUtil.post(ElAdminConstant.Url.SM_MS_URL, paramMap);

        Picture picture = new Picture();
//        JSONObject jsonObject = JSONUtil.parseObj(result);
//        if (!jsonObject.get(CODE).toString().equals(SUCCESS)) {
//            throw new BadRequestException(jsonObject.get(MSG).toString());
//        }
//        //转成实体类
//        picture = JSON.parseObject(jsonObject.get("data").toString(), Picture.class);
        picture.setUrl(fileServerPath);
        picture.setSize(FileUtil.getSize(Long.valueOf(multipartFile.getSize()).intValue()));
        picture.setUsername(username);
        picture.setFilename(FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename()) + "." + FileUtil.getExtensionName(multipartFile.getOriginalFilename()));
        pictureRepository.save(picture);
        //删除临时文件
//        FileUtil.deleteFile(file);
        return picture;

    }

    @Override
    public Picture findById(Long id) {
        Optional<Picture> picture = pictureRepository.findById(id);
        ValidationUtil.isNull(picture, "Picture", "id", id);
        return picture.get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Picture picture) {
        try {
            String result = HttpUtil.get(picture.getDelete());
            pictureRepository.delete(picture);
        } catch (Exception e) {
            pictureRepository.delete(picture);
        }

    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            delete(findById(id));
        }
    }
}
