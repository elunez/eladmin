package me.zhengjie.modules.system.domain;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Zheng Jie
 * @date 2019年9月7日 16:16:59
 */
@Entity
@Table(name = "user_avatar")
@Data
@NoArgsConstructor
public class UserAvatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String realName;

    private String path;

    private String size;

    public UserAvatar(UserAvatar userAvatar,String realName, String path, String size) {
        this.id = ObjectUtil.isNotEmpty(userAvatar) ? userAvatar.getId() : null;
        this.realName = realName;
        this.path = path;
        this.size = size;
    }
}
