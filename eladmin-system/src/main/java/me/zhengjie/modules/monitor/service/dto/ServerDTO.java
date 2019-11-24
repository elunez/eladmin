package me.zhengjie.modules.monitor.service.dto;

import lombok.Data;
import java.io.Serializable;


/**
* @author Zhang houying
* @date 2019-11-03
*/
@Data
public class ServerDTO implements Serializable {

    // 编号
    private Integer id;

    // 名称
    private String name;

    // IP地址
    private String address;

    // 访问端口
    private Integer port;

    // 状态
    private String state;

    // CPU使用率
    private Float cpuRate;

    // CPU内核数
    private Integer cpuCore;

    // 内存总数
    private Float memTotal;

    // 内存使用量
    private Float memUsed;

    // 磁盘总量
    private Float diskTotal;

    // 磁盘使用量
    private Float diskUsed;

    // 交换区总量
    private Float swapTotal;

    // 交换区使用量
    private Float swapUsed;

    // 排序
    private Integer sort;
}
