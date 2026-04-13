package me.zhengjie.utils.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public class FindInSetStrategy implements PredicateStrategy {

    @Override
    public javax.persistence.criteria.Predicate buildPredicate(Expression<?> expression, Object value, CriteriaBuilder cb) {
        return cb.greaterThan(cb.function("FIND_IN_SET", Integer.class,
                cb.literal(value.toString()), expression), 0);
    }
}
