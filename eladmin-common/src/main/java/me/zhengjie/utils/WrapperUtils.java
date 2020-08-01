package me.zhengjie.utils;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;
import me.zhengjie.utils.enums.WhereTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class WrapperUtils {
    private static final Logger log = LoggerFactory.getLogger(WrapperUtils.class);

    private static Map<WhereTypeEnum, WhereFun> typeFunc = new ConcurrentHashMap();

    /**
     * 执行
     *
     * @param obj     obj
     * @param wrapper
     */
    public synchronized static <T> Wrapper<T> excute(Object obj, Wrapper<T> wrapper, WhereFun whereFun) {
        //反射获取属性
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                final Object value = field.get(obj);
                if (Objects.nonNull(whereFun)) {
                    whereFun.whereFunc(wrapper, field, value);
                }
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        }
        return wrapper;
    }

}
