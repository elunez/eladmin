package me.zhengjie.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.service.dto.LocalStorageDto;
import me.zhengjie.domain.LocalStorage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocalStorageMapper extends BaseMapper<LocalStorageDto, LocalStorage> {

}