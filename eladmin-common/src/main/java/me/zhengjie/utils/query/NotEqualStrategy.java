package me.zhengjie.utils.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public class NotEqualStrategy implements PredicateStrategy {

    @Override
    public javax.persistence.criteria.Predicate buildPredicate(Expression<?> expression, Object value, CriteriaBuilder cb) {
        return cb.notEqual(expression, value);
    }
}
