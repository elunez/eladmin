package me.zhengjie.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.domain.LocalStorage;
import me.zhengjie.service.dto.LocalStorageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocalStorageMapper extends EntityMapper<LocalStorageDTO, LocalStorage> {

}