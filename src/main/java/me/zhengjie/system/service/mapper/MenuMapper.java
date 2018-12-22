package me.zhengjie.system.service.mapper;

import me.zhengjie.common.mapper.EntityMapper;
import me.zhengjie.system.domain.Menu;
import me.zhengjie.system.service.dto.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author jie
 * @date 2018-12-17
 */
@Mapper(componentModel = "spring",uses = {RoleMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {

}
