package me.zhengjie.modules.system.service.query;

import me.zhengjie.modules.system.domain.Dict;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.system.domain.DictDetail;
import me.zhengjie.modules.system.service.dto.DictDetailDTO;
import me.zhengjie.modules.system.repository.DictDetailRepository;
import me.zhengjie.modules.system.service.mapper.DictDetailMapper;
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
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jie
 * @date 2018-12-03
 */
@Service
@CacheConfig(cacheNames = "dictDetail")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictDetailQueryService {

    @Autowired
    private DictDetailRepository dictDetailRepository;

    @Autowired
    private DictDetailMapper dictDetailMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(DictDetailDTO dictDetail, Pageable pageable){
        Page<DictDetail> page = dictDetailRepository.findAll(new Spec(dictDetail),pageable);
        return PageUtil.toPage(page.map(dictDetailMapper::toDto));
    }

    class Spec implements Specification<DictDetail> {

        private DictDetailDTO dictDetail;

        public Spec(DictDetailDTO dictDetail){
            this.dictDetail = dictDetail;
        }

        @Override
        public Predicate toPredicate(Root<DictDetail> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            Join<Dict,DictDetail> join = root.join("dict",JoinType.LEFT);

            if(!ObjectUtils.isEmpty(dictDetail.getLabel())){
                /**
                * 模糊
                */
                list.add(cb.like(root.get("label").as(String.class),"%"+dictDetail.getLabel()+"%"));
            }

            if(!ObjectUtils.isEmpty(dictDetail.getDictName())){
                list.add(cb.equal(join.get("name").as(String.class),dictDetail.getDictName()));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}