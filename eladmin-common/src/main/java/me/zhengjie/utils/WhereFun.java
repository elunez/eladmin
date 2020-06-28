package me.zhengjie.utils;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;

import java.lang.reflect.Field;
import java.util.Objects;

public interface WhereFun {
    WhereFun DEFAULT = new DefaultWhereFun();

    <T> void whereFunc(Wrapper<T> wrapper, Field field, Object value);
}

class DefaultWhereFun implements WhereFun {
    @Override
    public <T> void whereFunc(Wrapper<T> wrapper, Field field, Object value) {
        if (wrapper instanceof AbstractWrapper) {
            if (Objects.nonNull(value)) {
                ((AbstractWrapper) wrapper).eq(true, field.getName(), value);
            }
        } else if (wrapper instanceof AbstractChainWrapper) {
            if (Objects.nonNull(value)) {
                ((AbstractChainWrapper) wrapper).eq(true, field.getName(), value);
            }
        }
    }
}
