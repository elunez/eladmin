package me.zhengjie.modules.system.service.impl;

import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.FileSort;
import me.zhengjie.modules.system.repository.FileSortRepository;
import me.zhengjie.modules.system.service.FileSortService;
import me.zhengjie.modules.system.service.dto.FileSortDTO;
import me.zhengjie.modules.system.service.dto.FileSortQueryCriteria;
import me.zhengjie.modules.system.service.mapper.FileSortMapper;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FileSortServiceImpl implements FileSortService {

    @Autowired
    private FileSortRepository fileSortRepository;

    @Autowired
    private FileSortMapper fileSortMapper;

    @Override
    public List<FileSortDTO> queryAll(FileSortQueryCriteria criteria) {
        return fileSortMapper.toDto(fileSortRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public FileSortDTO findById(String id) {
        Optional<FileSort> fileSort = fileSortRepository.findById(id);
        ValidationUtil.isNull(fileSort,"FileSort","id",id);
        return fileSortMapper.toDto(fileSort.get());
    }

    @Override
    public List<FileSort> findByPid(String pid) {
        return fileSortRepository.findByPid(pid);
    }

//    @Override
//    public Set<Dept> findByRoleIds(Long id) {
//        return fileSortRepository.findByRoles_Id(id);
//    }

    @Override
    public Object buildTree(List<FileSortDTO> fileSortDTOS) {
        Set<FileSortDTO> trees = new LinkedHashSet<>();
        Set<FileSortDTO> fileSorts= new LinkedHashSet<>();
        List<String> fileSortNames = fileSortDTOS.stream().map(FileSortDTO::getName).collect(Collectors.toList());
        Boolean isChild;
        for (FileSortDTO fileSortDTO : fileSortDTOS) {
            isChild = false;
            if ("0".equals(fileSortDTO.getPid().toString())) {
                trees.add(fileSortDTO);
            }
            for (FileSortDTO it : fileSortDTOS) {
                if (it.getPid().equals(fileSortDTO.getId())) {
                    isChild = true;
                    if (fileSortDTO.getChildren() == null) {
                        fileSortDTO.setChildren(new ArrayList<FileSortDTO>());
                    }
                    fileSortDTO.getChildren().add(it);
                }
            }
            if(isChild)
                fileSorts.add(fileSortDTO);
            else if(!fileSortNames.contains(fileSortRepository.findNameById(fileSortDTO.getPid())))
                fileSorts.add(fileSortDTO);
        }

        if (CollectionUtils.isEmpty(trees)) {
            trees = fileSorts;
        }

        Integer totalElements = fileSortDTOS!=null?fileSortDTOS.size():0;

        Map map = new HashMap();
        map.put("totalElements",totalElements);
        map.put("content",CollectionUtils.isEmpty(trees)?fileSortDTOS:trees);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileSortDTO create(FileSort resources) {
        return fileSortMapper.toDto(fileSortRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FileSort resources) {
        if(resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        Optional<FileSort> optionalDept = fileSortRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalDept,"FileSort","id",resources.getId());
        FileSort fileSort = optionalDept.get();
        resources.setId(fileSort.getId());
        fileSortRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        fileSortRepository.deleteById(id);
    }
}