package me.zhengjie.modules.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

/**
 * 角色
 * @author Zheng Jie
 * @date 2018-11-22
 */
@Entity
@Table(name = "role")
@Getter
@Setter
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = {Update.class})
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    // 数据权限类型 全部 、 本级 、 自定义
    @Column(name = "data_scope")
    private String dataScope = "本级";

    // 数值越小，级别越大
    @Column(name = "level")
    private Integer level = 3;

    @Column
    private String remark;

    // 权限
    @Column(name = "permission")
    private String permission;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany
    @JoinTable(name = "roles_menus", joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "menu_id",referencedColumnName = "id")})
    private Set<Menu> menus;

    @ManyToMany
    @JoinTable(name = "roles_depts", joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "dept_id",referencedColumnName = "id")})
    private Set<Dept> depts;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    public @interface Update {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
