package me.zhengjie.modules.mnt.service.impl;

import cn.hutool.core.util.IdUtil;
import me.zhengjie.modules.mnt.domain.DeployHistory;
import me.zhengjie.modules.mnt.repository.DeployHistoryRepository;
import me.zhengjie.modules.mnt.service.DeployHistoryService;
import me.zhengjie.modules.mnt.service.dto.DeployHistoryDto;
import me.zhengjie.modules.mnt.service.dto.DeployHistoryQueryCriteria;
import me.zhengjie.modules.mnt.service.mapper.DeployHistoryMapper;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeployHistoryServiceImpl implements DeployHistoryService {

    private final DeployHistoryRepository deployhistoryRepository;

    private final DeployHistoryMapper deployhistoryMapper;

    public DeployHistoryServiceImpl(DeployHistoryRepository deployhistoryRepository, DeployHistoryMapper deployhistoryMapper) {
        this.deployhistoryRepository = deployhistoryRepository;
        this.deployhistoryMapper = deployhistoryMapper;
    }

    @Override
    public Object queryAll(DeployHistoryQueryCriteria criteria, Pageable pageable){
        Page<DeployHistory> page = deployhistoryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(deployhistoryMapper::toDto));
    }

    @Override
    public List<DeployHistoryDto> queryAll(DeployHistoryQueryCriteria criteria){
        return deployhistoryMapper.toDto(deployhistoryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public DeployHistoryDto findById(String id) {
        DeployHistory deployhistory = deployhistoryRepository.findById(id).orElseGet(DeployHistory::new);
        ValidationUtil.isNull(deployhistory.getId(),"DeployHistory","id",id);
        return deployhistoryMapper.toDto(deployhistory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeployHistoryDto create(DeployHistory resources) {
        resources.setId(IdUtil.simpleUUID());
        return deployhistoryMapper.toDto(deployhistoryRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<String> ids) {
        for (String id : ids) {
            deployhistoryRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<DeployHistoryDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DeployHistoryDto deployHistoryDto : queryAll) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("部署编号", deployHistoryDto.getDeployId());
            map.put("应用名称", deployHistoryDto.getAppName());
            map.put("部署IP", deployHistoryDto.getIp());
            map.put("部署时间", deployHistoryDto.getDeployDate());
            map.put("部署人员", deployHistoryDto.getDeployUser());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
