package me.zhengjie.modules.wms.invoice.repository;

import me.zhengjie.modules.wms.invoice.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author jie
* @date 2019-08-27
*/
public interface InvoiceRepository extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor {

    Invoice findBySaleInvoiceCode(String saleInvoiceCode);
}