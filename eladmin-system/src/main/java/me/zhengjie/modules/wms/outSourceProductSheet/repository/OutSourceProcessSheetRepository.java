package me.zhengjie.modules.wms.outSourceProductSheet.repository;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author jie
* @date 2019-08-17
*/
public interface OutSourceProcessSheetRepository extends JpaRepository<OutSourceProcessSheet, Long>, JpaSpecificationExecutor {
}