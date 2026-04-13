package me.zhengjie.utils.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public class RightLikeStrategy implements PredicateStrategy {

    @Override
    public javax.persistence.criteria.Predicate buildPredicate(Expression<?> expression, Object value, CriteriaBuilder cb) {
        return cb.like(expression.as(String.class), value.toString() + "%");
    }
}
