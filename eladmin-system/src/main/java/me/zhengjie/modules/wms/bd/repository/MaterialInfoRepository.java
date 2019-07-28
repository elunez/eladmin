package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.MaterialInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author 黄星星
* @date 2019-07-27
*/
public interface MaterialInfoRepository extends JpaRepository<MaterialInfo, Long>, JpaSpecificationExecutor {
}