package me.zhengjie.system.service;

import me.zhengjie.core.security.JwtUser;
import me.zhengjie.system.domain.User;
import me.zhengjie.system.service.dto.UserDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author jie
 * @date 2018-11-23
 */
@CacheConfig(cacheNames = "user")
public interface UserService {

    /**
     * get
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    UserDTO findById(long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    UserDTO create(User resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(User resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * findByName
     * @param userName
     * @return
     */
    @Cacheable(key = "'findByName'+#p0")
    User findByName(String userName);

    /**
     * 修改密码
     * @param jwtUser
     * @param encryptPassword
     */
    void updatePass(JwtUser jwtUser, String encryptPassword);

    /**
     * 修改头像
     * @param jwtUser
     * @param url
     */
    void updateAvatar(JwtUser jwtUser, String url);

    /**
     * 修改邮箱
     * @param jwtUser
     * @param email
     */
    void updateEmail(JwtUser jwtUser, String email);
}
