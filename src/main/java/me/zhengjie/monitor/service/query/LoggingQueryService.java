package me.zhengjie.monitor.service.query;

import me.zhengjie.monitor.domain.Logging;
import me.zhengjie.monitor.repository.LoggingRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 2018-11-24
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LoggingQueryService {

    @Autowired
    private LoggingRepository loggingRepository;

    public Page queryAll(Logging logging, Pageable pageable){

        return loggingRepository.findAll(new Spec(logging),pageable);
    }

    public List queryAll(Logging logging){

        return loggingRepository.findAll(new Spec(logging));
    }

    class Spec implements Specification<Logging> {

        private Logging logging;

        public Spec(Logging logging){
            this.logging = logging;
        }

        @Override
        public Predicate toPredicate(Root<Logging> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();


            if(!ObjectUtils.isEmpty(logging.getUsername())){
                list.add(cb.like(root.get("username").as(String.class),"%"+logging.getUsername()+"%"));
            }

            if (!ObjectUtils.isEmpty(logging.getLogType())) {
                list.add(cb.equal(root.get("logType").as(String.class), logging.getLogType()));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
