package me.zhengjie.utils.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public class GreaterThanStrategy implements PredicateStrategy {

    @Override
    @SuppressWarnings("unchecked")
    public javax.persistence.criteria.Predicate buildPredicate(Expression<?> expression, Object value, CriteriaBuilder cb) {
        return cb.greaterThanOrEqualTo(expression.as((Class<? extends Comparable>) value.getClass()), (Comparable) value);
    }
}
