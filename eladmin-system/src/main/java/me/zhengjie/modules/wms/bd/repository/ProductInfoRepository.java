package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author 黄星星
* @date 2019-07-27
*/
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Integer>, JpaSpecificationExecutor {
}