package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.IncomeCategory;
import me.zhengjie.modules.wms.bd.domain.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 黄星星
 * @date 2019-07-26
 */
public interface WareHouseRepository extends JpaRepository<WareHouse, Long >, JpaSpecificationExecutor {

}
