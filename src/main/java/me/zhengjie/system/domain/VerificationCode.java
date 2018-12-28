package me.zhengjie.system.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author jie
 * @date 2018-12-26
 */
@Data
@Entity
@Table(name = "verification_code")
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    /**
     * true 为有效，false 为无效
     */
    private Boolean status = true;


    /**
     * 类型 ：phone 和 email
     */
    private String type;

    /**
     * 具体的phone与email
     */
    private String value;

    /**
     * 创建日期
     */
    @CreationTimestamp
    private Timestamp createTime;
}
