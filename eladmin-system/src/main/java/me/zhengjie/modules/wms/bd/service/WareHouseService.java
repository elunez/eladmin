package me.zhengjie.modules.wms.bd.service;

import me.zhengjie.modules.wms.bd.domain.WareHouse;
import me.zhengjie.modules.wms.bd.service.dto.WareHouseDTO;
import me.zhengjie.modules.wms.bd.service.dto.WareHouseQueryCriteria;
import org.springframework.data.domain.Pageable;


/**
 * @author 黄星星
 * @date 2019-07-27
 */
public interface WareHouseService {

    WareHouseDTO create(WareHouse resources);

    WareHouseDTO findById(long id);

    void delete(long id);

    Object queryAll(WareHouseQueryCriteria wareHouseQueryCriteria, Pageable pageable);

    Object queryAll(WareHouseQueryCriteria wareHouseQueryCriteria);

}
