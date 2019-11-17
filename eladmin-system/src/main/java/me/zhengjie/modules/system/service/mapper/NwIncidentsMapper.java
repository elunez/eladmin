package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.system.domain.NwIncidents;
import me.zhengjie.modules.system.service.dto.NwIncidentsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Bobby Kimutai
* @date 2019-08-05
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NwIncidentsMapper extends EntityMapper<NwIncidentsDTO, NwIncidents> {

}