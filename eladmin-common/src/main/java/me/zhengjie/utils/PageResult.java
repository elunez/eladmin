package me.zhengjie.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页查询结果
 * @author wujiawei
 * @see
 * @since 2021/3/17 下午4:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    
    private List<T> content;
    private long totalElements;
}
