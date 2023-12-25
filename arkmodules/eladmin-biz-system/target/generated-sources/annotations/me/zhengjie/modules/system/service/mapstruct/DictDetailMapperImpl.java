package me.zhengjie.modules.system.service.mapstruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import me.zhengjie.modules.system.domain.DictDetail;
import me.zhengjie.modules.system.service.dto.DictDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-25T11:11:17+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
@Component
public class DictDetailMapperImpl implements DictDetailMapper {

    @Autowired
    private DictSmallMapper dictSmallMapper;

    @Override
    public DictDetail toEntity(DictDetailDto dto) {
        if ( dto == null ) {
            return null;
        }

        DictDetail dictDetail = new DictDetail();

        dictDetail.setCreateBy( dto.getCreateBy() );
        dictDetail.setUpdateBy( dto.getUpdateBy() );
        dictDetail.setCreateTime( dto.getCreateTime() );
        dictDetail.setUpdateTime( dto.getUpdateTime() );
        dictDetail.setId( dto.getId() );
        dictDetail.setDict( dictSmallMapper.toEntity( dto.getDict() ) );
        dictDetail.setLabel( dto.getLabel() );
        dictDetail.setValue( dto.getValue() );
        dictDetail.setDictSort( dto.getDictSort() );

        return dictDetail;
    }

    @Override
    public DictDetailDto toDto(DictDetail entity) {
        if ( entity == null ) {
            return null;
        }

        DictDetailDto dictDetailDto = new DictDetailDto();

        dictDetailDto.setCreateBy( entity.getCreateBy() );
        dictDetailDto.setUpdateBy( entity.getUpdateBy() );
        dictDetailDto.setCreateTime( entity.getCreateTime() );
        dictDetailDto.setUpdateTime( entity.getUpdateTime() );
        dictDetailDto.setId( entity.getId() );
        dictDetailDto.setDict( dictSmallMapper.toDto( entity.getDict() ) );
        dictDetailDto.setLabel( entity.getLabel() );
        dictDetailDto.setValue( entity.getValue() );
        dictDetailDto.setDictSort( entity.getDictSort() );

        return dictDetailDto;
    }

    @Override
    public List<DictDetail> toEntity(List<DictDetailDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DictDetail> list = new ArrayList<DictDetail>( dtoList.size() );
        for ( DictDetailDto dictDetailDto : dtoList ) {
            list.add( toEntity( dictDetailDto ) );
        }

        return list;
    }

    @Override
    public List<DictDetailDto> toDto(List<DictDetail> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DictDetailDto> list = new ArrayList<DictDetailDto>( entityList.size() );
        for ( DictDetail dictDetail : entityList ) {
            list.add( toDto( dictDetail ) );
        }

        return list;
    }
}
