package me.zhengjie.monitor.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * pv 与 ip 统计
 *
 * @author jie
 * @date 2018-12-13
 */
@Entity
@Data
@Table(name = "visits")
public class Visits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String date;

    @Column(name = "pv_counts")
    private Long pvCounts;

    @Column(name = "ip_counts")
    private Long ipCounts;

    @CreationTimestamp
    private Timestamp createTime;

    private String weekDay;
}
