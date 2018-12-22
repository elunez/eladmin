package me.zhengjie.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * 角色
 * @author jie
 * @date 2018-11-22
 */
@Entity
@Table(name = "role")
@Getter
@Setter
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column
    private String remark;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany
    @JoinTable(name = "roles_permissions", joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id",referencedColumnName = "id")})
    private Set<Permission> permissions;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<Menu> menus;

    @Column
    @CreationTimestamp
    private Timestamp createTime;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", createDateTime=" + createTime +
                '}';
    }
}
