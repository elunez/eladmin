package me.zhengjie.modules.monitor.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.monitor.domain.Server;
import me.zhengjie.modules.monitor.service.dto.ServerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zhang houying
* @date 2019-11-03
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServerMapper extends BaseMapper<ServerDTO, Server> {

}
