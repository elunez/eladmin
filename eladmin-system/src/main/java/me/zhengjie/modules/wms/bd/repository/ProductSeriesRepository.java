package me.zhengjie.modules.wms.bd.repository;

import me.zhengjie.modules.wms.bd.domain.ProductSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author huangxingxing
* @date 2020-01-04
*/
public interface ProductSeriesRepository extends JpaRepository<ProductSeries, Long>, JpaSpecificationExecutor {
}