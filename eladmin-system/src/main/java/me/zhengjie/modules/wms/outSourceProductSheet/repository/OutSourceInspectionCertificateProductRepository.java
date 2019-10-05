package me.zhengjie.modules.wms.outSourceProductSheet.repository;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificateProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author jie
* @date 2019-10-01
*/
public interface OutSourceInspectionCertificateProductRepository extends JpaRepository<OutSourceInspectionCertificateProduct, Long>, JpaSpecificationExecutor {

    /**
     * 查询指定委外验收单下所有的产品信息（状态为true）
     * @param outSourceInspectionCertificateId
     * @return
     */
    @Query(value ="select * from s_out_source_inspection_certificate_product where out_source_inspection_certificate_id = ?1 and status =1", nativeQuery = true)
    List<OutSourceInspectionCertificateProduct> queryByOutSourceInspectionCertificateIdAndStatusTrue(Long outSourceInspectionCertificateId);

    @Modifying
    @Query(value = "delete s_out_source_inspection_certificate_product  where product_code = ?1 and out_source_inspection_certificate_id = ?2", nativeQuery = true)
    void deleteByProductCodeAndOutSourceInspectionCertificateId(String productCode, Long outSourceInspectionCertificateId);
}