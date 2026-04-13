package me.zhengjie.utils.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import java.util.Collection;

public class NotInStrategy implements PredicateStrategy {

    @Override
    public javax.persistence.criteria.Predicate buildPredicate(Expression<?> expression, Object value, CriteriaBuilder cb) {
        return expression.in((Collection<Object>) value).not();
    }

    @Override
    public boolean needsCollectionCheck() {
        return true;
    }
}
