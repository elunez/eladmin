package me.zhengjie.modules.system.service.mapstruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import me.zhengjie.modules.system.domain.Dict;
import me.zhengjie.modules.system.domain.DictDetail;
import me.zhengjie.modules.system.service.dto.DictDetailDto;
import me.zhengjie.modules.system.service.dto.DictDto;
import me.zhengjie.modules.system.service.dto.DictSmallDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-14T20:08:48+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
@Component
public class DictMapperImpl implements DictMapper {

    @Override
    public Dict toEntity(DictDto dto) {
        if ( dto == null ) {
            return null;
        }

        Dict dict = new Dict();

        dict.setCreateBy( dto.getCreateBy() );
        dict.setUpdateBy( dto.getUpdateBy() );
        dict.setCreateTime( dto.getCreateTime() );
        dict.setUpdateTime( dto.getUpdateTime() );
        dict.setId( dto.getId() );
        dict.setDictDetails( dictDetailDtoListToDictDetailList( dto.getDictDetails() ) );
        dict.setName( dto.getName() );
        dict.setDescription( dto.getDescription() );

        return dict;
    }

    @Override
    public DictDto toDto(Dict entity) {
        if ( entity == null ) {
            return null;
        }

        DictDto dictDto = new DictDto();

        dictDto.setCreateBy( entity.getCreateBy() );
        dictDto.setUpdateBy( entity.getUpdateBy() );
        dictDto.setCreateTime( entity.getCreateTime() );
        dictDto.setUpdateTime( entity.getUpdateTime() );
        dictDto.setId( entity.getId() );
        dictDto.setDictDetails( dictDetailListToDictDetailDtoList( entity.getDictDetails() ) );
        dictDto.setName( entity.getName() );
        dictDto.setDescription( entity.getDescription() );

        return dictDto;
    }

    @Override
    public List<Dict> toEntity(List<DictDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Dict> list = new ArrayList<Dict>( dtoList.size() );
        for ( DictDto dictDto : dtoList ) {
            list.add( toEntity( dictDto ) );
        }

        return list;
    }

    @Override
    public List<DictDto> toDto(List<Dict> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DictDto> list = new ArrayList<DictDto>( entityList.size() );
        for ( Dict dict : entityList ) {
            list.add( toDto( dict ) );
        }

        return list;
    }

    protected Dict dictSmallDtoToDict(DictSmallDto dictSmallDto) {
        if ( dictSmallDto == null ) {
            return null;
        }

        Dict dict = new Dict();

        dict.setId( dictSmallDto.getId() );

        return dict;
    }

    protected DictDetail dictDetailDtoToDictDetail(DictDetailDto dictDetailDto) {
        if ( dictDetailDto == null ) {
            return null;
        }

        DictDetail dictDetail = new DictDetail();

        dictDetail.setCreateBy( dictDetailDto.getCreateBy() );
        dictDetail.setUpdateBy( dictDetailDto.getUpdateBy() );
        dictDetail.setCreateTime( dictDetailDto.getCreateTime() );
        dictDetail.setUpdateTime( dictDetailDto.getUpdateTime() );
        dictDetail.setId( dictDetailDto.getId() );
        dictDetail.setDict( dictSmallDtoToDict( dictDetailDto.getDict() ) );
        dictDetail.setLabel( dictDetailDto.getLabel() );
        dictDetail.setValue( dictDetailDto.getValue() );
        dictDetail.setDictSort( dictDetailDto.getDictSort() );

        return dictDetail;
    }

    protected List<DictDetail> dictDetailDtoListToDictDetailList(List<DictDetailDto> list) {
        if ( list == null ) {
            return null;
        }

        List<DictDetail> list1 = new ArrayList<DictDetail>( list.size() );
        for ( DictDetailDto dictDetailDto : list ) {
            list1.add( dictDetailDtoToDictDetail( dictDetailDto ) );
        }

        return list1;
    }

    protected DictSmallDto dictToDictSmallDto(Dict dict) {
        if ( dict == null ) {
            return null;
        }

        DictSmallDto dictSmallDto = new DictSmallDto();

        dictSmallDto.setId( dict.getId() );

        return dictSmallDto;
    }

    protected DictDetailDto dictDetailToDictDetailDto(DictDetail dictDetail) {
        if ( dictDetail == null ) {
            return null;
        }

        DictDetailDto dictDetailDto = new DictDetailDto();

        dictDetailDto.setCreateBy( dictDetail.getCreateBy() );
        dictDetailDto.setUpdateBy( dictDetail.getUpdateBy() );
        dictDetailDto.setCreateTime( dictDetail.getCreateTime() );
        dictDetailDto.setUpdateTime( dictDetail.getUpdateTime() );
        dictDetailDto.setId( dictDetail.getId() );
        dictDetailDto.setDict( dictToDictSmallDto( dictDetail.getDict() ) );
        dictDetailDto.setLabel( dictDetail.getLabel() );
        dictDetailDto.setValue( dictDetail.getValue() );
        dictDetailDto.setDictSort( dictDetail.getDictSort() );

        return dictDetailDto;
    }

    protected List<DictDetailDto> dictDetailListToDictDetailDtoList(List<DictDetail> list) {
        if ( list == null ) {
            return null;
        }

        List<DictDetailDto> list1 = new ArrayList<DictDetailDto>( list.size() );
        for ( DictDetail dictDetail : list ) {
            list1.add( dictDetailToDictDetailDto( dictDetail ) );
        }

        return list1;
    }
}
