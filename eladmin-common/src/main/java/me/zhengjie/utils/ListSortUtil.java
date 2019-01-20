package me.zhengjie.utils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * List按照指定字段排序工具类
 * @author jie
 * @date 2018/08/23 20:03:12
 * @param <T>
 */
public class ListSortUtil<T> {

    /**
     * @param targetList 目标排序List
     * @param sortField 排序字段(实体类属性名)
     * @param sortMode 排序方式（asc or  desc）
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void sort(List<T> targetList, final String sortField, final String sortMode) {

        Collections.sort(targetList, new Comparator() {

            @Override
            public int compare(Object obj1, Object obj2) {
                int retVal = 0;
                try {
                    //首字母转大写
                    String newStr=sortField.substring(0, 1).toUpperCase()+sortField.replaceFirst("\\w","");
                    String methodStr="get"+newStr;

                    Method method1 = ((T)obj1).getClass().getMethod(methodStr, new Class[0]);
                    Method method2 = ((T)obj2).getClass().getMethod(methodStr, new Class[0]);
                    if (sortMode != null && "desc".equals(sortMode)) {
                        // 倒序
                        retVal = method2.invoke(((T) obj2), new Object[0]).toString().compareTo(method1.invoke(((T) obj1), new Object[0]).toString());
                    } else {
                        // 正序
                        retVal = method1.invoke(((T) obj1), new Object[0]).toString().compareTo(method2.invoke(((T) obj2), new Object[0]).toString());
                    }
                } catch (Exception e) {
                    throw new RuntimeException();
                }
                return retVal;
            }
        });
    }

}