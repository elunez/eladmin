package me.zhengjie.modules.wms.outSourceProductSheet.repository;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author jie
* @date 2019-10-01
*/
public interface OutSourceInspectionCertificateRepository extends JpaRepository<OutSourceInspectionCertificate, Long>, JpaSpecificationExecutor {
}