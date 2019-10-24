package me.zhengjie.service;

import me.zhengjie.domain.LocalStorage;
import me.zhengjie.service.dto.LocalStorageDTO;
import me.zhengjie.service.dto.LocalStorageQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
public interface LocalStorageService {

    Object queryAll(LocalStorageQueryCriteria criteria, Pageable pageable);

    Object queryAll(LocalStorageQueryCriteria criteria);

    LocalStorageDTO findById(Long id);

    LocalStorageDTO create(String name, MultipartFile file);

    void update(LocalStorage resources);

    void delete(Long id);

    void deleteAll(Long[] ids);
}