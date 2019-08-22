package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;
import me.zhengjie.modules.wms.bd.request.ProductInitialSetup;
import me.zhengjie.modules.wms.bd.request.ProductInventoryWarning;
import me.zhengjie.modules.wms.bd.request.SupplierAddress;
import me.zhengjie.modules.wms.bd.request.SupplierContact;

import java.io.Serializable;
import java.util.List;


/**
* @author jie
* @date 2019-08-03
*/
@Data
public class ProductInfoDetailDTO extends ProductInfoDTO implements Serializable {

    private List<ProductInventoryWarning> productInventoryWarningList;

    private List<ProductInitialSetup> productInitialSetupList;

}