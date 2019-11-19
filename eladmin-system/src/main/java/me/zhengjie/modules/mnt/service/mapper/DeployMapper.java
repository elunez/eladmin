package me.zhengjie.modules.mnt.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.mnt.domain.Deploy;
import me.zhengjie.modules.mnt.service.dto.DeployDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeployMapper extends BaseMapper<DeployDTO, Deploy> {

}
