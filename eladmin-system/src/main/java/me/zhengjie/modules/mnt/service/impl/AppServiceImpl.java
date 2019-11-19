package me.zhengjie.modules.mnt.service.impl;

import cn.hutool.core.util.IdUtil;
import me.zhengjie.modules.mnt.domain.App;
import me.zhengjie.modules.mnt.repository.AppRepository;
import me.zhengjie.modules.mnt.service.AppService;
import me.zhengjie.modules.mnt.service.dto.AppDTO;
import me.zhengjie.modules.mnt.service.dto.AppQueryCriteria;
import me.zhengjie.modules.mnt.service.mapper.AppMapper;
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
public class AppServiceImpl implements AppService {

    private AppRepository appRepository;

    private AppMapper appMapper;

	public AppServiceImpl(AppRepository appRepository, AppMapper appMapper) {
		this.appMapper = appMapper;
		this.appRepository = appRepository;
	}

    @Override
    public Object queryAll(AppQueryCriteria criteria, Pageable pageable){
        Page<App> page = appRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(appMapper::toDto));
    }

    @Override
    public Object queryAll(AppQueryCriteria criteria){
        return appMapper.toDto(appRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public AppDTO findById(String id) {
		Optional<App> app = appRepository.findById(id);
        ValidationUtil.isNull(app,"App","id",id);
        return appMapper.toDto(app.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AppDTO create(App resources) {
        resources.setId(IdUtil.fastUUID());
        return appMapper.toDto(appRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(App resources) {
        Optional<App> optionalApp = appRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalApp,"App","id",resources.getId());
        App App = optionalApp.get();
        App.copy(resources);
        appRepository.save(App);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        appRepository.deleteById(id);
    }
}
