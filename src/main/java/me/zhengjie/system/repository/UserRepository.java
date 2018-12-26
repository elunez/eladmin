package me.zhengjie.system.repository;

import me.zhengjie.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Query("from User u join fetch u.roles where u.username = :username")
    User findByUsername(@Param("username") String username);

    /**
     * findByEmail
     * @param email
     * @return
     */
    @Query("from User u join fetch u.roles where u.email = :email")
    User findByEmail(@Param("email") String email);
}
