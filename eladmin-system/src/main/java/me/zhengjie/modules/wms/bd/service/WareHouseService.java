package me.zhengjie.modules.wms.bd.service;

import me.zhengjie.modules.wms.bd.domain.WareHouse;
import me.zhengjie.modules.wms.bd.service.dto.WareHouseDTO;
import org.springframework.data.domain.Pageable;


/**
 * @author 黄星星
 * @date 2019-07-27
 */
public interface WareHouseService {

    WareHouseDTO create(WareHouse resources);

    WareHouseDTO findById(long id);

    void delete(long id);

    Object queryAll(WareHouseDTO wareHouse, Pageable pageable);

}
