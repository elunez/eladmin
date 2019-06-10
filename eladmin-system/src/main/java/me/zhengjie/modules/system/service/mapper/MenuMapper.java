package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.modules.system.domain.Menu;
import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.system.service.dto.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {

}
