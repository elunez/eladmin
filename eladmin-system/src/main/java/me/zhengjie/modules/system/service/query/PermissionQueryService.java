package me.zhengjie.modules.system.service.query;

import me.zhengjie.modules.system.domain.Permission;
import me.zhengjie.modules.system.repository.PermissionRepository;
import me.zhengjie.modules.system.service.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = "permission")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PermissionQueryService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 不分页
     */
    @Cacheable(key = "'queryAll:'+#p0")
    public List queryAll(String name){
        return permissionMapper.toDto(permissionRepository.findAll(new Spec(name)));
    }

    class Spec implements Specification<Permission> {

        private String name;

        public Spec(String name) {
            this.name = name;
        }

        @Override
        public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(name)){
                /**
                 * 模糊
                 */
                list.add(cb.like(root.get("name").as(String.class),"%"+name+"%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
