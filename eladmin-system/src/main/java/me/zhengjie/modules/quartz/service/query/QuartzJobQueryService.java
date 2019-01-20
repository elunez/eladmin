package me.zhengjie.modules.quartz.service.query;

import me.zhengjie.modules.quartz.domain.QuartzJob;
import me.zhengjie.modules.quartz.repository.QuartzJobRepository;
import me.zhengjie.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
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
 * @date 2019-01-07
 */
@Service
@CacheConfig(cacheNames = "quartzJob")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QuartzJobQueryService {

    @Autowired
    private QuartzJobRepository quartzJobRepository;

    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(QuartzJob quartzJob, Pageable pageable){
        return PageUtil.toPage(quartzJobRepository.findAll(new Spec(quartzJob),pageable));
    }

    class Spec implements Specification<QuartzJob> {

        private QuartzJob quartzJob;

        public Spec(QuartzJob quartzJob){
            this.quartzJob = quartzJob;
        }

        @Override
        public Predicate toPredicate(Root<QuartzJob> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if(!ObjectUtils.isEmpty(quartzJob.getJobName())){

                /**
                 * 模糊
                 */
                list.add(cb.like(root.get("jobName").as(String.class),"%"+quartzJob.getJobName()+"%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
