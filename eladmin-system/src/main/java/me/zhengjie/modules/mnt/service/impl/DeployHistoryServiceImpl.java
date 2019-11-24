package me.zhengjie.modules.mnt.service.impl;

import cn.hutool.core.util.IdUtil;
import me.zhengjie.modules.mnt.domain.DeployHistory;
import me.zhengjie.modules.mnt.repository.DeployHistoryRepository;
import me.zhengjie.modules.mnt.service.DeployHistoryService;
import me.zhengjie.modules.mnt.service.dto.DeployHistoryDTO;
import me.zhengjie.modules.mnt.service.dto.DeployHistoryQueryCriteria;
import me.zhengjie.modules.mnt.service.mapper.DeployHistoryMapper;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeployHistoryServiceImpl implements DeployHistoryService {

    @Autowired
    private DeployHistoryRepository deployhistoryRepository;

    @Autowired
    private DeployHistoryMapper deployhistoryMapper;

    @Override
    public Object queryAll(DeployHistoryQueryCriteria criteria, Pageable pageable){
        Page<DeployHistory> page = deployhistoryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(deployhistoryMapper::toDto));
    }

    @Override
    public Object queryAll(DeployHistoryQueryCriteria criteria){
        return deployhistoryMapper.toDto(deployhistoryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public DeployHistoryDTO findById(String id) {
        Optional<DeployHistory> deployhistory = deployhistoryRepository.findById(id);
        ValidationUtil.isNull(deployhistory,"DeployHistory","id",id);
        return deployhistoryMapper.toDto(deployhistory.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeployHistoryDTO create(DeployHistory resources) {
        resources.setId(IdUtil.simpleUUID());
        return deployhistoryMapper.toDto(deployhistoryRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        deployhistoryRepository.deleteById(id);
    }
}
