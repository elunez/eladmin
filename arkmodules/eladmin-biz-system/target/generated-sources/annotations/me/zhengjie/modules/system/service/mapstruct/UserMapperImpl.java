package me.zhengjie.modules.system.service.mapstruct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.Job;
import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.service.dto.DeptSmallDto;
import me.zhengjie.modules.system.service.dto.JobSmallDto;
import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import me.zhengjie.modules.system.service.dto.UserDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-25T11:11:17+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setCreateBy( dto.getCreateBy() );
        user.setUpdateBy( dto.getUpdateBy() );
        user.setCreateTime( dto.getCreateTime() );
        user.setUpdateTime( dto.getUpdateTime() );
        user.setId( dto.getId() );
        user.setRoles( roleSmallDtoSetToRoleSet( dto.getRoles() ) );
        user.setJobs( jobSmallDtoSetToJobSet( dto.getJobs() ) );
        user.setDept( deptSmallDtoToDept( dto.getDept() ) );
        user.setUsername( dto.getUsername() );
        user.setNickName( dto.getNickName() );
        user.setEmail( dto.getEmail() );
        user.setPhone( dto.getPhone() );
        user.setGender( dto.getGender() );
        user.setAvatarName( dto.getAvatarName() );
        user.setAvatarPath( dto.getAvatarPath() );
        user.setPassword( dto.getPassword() );
        user.setEnabled( dto.getEnabled() );
        user.setIsAdmin( dto.getIsAdmin() );
        user.setPwdResetTime( dto.getPwdResetTime() );

        return user;
    }

    @Override
    public UserDto toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setCreateBy( entity.getCreateBy() );
        userDto.setUpdateBy( entity.getUpdateBy() );
        userDto.setCreateTime( entity.getCreateTime() );
        userDto.setUpdateTime( entity.getUpdateTime() );
        userDto.setId( entity.getId() );
        userDto.setRoles( roleSetToRoleSmallDtoSet( entity.getRoles() ) );
        userDto.setJobs( jobSetToJobSmallDtoSet( entity.getJobs() ) );
        userDto.setDept( deptToDeptSmallDto( entity.getDept() ) );
        userDto.setUsername( entity.getUsername() );
        userDto.setNickName( entity.getNickName() );
        userDto.setEmail( entity.getEmail() );
        userDto.setPhone( entity.getPhone() );
        userDto.setGender( entity.getGender() );
        userDto.setAvatarName( entity.getAvatarName() );
        userDto.setAvatarPath( entity.getAvatarPath() );
        userDto.setPassword( entity.getPassword() );
        userDto.setEnabled( entity.getEnabled() );
        userDto.setIsAdmin( entity.getIsAdmin() );
        userDto.setPwdResetTime( entity.getPwdResetTime() );

        return userDto;
    }

    @Override
    public List<User> toEntity(List<UserDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserDto userDto : dtoList ) {
            list.add( toEntity( userDto ) );
        }

        return list;
    }

    @Override
    public List<UserDto> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    protected Role roleSmallDtoToRole(RoleSmallDto roleSmallDto) {
        if ( roleSmallDto == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( roleSmallDto.getId() );
        role.setName( roleSmallDto.getName() );
        role.setDataScope( roleSmallDto.getDataScope() );
        role.setLevel( roleSmallDto.getLevel() );

        return role;
    }

    protected Set<Role> roleSmallDtoSetToRoleSet(Set<RoleSmallDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Role> set1 = new HashSet<Role>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoleSmallDto roleSmallDto : set ) {
            set1.add( roleSmallDtoToRole( roleSmallDto ) );
        }

        return set1;
    }

    protected Job jobSmallDtoToJob(JobSmallDto jobSmallDto) {
        if ( jobSmallDto == null ) {
            return null;
        }

        Job job = new Job();

        job.setId( jobSmallDto.getId() );
        job.setName( jobSmallDto.getName() );

        return job;
    }

    protected Set<Job> jobSmallDtoSetToJobSet(Set<JobSmallDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Job> set1 = new HashSet<Job>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( JobSmallDto jobSmallDto : set ) {
            set1.add( jobSmallDtoToJob( jobSmallDto ) );
        }

        return set1;
    }

    protected Dept deptSmallDtoToDept(DeptSmallDto deptSmallDto) {
        if ( deptSmallDto == null ) {
            return null;
        }

        Dept dept = new Dept();

        dept.setId( deptSmallDto.getId() );
        dept.setName( deptSmallDto.getName() );

        return dept;
    }

    protected RoleSmallDto roleToRoleSmallDto(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleSmallDto roleSmallDto = new RoleSmallDto();

        roleSmallDto.setId( role.getId() );
        roleSmallDto.setName( role.getName() );
        roleSmallDto.setLevel( role.getLevel() );
        roleSmallDto.setDataScope( role.getDataScope() );

        return roleSmallDto;
    }

    protected Set<RoleSmallDto> roleSetToRoleSmallDtoSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleSmallDto> set1 = new HashSet<RoleSmallDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleToRoleSmallDto( role ) );
        }

        return set1;
    }

    protected JobSmallDto jobToJobSmallDto(Job job) {
        if ( job == null ) {
            return null;
        }

        JobSmallDto jobSmallDto = new JobSmallDto();

        jobSmallDto.setId( job.getId() );
        jobSmallDto.setName( job.getName() );

        return jobSmallDto;
    }

    protected Set<JobSmallDto> jobSetToJobSmallDtoSet(Set<Job> set) {
        if ( set == null ) {
            return null;
        }

        Set<JobSmallDto> set1 = new HashSet<JobSmallDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Job job : set ) {
            set1.add( jobToJobSmallDto( job ) );
        }

        return set1;
    }

    protected DeptSmallDto deptToDeptSmallDto(Dept dept) {
        if ( dept == null ) {
            return null;
        }

        DeptSmallDto deptSmallDto = new DeptSmallDto();

        deptSmallDto.setId( dept.getId() );
        deptSmallDto.setName( dept.getName() );

        return deptSmallDto;
    }
}
