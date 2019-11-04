package ${package}.service.mapper;

import me.zhengjie.base.BaseMapper;
import ${package}.domain.${className};
import ${package}.service.dto.${className}DTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author ${author}
* @date ${date}
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ${className}Mapper extends BaseMapper<${className}DTO, ${className}> {

}