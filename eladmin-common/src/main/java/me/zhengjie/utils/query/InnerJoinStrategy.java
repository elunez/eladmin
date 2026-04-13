package me.zhengjie.utils.query;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

public class InnerJoinStrategy implements JoinStrategy {

    @Override
    public Join<?, ?> buildJoin(Join<?, ?> existingJoin, Root<?> root, String joinName) {
        if (existingJoin != null) {
            return existingJoin.join(joinName, JoinType.INNER);
        } else {
            return root.join(joinName, JoinType.INNER);
        }
    }
}
