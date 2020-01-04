package me.zhengjie.modules.wms.invoice.request;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.zhengjie.modules.wms.invoice.domain.Invoice;
import me.zhengjie.modules.wms.invoice.domain.InvoiceProduct;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceProductDTO;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 黄星星
 * @date 2019-08-27
 */
@Data
public class UpdateInvoiceRequest implements Serializable {
    private Long id;

    private Timestamp createTime;

    private Timestamp updateTime;

    // 客户订单编号
    private String customerOrderCode;

    // 收货地址
    private String deliveryAddress;

    // 收货人
    private String consignee;

    // 联系方式
    private String contactWay;

    // 发票号
    private String invoiceNumber;

    // 物流公司
    private String logisticsCompany;

    // 物流单号
    private String logisticsCode;

    // 销售发货单号
    private String saleInvoiceCode;

    // 状态
    private Boolean status;

    // 备注
    private String remark;

    private Long customerId;

    private String customerName;

    private List<InvoiceProductDTO> invoiceProductList;

    public void copy(Invoice source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
