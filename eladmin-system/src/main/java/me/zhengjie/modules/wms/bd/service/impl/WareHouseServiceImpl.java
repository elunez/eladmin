package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.modules.wms.bd.domain.WareHouse;
import me.zhengjie.modules.wms.bd.repository.WareHouseRepository;
import me.zhengjie.modules.wms.bd.service.WareHouseService;
import me.zhengjie.modules.wms.bd.service.dto.WareHouseDTO;
import me.zhengjie.modules.wms.bd.service.mapper.WareHouseMapper;
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
 * @author 黄星星
 * @date 2019-07-27
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WareHouseServiceImpl implements WareHouseService {

    @Autowired
    private WareHouseMapper wareHouseMapper;

    @Autowired
    private WareHouseRepository wareHouseRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WareHouseDTO create(WareHouse resources) {
        return wareHouseMapper.toDto(wareHouseRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WareHouseDTO findById(long id) {
        Optional<WareHouse> wareHouse = wareHouseRepository.findById(id);
        ValidationUtil.isNull(wareHouse,"wareHouse","id",id);
        return wareHouseMapper.toDto(wareHouse.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        wareHouseRepository.deleteById(id);
    }

    @Override
    public Object queryAll(WareHouseDTO wareHouse, Pageable pageable) {
        Page<WareHouse> page = wareHouseRepository.findAll((root, query, cb) -> QueryHelp.getPredicate(root, wareHouse, cb), pageable);
        return PageUtil.toPage(page.map(wareHouseMapper::toDto));
    }

}
