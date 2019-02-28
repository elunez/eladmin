package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.Date;

/**
 * @author jie
 * @date 2018-11-22
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {

    /**
     * findByUsername
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * findByEmail
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 修改密码
     * @param id
     * @param pass
     */
    @Modifying
    @Query(value = "update user set password = ?2 , last_password_reset_time = ?3 where id = ?1",nativeQuery = true)
    void updatePass(Long id, String pass, Date lastPasswordResetTime);

    /**
     * 修改头像
     * @param id
     * @param url
     */
    @Modifying
    @Query(value = "update user set avatar = ?2 where id = ?1",nativeQuery = true)
    void updateAvatar(Long id, String url);

    /**
     * 修改邮箱
     * @param id
     * @param email
     */
    @Modifying
    @Query(value = "update user set email = ?2 where id = ?1",nativeQuery = true)
    void updateEmail(Long id, String email);
}
