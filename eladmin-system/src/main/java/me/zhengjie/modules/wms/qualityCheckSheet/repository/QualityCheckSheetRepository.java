package me.zhengjie.modules.wms.qualityCheckSheet.repository;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import me.zhengjie.modules.wms.qualityCheckSheet.domain.QualityCheckSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
* @author huangxingxing
* @date 2019-11-12
*/
public interface QualityCheckSheetRepository extends JpaRepository<QualityCheckSheet, Long>, JpaSpecificationExecutor {

    /**
     * 根据质量检验单单据编号查看质量检验单信息
     * @param qualityCheekSheetCode
     * @return
     */
    @Query(value ="select * from quality_check_sheet where quality_cheek_sheet_code = ?1 and status = 1", nativeQuery = true)
    QualityCheckSheet findByQualityCheekSheetCode(String qualityCheekSheetCode);
}