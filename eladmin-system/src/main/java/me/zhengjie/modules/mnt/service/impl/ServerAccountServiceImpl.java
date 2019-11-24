package me.zhengjie.modules.mnt.service.impl;

import cn.hutool.core.util.IdUtil;
import me.zhengjie.modules.mnt.domain.ServerAccount;
import me.zhengjie.modules.mnt.repository.ServerAccountRepository;
import me.zhengjie.modules.mnt.repository.ServerDeployRepository;
import me.zhengjie.modules.mnt.service.ServerAccountService;
import me.zhengjie.modules.mnt.service.dto.ServerAccountDTO;
import me.zhengjie.modules.mnt.service.dto.ServerAccountQueryCriteria;
import me.zhengjie.modules.mnt.service.mapper.ServerAccountMapper;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.hibernate.mapping.IdGenerator;
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
public class ServerAccountServiceImpl implements ServerAccountService {

    @Autowired
    private ServerAccountRepository serverAccountRepository;

    @Autowired
    private ServerDeployRepository serverDeployRepository;

    @Autowired
    private ServerAccountMapper serverAccountMapper;

    @Override
    public Object queryAll(ServerAccountQueryCriteria criteria, Pageable pageable){
        Page<ServerAccount> page = serverAccountRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(serverAccountMapper::toDto));
    }

    @Override
    public Object queryAll(ServerAccountQueryCriteria criteria){
        return serverAccountMapper.toDto(serverAccountRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ServerAccountDTO findById(String id) {
        Optional<ServerAccount> ServerAccount = serverAccountRepository.findById(id);
        ValidationUtil.isNull(ServerAccount,"ServerAccount","id",id);
        return serverAccountMapper.toDto(ServerAccount.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerAccountDTO create(ServerAccount resources) {
        resources.setId(IdUtil.getSnowflake(0, 0).toString());
        return serverAccountMapper.toDto(serverAccountRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ServerAccount resources) {
        Optional<ServerAccount> optionalServerAccount = serverAccountRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalServerAccount,"ServerAccount","id",resources.getId());
        ServerAccount ServerAccount = optionalServerAccount.get();
        ServerAccount.copy(resources);
        serverAccountRepository.save(ServerAccount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        serverAccountRepository.deleteById(id);
        serverDeployRepository.changeByAccount(id);
    }
}
