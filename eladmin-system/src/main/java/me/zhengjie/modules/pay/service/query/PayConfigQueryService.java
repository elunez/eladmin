package me.zhengjie.modules.pay.service.query;

import me.zhengjie.modules.pay.domain.PayConfig;
import me.zhengjie.modules.pay.repository.PayConfigRepository;
import me.zhengjie.modules.pay.service.dto.PayConfigDTO;
import me.zhengjie.modules.pay.service.mapper.PayConfigMapper;
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

import javax.annotation.Resource;
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
@CacheConfig(cacheNames = "payConfig")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PayConfigQueryService {

    @Autowired
    private PayConfigRepository payConfigRepository;

    @Resource
    private PayConfigMapper payConfigMapper;

    /**
     * 分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(PayConfigDTO payConfig, Pageable pageable) {
        Page<PayConfig> page = payConfigRepository.findAll(new Spec(payConfig), pageable);
        return PageUtil.toPage(page.map(payConfigMapper::toDto));
    }

    /**
     * 不分页
     */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(PayConfigDTO payConfig) {
        return payConfigMapper.toDto(payConfigRepository.findAll(new Spec(payConfig)));
    }

    class Spec implements Specification<PayConfig> {

        private PayConfigDTO payConfig;

        public Spec(PayConfigDTO payConfig) {
            this.payConfig = payConfig;
        }

        @Override
        public Predicate toPredicate(Root<PayConfig> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}