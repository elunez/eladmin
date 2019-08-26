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
public class MaterialInfoDetailDTO extends MaterialInfoDTO implements Serializable {

    private List<MaterialInventoryWarning> materialInventoryWarning;

    private List<MaterialInitialSetup> materialInitialSetup;

}