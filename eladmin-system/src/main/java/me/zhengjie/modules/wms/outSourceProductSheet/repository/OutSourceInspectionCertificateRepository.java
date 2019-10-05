package me.zhengjie.modules.wms.outSourceProductSheet.repository;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificate;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
* @author jie
* @date 2019-10-01
*/
public interface OutSourceInspectionCertificateRepository extends JpaRepository<OutSourceInspectionCertificate, Long>, JpaSpecificationExecutor {
    /**
     * 根据委外验收单单据编号查询委外验收单信息
     * @param outSourceInspectionCertificateCode
     * @return
     */
    @Query(value ="select * from s_out_source_inspection_certificate where out_source_inspection_certificate_code = ?1 and status = 1", nativeQuery = true)
    OutSourceInspectionCertificate findByOutSourceInspectionCertificateCode(String outSourceInspectionCertificateCode);
}