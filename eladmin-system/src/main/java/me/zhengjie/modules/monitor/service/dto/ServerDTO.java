package me.zhengjie.modules.monitor.service.dto;

import lombok.Data;
import java.io.Serializable;


/**
* @author Zhang houying
* @date 2019-11-03
*/
@Data
public class ServerDTO implements Serializable {

    private Integer id;

    private String name;

    private String address;

    private Integer port;

    private String state;

    /** CPU使用率 */
    private Float cpuRate;

    /** CPU内核数 */
    private Integer cpuCore;

    /** 内存总数 */
    private Float memTotal;

    /** 内存使用量 */
    private Float memUsed;

    /** 磁盘总量 */
    private Float diskTotal;

    /** 磁盘使用量 */
    private Float diskUsed;

    /** 交换区总量 */
    private Float swapTotal;

    /** 交换区使用量 */
    private Float swapUsed;

    private Integer sort;
}
