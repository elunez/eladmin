package me.zhengjie.utils.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public interface PredicateStrategy {

    javax.persistence.criteria.Predicate buildPredicate(Expression<?> expression, Object value, CriteriaBuilder cb);

    default boolean needsValue() {
        return true;
    }

    default boolean needsCollectionCheck() {
        return false;
    }

    default boolean needsBetweenCheck() {
        return false;
    }
}
