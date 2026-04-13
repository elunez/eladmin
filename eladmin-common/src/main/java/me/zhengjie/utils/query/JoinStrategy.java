package me.zhengjie.utils.query;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

public interface JoinStrategy {

    Join<?, ?> buildJoin(Join<?, ?> existingJoin, Root<?> root, String joinName);
}
