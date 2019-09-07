package me.zhengjie.modules.wms.invoice.request;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.zhengjie.modules.wms.invoice.domain.Invoice;
import me.zhengjie.modules.wms.invoice.domain.InvoiceProduct;

import java.io.Serializable;
import java.util.List;

/**
 * @author 黄星星
 * @date 2019-08-27
 */
@Data
public class CreateInvoiceRequest implements Serializable {

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

    // 物流编号
    private String logisticsCode;

    // 销售发货单号
    private String saleInvoiceCode;

    // 备注
    private String remark;

    // 客户ID
    private Long customerId;

    private List<InvoiceProduct> invoiceProductList;

    public void copy(Invoice source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
