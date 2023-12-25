package me.zhengjie.modules.system.service.mapstruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import me.zhengjie.modules.system.domain.Menu;
import me.zhengjie.modules.system.service.dto.MenuDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-25T11:11:17+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
@Component
public class MenuMapperImpl implements MenuMapper {

    @Override
    public Menu toEntity(MenuDto dto) {
        if ( dto == null ) {
            return null;
        }

        Menu menu = new Menu();

        menu.setCreateBy( dto.getCreateBy() );
        menu.setUpdateBy( dto.getUpdateBy() );
        menu.setCreateTime( dto.getCreateTime() );
        menu.setUpdateTime( dto.getUpdateTime() );
        menu.setId( dto.getId() );
        menu.setTitle( dto.getTitle() );
        menu.setComponentName( dto.getComponentName() );
        menu.setMenuSort( dto.getMenuSort() );
        menu.setComponent( dto.getComponent() );
        menu.setPath( dto.getPath() );
        menu.setType( dto.getType() );
        menu.setPermission( dto.getPermission() );
        menu.setIcon( dto.getIcon() );
        menu.setCache( dto.getCache() );
        menu.setHidden( dto.getHidden() );
        menu.setPid( dto.getPid() );
        menu.setSubCount( dto.getSubCount() );
        menu.setIFrame( dto.getIFrame() );

        return menu;
    }

    @Override
    public MenuDto toDto(Menu entity) {
        if ( entity == null ) {
            return null;
        }

        MenuDto menuDto = new MenuDto();

        menuDto.setCreateBy( entity.getCreateBy() );
        menuDto.setUpdateBy( entity.getUpdateBy() );
        menuDto.setCreateTime( entity.getCreateTime() );
        menuDto.setUpdateTime( entity.getUpdateTime() );
        menuDto.setId( entity.getId() );
        menuDto.setType( entity.getType() );
        menuDto.setPermission( entity.getPermission() );
        menuDto.setTitle( entity.getTitle() );
        menuDto.setMenuSort( entity.getMenuSort() );
        menuDto.setPath( entity.getPath() );
        menuDto.setComponent( entity.getComponent() );
        menuDto.setPid( entity.getPid() );
        menuDto.setSubCount( entity.getSubCount() );
        menuDto.setIFrame( entity.getIFrame() );
        menuDto.setCache( entity.getCache() );
        menuDto.setHidden( entity.getHidden() );
        menuDto.setComponentName( entity.getComponentName() );
        menuDto.setIcon( entity.getIcon() );

        return menuDto;
    }

    @Override
    public List<Menu> toEntity(List<MenuDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Menu> list = new ArrayList<Menu>( dtoList.size() );
        for ( MenuDto menuDto : dtoList ) {
            list.add( toEntity( menuDto ) );
        }

        return list;
    }

    @Override
    public List<MenuDto> toDto(List<Menu> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MenuDto> list = new ArrayList<MenuDto>( entityList.size() );
        for ( Menu menu : entityList ) {
            list.add( toDto( menu ) );
        }

        return list;
    }
}
