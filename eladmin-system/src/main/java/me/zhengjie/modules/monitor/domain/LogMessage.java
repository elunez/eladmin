package me.zhengjie.modules.monitor.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author https://cloud.tencent.com/developer/article/1096792
 * @date 2018-12-24
 */
@Data
@AllArgsConstructor
public class LogMessage {

    private String body;
    private String timestamp;
    private String threadName;
    private String className;
    private String level;
}
