package me.zhengjie.modules.wms.outSourceProductSheet.request;

import lombok.Data;

/**
 * @author 黄星星
 * @date 2019-10-01
 * 查询委外加工单产品信息
 */
@Data
public class QueryOutSourceProcessSheetProductRequest {

    // 所属委外加工单
    private Long outSourceProcessSheetId;
}
