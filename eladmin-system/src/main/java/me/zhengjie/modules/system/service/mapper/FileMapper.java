package me.zhengjie.modules.system.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.system.domain.FileModel;
import me.zhengjie.modules.system.service.dto.FileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Mapper(componentModel = "spring",uses = {FileSortMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileMapper extends EntityMapper<FileDTO, FileModel> {

}