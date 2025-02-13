package com.mountainhome.database.mappers;

import com.mountainhome.database.domain.dto.JobInputProductDto;
import com.mountainhome.database.domain.entities.JobInputEntity;
import com.mountainhome.database.domain.entities.JobProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobInputProductMapper {
    @Mapping(source = "resource.name", target = "resourceName")
    JobInputProductDto toJobInputProductDto(JobInputEntity jobInputEntity); //TODO: maybe use generics if its worth it

    @Mapping(source = "resource.name", target = "resourceName")
    JobInputProductDto toJobInputProductDto(JobProductEntity jobProductEntity);
}
