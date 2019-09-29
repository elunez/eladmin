package me.zhengjie.service.impl;

import me.zhengjie.domain.LocalStorage;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.utils.*;
import me.zhengjie.repository.LocalStorageRepository;
import me.zhengjie.service.LocalStorageService;
import me.zhengjie.service.dto.LocalStorageDTO;
import me.zhengjie.service.dto.LocalStorageQueryCriteria;
import me.zhengjie.service.mapper.LocalStorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LocalStorageServiceImpl implements LocalStorageService {

    @Autowired
    private LocalStorageRepository localStorageRepository;

    @Autowired
    private LocalStorageMapper localStorageMapper;

    @Value("${file.path}")
    private String path;

    @Value("${file.maxSize}")
    private long maxSize;

    @Override
    public Object queryAll(LocalStorageQueryCriteria criteria, Pageable pageable){
        Page<LocalStorage> page = localStorageRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(localStorageMapper::toDto));
    }

    @Override
    public Object queryAll(LocalStorageQueryCriteria criteria){
        return localStorageMapper.toDto(localStorageRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public LocalStorageDTO findById(Long id) {
        Optional<LocalStorage> localStorage = localStorageRepository.findById(id);
        ValidationUtil.isNull(localStorage,"LocalStorage","id",id);
        return localStorageMapper.toDto(localStorage.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LocalStorageDTO create(String name, MultipartFile multipartFile) {
        FileUtil.checkSize(maxSize, multipartFile.getSize());
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        // 可自行选择方式
//        String type = FileUtil.getFileTypeByMimeType(suffix);
        String type = FileUtil.getFileType(suffix);
        File file = FileUtil.upload(multipartFile, path + type +  File.separator);
        try {
            name = StringUtils.isBlank(name) ? FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename()) : name;
            LocalStorage localStorage = new LocalStorage(
                    file.getName(),
                    name,
                    suffix,
                    file.getPath(),
                    type,
                    FileUtil.getSize(multipartFile.getSize()),
                    SecurityUtils.getUsername()
            );
            return localStorageMapper.toDto(localStorageRepository.save(localStorage));
        }catch (Exception e){
            FileUtil.del(file);
            throw e;
        }
    }

    public static void main(String[] args) {
        File file = new File("C:\\Users\\Jie\\Pictures\\Saved Pictures\\demo1.jpg");
        System.out.println(FileUtil.getType(file));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LocalStorage resources) {
        Optional<LocalStorage> optionalLocalStorage = localStorageRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalLocalStorage,"LocalStorage","id",resources.getId());
        LocalStorage localStorage = optionalLocalStorage.get();
        localStorage.copy(resources);
        localStorageRepository.save(localStorage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        LocalStorage storage = localStorageRepository.findById(id).get();
        FileUtil.del(storage.getPath());
        localStorageRepository.delete(storage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            LocalStorage storage = localStorageRepository.findById(id).get();
            FileUtil.del(storage.getPath());
            localStorageRepository.delete(storage);
        }
    }
}
