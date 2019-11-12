package me.zhengjie.modules.wms.qualityCheckSheet.repository;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import me.zhengjie.modules.wms.qualityCheckSheet.domain.QualityCheckSheetProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author huangxingxing
* @date 2019-11-12
*/
public interface QualityCheckSheetProductRepository extends JpaRepository<QualityCheckSheetProduct, Long>, JpaSpecificationExecutor {

    /**
     * 查询指定质量检验单下所有的产品信息（状态为true）
     * @param qualityCheckSheetId
     * @return
     */
    @Query(value ="select * from quality_check_sheet_product where quality_check_sheet_id = ?1 and status =1", nativeQuery = true)
    List<QualityCheckSheetProduct> queryByQualityCheckSheetIdAndStatusTrue(Long qualityCheckSheetId);

    @Modifying
    @Query(value = "delete from quality_check_sheet_product  where product_code = ?1 and quality_check_sheet_id = ?2", nativeQuery = true)
    void deleteByProductCodeAndQualityCheckSheetId(String productCode, Long qualityCheckSheetId);
}