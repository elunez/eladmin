package me.zhengjie.modules.system.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
@Entity
@Getter
@Setter
@Table(name="job")
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    @Column(name = "name",nullable = false)
    @NotBlank
    private String name;

    @Column(unique = true)
    @NotNull
    private Long sort;

    @Column(name = "enabled",nullable = false)
    @NotNull
    private Boolean enabled;

    @OneToOne
    @JoinColumn(name = "dept_id")
    private Dept dept;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    public @interface Update {}
}