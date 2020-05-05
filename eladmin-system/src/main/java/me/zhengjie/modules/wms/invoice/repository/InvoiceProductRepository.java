package me.zhengjie.modules.wms.invoice.repository;

import me.zhengjie.modules.wms.invoice.domain.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author jie
* @date 2019-08-27
*/
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long>, JpaSpecificationExecutor {

    List<InvoiceProduct> findByInvoiceIdAndStatusTrue(Long invoiceId);

    /**
     * 根据销售发货单编号编号查询所有产品
     * @param invoicCode
     * @return
     */
    List<InvoiceProduct> findByInvoiceCodeAndStatusTrue(String invoicCode);

    /**
     * 根据客户订单编号查询所有销售发货单对应的产品
     * @param customerOrderCode
     * @return
     */
    List<InvoiceProduct> findByCustomerOrderCodeAndStatusTrue(String customerOrderCode);


    @Modifying
    @Query(value = "update s_invoice_product set status = 0 where invoice_id = ?1", nativeQuery = true)
    void deleteInvoiceProduct(long invoiceId);

    /**
     * 根据产品code以及客户订单id删除发货单中对应的产品信息
     * @param productCode
     * @param invoiceId
     */
    @Modifying
    @Query(value = "delete s_invoice_product  where product_code = ?1 and invoice_id = ?2", nativeQuery = true)
    void deleteByProductCodeAndInvoiceId(String productCode, Long invoiceId);

}