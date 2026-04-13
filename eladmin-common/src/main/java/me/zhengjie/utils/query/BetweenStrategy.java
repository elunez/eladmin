package me.zhengjie.utils.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import java.util.List;

public class BetweenStrategy implements PredicateStrategy {

    @Override
    @SuppressWarnings("unchecked")
    public javax.persistence.criteria.Predicate buildPredicate(Expression<?> expression, Object value, CriteriaBuilder cb) {
        List<Object> between = (List<Object>) value;
        return cb.between(expression.as((Class<? extends Comparable>) between.get(0).getClass()),
                (Comparable) between.get(0), (Comparable) between.get(1));
    }

    @Override
    public boolean needsBetweenCheck() {
        return true;
    }
}
