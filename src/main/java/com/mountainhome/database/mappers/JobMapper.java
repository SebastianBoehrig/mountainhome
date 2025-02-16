package com.mountainhome.database.mappers;

import com.mountainhome.database.domain.dto.JobDto;
import com.mountainhome.database.domain.entities.JobEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",  uses = JobInputProductMapper.class)
public interface JobMapper {
    JobDto toJobDto(JobEntity entity);
}
