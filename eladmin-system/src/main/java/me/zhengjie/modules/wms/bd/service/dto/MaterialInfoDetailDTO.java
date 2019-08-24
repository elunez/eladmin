package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;
import me.zhengjie.modules.wms.bd.request.MaterialInitialSetup;
import me.zhengjie.modules.wms.bd.request.MaterialInventoryWarning;

import java.io.Serializable;
import java.util.List;


/**
* @author jie
* @date 2019-08-03
*/
@Data
public class MaterialInfoDetailDTO extends ProductInfoDTO implements Serializable {

    private List<MaterialInventoryWarning> materialInventoryWarningList;

    private List<MaterialInitialSetup> materialInitialSetupList;

}