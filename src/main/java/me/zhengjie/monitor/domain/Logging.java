package me.zhengjie.monitor.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author jie
 * @date 2018-11-24
 */
@Entity
@Data
@Table(name = "log")
@NoArgsConstructor
public class Logging {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 描述
     */
    private String description;

    /**
     * 方法名
     */
    private String method;

    /**
     * 参数
     */
    @Column(columnDefinition = "text")
    private String params;

    /**
     * 日志类型
     */
    private String logType;

    /**
     * 请求ip
     */
    private String requestIp;

    /**
     * 请求耗时
     */
    private Long time;

    /**
     * 异常详细
     */
    @Column(columnDefinition = "text")
    private String exceptionDetail;

    /**
     * 创建日期
     */
    @CreationTimestamp
    private Timestamp createTime;

    public Logging(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}
