package me.zhengjie.modules.wms.invoice.repository;

import me.zhengjie.modules.wms.invoice.domain.Invoice;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author jie
* @date 2019-08-27
*/
public interface InvoiceRepository extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor {

    Invoice findBySaleInvoiceCode(String saleInvoiceCode);

    /**
     * 根据客户订单编号查看客户订单
     * @param customerOrderCode
     * @return
     */
    List<Invoice> findByCustomerOrderCodeAndStatusTrue(String customerOrderCode);

    @Modifying
    @Query(value = "update s_invoice set status = 0 where id = ?1", nativeQuery = true)
    void deleteInvoice(long invoiceId);
}