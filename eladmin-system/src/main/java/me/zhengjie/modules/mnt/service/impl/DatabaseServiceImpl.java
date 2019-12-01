package me.zhengjie.modules.mnt.service.impl;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.mnt.domain.Database;
import me.zhengjie.modules.mnt.repository.DatabaseRepository;
import me.zhengjie.modules.mnt.service.DatabaseService;
import me.zhengjie.modules.mnt.service.dto.DatabaseDto;
import me.zhengjie.modules.mnt.service.dto.DatabaseQueryCriteria;
import me.zhengjie.modules.mnt.service.mapper.DatabaseMapper;
import me.zhengjie.modules.mnt.util.SqlUtils;
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
@Slf4j
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DatabaseServiceImpl implements DatabaseService {

    private DatabaseRepository databaseRepository;

    private DatabaseMapper databaseMapper;

    public DatabaseServiceImpl(DatabaseRepository databaseRepository,DatabaseMapper databaseMapper){
    	this.databaseMapper = databaseMapper;
    	this.databaseRepository = databaseRepository;
	}

    @Override
    public Object queryAll(DatabaseQueryCriteria criteria, Pageable pageable){
        Page<Database> page = databaseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(databaseMapper::toDto));
    }

    @Override
    public Object queryAll(DatabaseQueryCriteria criteria){
        return databaseMapper.toDto(databaseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public DatabaseDto findById(String id) {
        Database database = databaseRepository.findById(id).orElseGet(Database::new);
        ValidationUtil.isNull(database.getId(),"Database","id",id);
        return databaseMapper.toDto(database);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DatabaseDto create(Database resources) {
        resources.setId(IdUtil.simpleUUID());
        return databaseMapper.toDto(databaseRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Database resources) {
        Database database = databaseRepository.findById(resources.getId()).orElseGet(Database::new);
        ValidationUtil.isNull(database.getId(),"Database","id",resources.getId());
        database.copy(resources);
        databaseRepository.save(database);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        databaseRepository.deleteById(id);
    }

	@Override
	public boolean testConnection(Database resources) {
		try {
			return SqlUtils.testConnection(resources.getJdbcUrl(), resources.getUserName(), resources.getPwd());
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}

	}
}
