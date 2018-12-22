package me.zhengjie.system.service.query;

import me.zhengjie.common.utils.PageUtil;
import me.zhengjie.system.domain.Role;
import me.zhengjie.system.repository.RoleRepository;
import me.zhengjie.system.service.dto.RoleDTO;
import me.zhengjie.system.service.mapper.RoleMapper;
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
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "role")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleQueryService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(RoleDTO role, Pageable pageable){
        Page<Role> page = roleRepository.findAll(new Spec(role),pageable);
        return PageUtil.toPage(page.map(roleMapper::toDto));
    }

    /**
     * 不分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(RoleDTO role){
        return roleMapper.toDto(roleRepository.findAll(new Spec(role)));
    }

    class Spec implements Specification<Role> {

        private RoleDTO role;

        public Spec(RoleDTO role){
            this.role = role;
        }

        @Override
        public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(role.getName())){
                /**
                 * 模糊
                 */
                list.add(cb.like(root.get("name").as(String.class),"%"+role.getName()+"%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
