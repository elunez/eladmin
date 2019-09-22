package me.zhengjie.modules.wms.outSourceProductSheet.repository;

import me.zhengjie.modules.wms.invoice.domain.InvoiceProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author jie
* @date 2019-08-17
*/
public interface OutSourceProcessSheetProductRepository extends JpaRepository<OutSourceProcessSheetProduct, Long>, JpaSpecificationExecutor {

    /**
     * 查询指定委外加工单下所有的产品信息（状态为true）
     * @param outSourceProcessSheetId
     * @return
     */
    @Query(value ="select * from s_out_source_process_sheet_product where out_source_process_sheet_id = ?1 and status =1", nativeQuery = true)
    List<OutSourceProcessSheetProduct> queryByOutSourceProcessSheetIdAndStatusTrue(Long outSourceProcessSheetId);

    List<OutSourceProcessSheetProduct> findByOutSourceProcessSheetIdAndStatusTrue(Long invoiceId);

    @Modifying
    @Query(value = "delete s_out_source_process_sheet_product  where product_code = ?1 and out_source_process_sheet_id = ?2", nativeQuery = true)
    void deleteByProductCodeAndOutSourceProcessSheetId(String productCode, Long outSourceProcessSheetId);
}