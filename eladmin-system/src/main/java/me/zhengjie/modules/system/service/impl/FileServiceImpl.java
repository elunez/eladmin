package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.FileModel;
import me.zhengjie.modules.system.repository.FileRepository;
import me.zhengjie.modules.system.service.FileService;
import me.zhengjie.modules.system.service.dto.FileDTO;
import me.zhengjie.modules.system.service.dto.FileQueryCriteria;
import me.zhengjie.modules.system.service.mapper.FileMapper;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileMapper fileMapper;

    @Override
    public Object queryAll(FileQueryCriteria criteria, Pageable pageable) {
        Page<FileModel> page = fileRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(fileMapper::toDto));
    }

    @Override
    public FileDTO findById(String id) {
        Optional<FileModel> file = fileRepository.findById(id);
        ValidationUtil.isNull(file,"File","id",id);
        return fileMapper.toDto(file.get());
    }

//    @Override
//    public List<FileModel> findByPid(String pid) {
//        return fileRepository.findByPid(pid);
//    }

//    @Override
//    public Set<Dept> findByRoleIds(Long id) {
//        return fileSortRepository.findByRoles_Id(id);
//    }

//    @Override
//    public Object buildTree(List<FileDTO> fileDTOS) {
//        Set<FileDTO> trees = new LinkedHashSet<>();
//        Set<FileSortDTO> fileSorts= new LinkedHashSet<>();
//        List<String> fileNames = fileDTOS.stream().map(FileDTO::getFileName).collect(Collectors.toList());
//        Boolean isChild;
//        for (FileSortDTO fileSortDTO : fileDTOS) {
//            isChild = false;
//            if ("0".equals(fileDTO.getPid().toString())) {
//                trees.add(fileDTO);
//            }
//            for (FileSortDTO it : fileDTOS) {
//                if (it.getPid().equals(fileSortDTO.getId())) {
//                    isChild = true;
//                    if (fileSortDTO.getChildren() == null) {
//                        fileSortDTO.setChildren(new ArrayList<FileSortDTO>());
//                    }
//                    fileSortDTO.getChildren().add(it);
//                }
//            }
//            if(isChild)
//                fileSorts.add(fileSortDTO);
//            else if(!fileSortNames.contains(fileSortRepository.findNameById(fileSortDTO.getPid())))
//                fileSorts.add(fileSortDTO);
//        }
//
//        if (CollectionUtils.isEmpty(trees)) {
//            trees = fileSorts;
//        }
//
//        Integer totalElements = fileSortDTOS!=null?fileSortDTOS.size():0;
//
//        Map map = new HashMap();
//        map.put("totalElements",totalElements);
//        map.put("content",CollectionUtils.isEmpty(trees)?fileSortDTOS:trees);
//        return map;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileDTO create(FileModel resources) {
        return fileMapper.toDto(fileRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FileModel resources) {

        Optional<FileModel> optionalDept = fileRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalDept,"FileModel","id",resources.getId());
        FileModel file = optionalDept.get();
        //文件更新只更新所属目录
        file.setFileSort(resources.getFileSort());
        resources.setId(file.getId());
        fileRepository.save(file);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        fileRepository.deleteById(id);
    }
}