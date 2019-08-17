package me.zhengjie.modules.wms.order.request;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.zhengjie.modules.wms.order.service.dto.CustomerOrderProductDTO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
* @author jie
* @date 2019-08-03
*/
@Data
public class UpdateCustomerOrderRequest implements Serializable {

    private Long id;

    // 所属客户主键
    private Long customerId;

    // 客户名称
    private String customerName;

    // 订单日期
    private Timestamp orderDate;

    // 交货日期
    private Timestamp deliveryDate;

    // 客户订单编号
    private String customerOrderCode;

    // 制单人主键
    private Long customerOrderMakerId;

    // 制单人姓名
    private String customerOrderMakerName;

    // 交货方式code
    private String deliveryWayCode;

    // 交货方式名称
    private String deliveryWayName;

    // 付款方式
    private String payWayCode;

    // 付款方式名称
    private String payWayName;

    // 收货地址
    private String deliveryAddress;

    // 收货人
    private String deliveryUser;

    // 收货人联系方式
    private String deliveryUserContact;

    // 备注
    private String remark;

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;

    // 总额
    private Long totalMoney;

    // 总数量
    private Long totalNumber;

    // 状态
    private Boolean status;

    // 订单产品
    private List<CustomerOrderProductDTO>  customerOrderProductList;

    public void copy(UpdateCustomerOrderRequest source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}