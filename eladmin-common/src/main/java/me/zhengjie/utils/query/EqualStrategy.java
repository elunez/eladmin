package me.zhengjie.utils.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public class EqualStrategy implements PredicateStrategy {

    @Override
    @SuppressWarnings("unchecked")
    public javax.persistence.criteria.Predicate buildPredicate(Expression<?> expression, Object value, CriteriaBuilder cb) {
        return cb.equal(expression.as((Class<? extends Comparable>) value.getClass()), value);
    }
}
