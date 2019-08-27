package me.zhengjie.modules.wms.invoice.repository;

import me.zhengjie.modules.wms.invoice.domain.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* @author jie
* @date 2019-08-27
*/
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long>, JpaSpecificationExecutor {

    List<InvoiceProduct> findByInvoiceIdAndStatusTrue(Long invoiceId);
}