package me.zhengjie.modules.wms.qualityCheckSheet.repository;

import me.zhengjie.modules.wms.qualityCheckSheet.domain.QualityCheckSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author huangxingxing
* @date 2019-11-12
*/
public interface QualityCheckSheetRepository extends JpaRepository<QualityCheckSheet, Long>, JpaSpecificationExecutor {
}