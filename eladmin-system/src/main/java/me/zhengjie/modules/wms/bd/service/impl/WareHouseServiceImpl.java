package me.zhengjie.modules.wms.bd.service.impl;

import me.zhengjie.exception.BadRequestException;
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
import org.springframework.util.CollectionUtils;

import java.util.List;
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
        //验证仓库编码或者仓库名字是否存在
        List<WareHouse> wareHouseList = wareHouseRepository.findByNameOrWareHouseCodeAndStatusTrue(resources.getName(), resources.getWareHouseCode());
        if(!CollectionUtils.isEmpty(wareHouseList)) {
            throw new BadRequestException("仓库编码或编号已经存在");
        }

        WareHouse wareHouseDelete = wareHouseRepository.findByNameAndWareHouseCodeAndStatusFalse(resources.getName(), resources.getWareHouseCode());
        if(null != wareHouseDelete){
            wareHouseDelete.setStatus(true);
            wareHouseRepository.updateStatusTrue(wareHouseDelete.getId());
            return wareHouseMapper.toDto(wareHouseDelete);

        }
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
    public void delete(long id) {
        Optional<WareHouse> wareHouseOptional  = wareHouseRepository.findById(id);
        WareHouse wareHouse = wareHouseOptional.get();
        wareHouse.setStatus(false);
        wareHouseRepository.deleteWareHouse(id);
    }

    @Override
    public Object queryAll(WareHouseDTO wareHouse, Pageable pageable) {
        Page<WareHouse> page = wareHouseRepository.findAll((root, query, cb) -> QueryHelp.getPredicate(root, wareHouse, cb), pageable);
        return PageUtil.toPage(page.map(wareHouseMapper::toDto));
    }

}
