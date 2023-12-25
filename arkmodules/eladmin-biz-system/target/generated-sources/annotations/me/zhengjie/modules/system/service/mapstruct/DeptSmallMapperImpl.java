package me.zhengjie.modules.system.service.mapstruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.service.dto.DeptSmallDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-25T11:11:17+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
@Component
public class DeptSmallMapperImpl implements DeptSmallMapper {

    @Override
    public Dept toEntity(DeptSmallDto dto) {
        if ( dto == null ) {
            return null;
        }

        Dept dept = new Dept();

        dept.setId( dto.getId() );
        dept.setName( dto.getName() );

        return dept;
    }

    @Override
    public DeptSmallDto toDto(Dept entity) {
        if ( entity == null ) {
            return null;
        }

        DeptSmallDto deptSmallDto = new DeptSmallDto();

        deptSmallDto.setId( entity.getId() );
        deptSmallDto.setName( entity.getName() );

        return deptSmallDto;
    }

    @Override
    public List<Dept> toEntity(List<DeptSmallDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Dept> list = new ArrayList<Dept>( dtoList.size() );
        for ( DeptSmallDto deptSmallDto : dtoList ) {
            list.add( toEntity( deptSmallDto ) );
        }

        return list;
    }

    @Override
    public List<DeptSmallDto> toDto(List<Dept> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DeptSmallDto> list = new ArrayList<DeptSmallDto>( entityList.size() );
        for ( Dept dept : entityList ) {
            list.add( toDto( dept ) );
        }

        return list;
    }
}
