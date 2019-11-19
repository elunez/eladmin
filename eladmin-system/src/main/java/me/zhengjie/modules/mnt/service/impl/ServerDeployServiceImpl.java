package me.zhengjie.modules.mnt.service.impl;

import me.zhengjie.modules.mnt.domain.ServerDeploy;
import me.zhengjie.modules.mnt.repository.ServerDeployRepository;
import me.zhengjie.modules.mnt.service.ServerDeployService;
import me.zhengjie.modules.mnt.service.dto.ServerDeployDTO;
import me.zhengjie.modules.mnt.service.dto.ServerDeployQueryCriteria;
import me.zhengjie.modules.mnt.service.mapper.ServerDeployMapper;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
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
public class ServerDeployServiceImpl implements ServerDeployService {

    private ServerDeployRepository serverDeployRepository;

    private ServerDeployMapper serverDeployMapper;

    public ServerDeployServiceImpl(ServerDeployRepository serverDeployRepository,ServerDeployMapper serverDeployMapper){
    	this.serverDeployRepository = serverDeployRepository;
    	this.serverDeployMapper = serverDeployMapper;
	}

    @Override
    public Object queryAll(ServerDeployQueryCriteria criteria, Pageable pageable){
        Page<ServerDeploy> page = serverDeployRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(serverDeployMapper::toDto));
    }

    @Override
    public Object queryAll(ServerDeployQueryCriteria criteria){
        return serverDeployMapper.toDto(serverDeployRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ServerDeployDTO findById(String id) {
        Optional<ServerDeploy> server = serverDeployRepository.findById(id);
        ValidationUtil.isNull(server,"ServerDeploy","id",id);
        return serverDeployMapper.toDto(server.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerDeployDTO create(ServerDeploy resources) {
		return serverDeployMapper.toDto(serverDeployRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ServerDeploy resources) {
        Optional<ServerDeploy> optionalServer = serverDeployRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalServer,"ServerDeploy","id",resources.getId());
        ServerDeploy serverDeploy = optionalServer.get();
		serverDeploy.copy(resources);
        serverDeployRepository.save(serverDeploy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        serverDeployRepository.deleteById(id);
    }
}
