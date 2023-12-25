package me.zhengjie.modules.system.service.mapstruct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.Menu;
import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.modules.system.service.dto.DeptDto;
import me.zhengjie.modules.system.service.dto.MenuDto;
import me.zhengjie.modules.system.service.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-25T11:11:17+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public Role toEntity(RoleDto dto) {
        if ( dto == null ) {
            return null;
        }

        Role role = new Role();

        role.setCreateBy( dto.getCreateBy() );
        role.setUpdateBy( dto.getUpdateBy() );
        role.setCreateTime( dto.getCreateTime() );
        role.setUpdateTime( dto.getUpdateTime() );
        role.setId( dto.getId() );
        role.setMenus( menuDtoSetToMenuSet( dto.getMenus() ) );
        role.setDepts( deptDtoSetToDeptSet( dto.getDepts() ) );
        role.setName( dto.getName() );
        role.setDataScope( dto.getDataScope() );
        role.setLevel( dto.getLevel() );
        role.setDescription( dto.getDescription() );

        return role;
    }

    @Override
    public RoleDto toDto(Role entity) {
        if ( entity == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setCreateBy( entity.getCreateBy() );
        roleDto.setUpdateBy( entity.getUpdateBy() );
        roleDto.setCreateTime( entity.getCreateTime() );
        roleDto.setUpdateTime( entity.getUpdateTime() );
        roleDto.setId( entity.getId() );
        roleDto.setMenus( menuSetToMenuDtoSet( entity.getMenus() ) );
        roleDto.setDepts( deptSetToDeptDtoSet( entity.getDepts() ) );
        roleDto.setName( entity.getName() );
        roleDto.setDataScope( entity.getDataScope() );
        roleDto.setLevel( entity.getLevel() );
        roleDto.setDescription( entity.getDescription() );

        return roleDto;
    }

    @Override
    public List<Role> toEntity(List<RoleDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Role> list = new ArrayList<Role>( dtoList.size() );
        for ( RoleDto roleDto : dtoList ) {
            list.add( toEntity( roleDto ) );
        }

        return list;
    }

    @Override
    public List<RoleDto> toDto(List<Role> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RoleDto> list = new ArrayList<RoleDto>( entityList.size() );
        for ( Role role : entityList ) {
            list.add( toDto( role ) );
        }

        return list;
    }

    protected Set<Menu> menuDtoSetToMenuSet(Set<MenuDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Menu> set1 = new HashSet<Menu>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( MenuDto menuDto : set ) {
            set1.add( menuMapper.toEntity( menuDto ) );
        }

        return set1;
    }

    protected Set<Dept> deptDtoSetToDeptSet(Set<DeptDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Dept> set1 = new HashSet<Dept>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( DeptDto deptDto : set ) {
            set1.add( deptMapper.toEntity( deptDto ) );
        }

        return set1;
    }

    protected Set<MenuDto> menuSetToMenuDtoSet(Set<Menu> set) {
        if ( set == null ) {
            return null;
        }

        Set<MenuDto> set1 = new HashSet<MenuDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Menu menu : set ) {
            set1.add( menuMapper.toDto( menu ) );
        }

        return set1;
    }

    protected Set<DeptDto> deptSetToDeptDtoSet(Set<Dept> set) {
        if ( set == null ) {
            return null;
        }

        Set<DeptDto> set1 = new HashSet<DeptDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Dept dept : set ) {
            set1.add( deptMapper.toDto( dept ) );
        }

        return set1;
    }
}
