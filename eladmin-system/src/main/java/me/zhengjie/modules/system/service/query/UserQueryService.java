package me.zhengjie.modules.system.service.query;

import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.modules.system.service.dto.UserDTO;
import me.zhengjie.modules.system.service.mapper.UserMapper;
import me.zhengjie.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jie
 * @date 2018-11-22
 */
@Service
@CacheConfig(cacheNames = "user")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserQueryService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserMapper userMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(UserDTO user, Pageable pageable){
        Page<User> page = userRepo.findAll(new Spec(user),pageable);
        return PageUtil.toPage(page.map(userMapper::toDto));
    }

    /**
     * 不分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(UserDTO user){
        return userMapper.toDto(userRepo.findAll(new Spec(user)));
    }

    class Spec implements Specification<User> {

        private UserDTO user;

        public Spec(UserDTO user){
            this.user = user;
        }

        @Override
        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(user.getId())){
                /**
                 * 相等
                 */
                list.add(cb.equal(root.get("id").as(Long.class),user.getId()));
            }

            if(!ObjectUtils.isEmpty(user.getEnabled())){
                /**
                 * 相等
                 */
                list.add(cb.equal(root.get("enabled").as(Boolean.class),user.getEnabled()));
            }


            if(!ObjectUtils.isEmpty(user.getUsername())){
                /**
                 * 模糊
                 */
                list.add(cb.like(root.get("username").as(String.class),"%"+user.getUsername()+"%"));
            }

            if(!ObjectUtils.isEmpty(user.getEmail())){
                /**
                 * 模糊
                 */
                list.add(cb.like(root.get("email").as(String.class),"%"+user.getEmail()+"%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
