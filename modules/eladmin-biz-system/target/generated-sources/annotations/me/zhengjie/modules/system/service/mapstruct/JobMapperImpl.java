package me.zhengjie.modules.system.service.mapstruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import me.zhengjie.modules.system.domain.Job;
import me.zhengjie.modules.system.service.dto.JobDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-25T11:11:17+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_382 (Amazon.com Inc.)"
)
@Component
public class JobMapperImpl implements JobMapper {

    @Override
    public Job toEntity(JobDto dto) {
        if ( dto == null ) {
            return null;
        }

        Job job = new Job();

        job.setCreateBy( dto.getCreateBy() );
        job.setUpdateBy( dto.getUpdateBy() );
        job.setCreateTime( dto.getCreateTime() );
        job.setUpdateTime( dto.getUpdateTime() );
        job.setId( dto.getId() );
        job.setName( dto.getName() );
        if ( dto.getJobSort() != null ) {
            job.setJobSort( dto.getJobSort().longValue() );
        }
        job.setEnabled( dto.getEnabled() );

        return job;
    }

    @Override
    public JobDto toDto(Job entity) {
        if ( entity == null ) {
            return null;
        }

        JobDto jobDto = new JobDto();

        jobDto.setCreateBy( entity.getCreateBy() );
        jobDto.setUpdateBy( entity.getUpdateBy() );
        jobDto.setCreateTime( entity.getCreateTime() );
        jobDto.setUpdateTime( entity.getUpdateTime() );
        jobDto.setId( entity.getId() );
        if ( entity.getJobSort() != null ) {
            jobDto.setJobSort( entity.getJobSort().intValue() );
        }
        jobDto.setName( entity.getName() );
        jobDto.setEnabled( entity.getEnabled() );

        return jobDto;
    }

    @Override
    public List<Job> toEntity(List<JobDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Job> list = new ArrayList<Job>( dtoList.size() );
        for ( JobDto jobDto : dtoList ) {
            list.add( toEntity( jobDto ) );
        }

        return list;
    }

    @Override
    public List<JobDto> toDto(List<Job> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<JobDto> list = new ArrayList<JobDto>( entityList.size() );
        for ( Job job : entityList ) {
            list.add( toDto( job ) );
        }

        return list;
    }
}
