package me.zhengjie.modules.mnt.service.impl;

import me.zhengjie.modules.mnt.domain.ServerDeploy;
import me.zhengjie.modules.mnt.repository.ServerDeployRepository;
import me.zhengjie.modules.mnt.service.ServerDeployService;
import me.zhengjie.modules.mnt.service.dto.ServerDeployDto;
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
    public ServerDeployDto findById(Long id) {
        ServerDeploy server = serverDeployRepository.findById(id).orElseGet(ServerDeploy::new);
        ValidationUtil.isNull(server.getId(),"ServerDeploy","id",id);
        return serverDeployMapper.toDto(server);
    }

    @Override
    public ServerDeployDto findByIp(String ip) {
        ServerDeploy deploy = serverDeployRepository.findByIp(ip);
        return serverDeployMapper.toDto(deploy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerDeployDto create(ServerDeploy resources) {
		return serverDeployMapper.toDto(serverDeployRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ServerDeploy resources) {
        ServerDeploy serverDeploy = serverDeployRepository.findById(resources.getId()).orElseGet(ServerDeploy::new);
        ValidationUtil.isNull( serverDeploy.getId(),"ServerDeploy","id",resources.getId());
		serverDeploy.copy(resources);
        serverDeployRepository.save(serverDeploy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        serverDeployRepository.deleteById(id);
    }
}
