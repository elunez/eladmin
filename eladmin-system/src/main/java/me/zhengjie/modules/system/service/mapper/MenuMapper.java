package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.system.domain.Menu;
import me.zhengjie.modules.system.service.dto.MenuDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 * componentModel不写即为default,需要使用Mappers.getMapper(Class)获取对应Mapper的实例对象
 * componentModel为spring的时候,可使用Spring的自动注入
 * unmappedTargetPolicy在映射方法的目标对象(Target)的属性未填充源值(Source)的情况下应用的默认报告策略，支持的值 :
 * 1> ERROR : 任何未映射的目标属性都将导致映射代码生成失败
 * 2> WARN : 任何未映射的目标属性将在构建时引发警告
 * 3> IGNORE : 未映射的目标属性被忽略
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends BaseMapper<MenuDto, Menu> {

}
