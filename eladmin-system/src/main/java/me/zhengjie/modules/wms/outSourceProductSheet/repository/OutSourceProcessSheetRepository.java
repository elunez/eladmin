package me.zhengjie.modules.wms.outSourceProductSheet.repository;

import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
* @author jie
* @date 2019-08-17
*/
public interface OutSourceProcessSheetRepository extends JpaRepository<OutSourceProcessSheet, Long>, JpaSpecificationExecutor {

    /**
     * 根据委外加工单单据编号查询委外加工单信息
     * @param outSourceProcessSheetCode
     * @return
     */
    @Query(value ="select * from s_out_source_process_sheet where out_source_process_sheet_code = ?1 and status = 1", nativeQuery = true)
    OutSourceProcessSheet findByOutSourceProcessSheetCode(String outSourceProcessSheetCode);

    @Modifying
    @Query(value = "update s_out_source_process_sheet set proc_status = ?1 where out_source_process_sheet_code = ?2", nativeQuery = true)
    void updateProcStatus(String procStatus, String outSourceProcessSheetCode);
}