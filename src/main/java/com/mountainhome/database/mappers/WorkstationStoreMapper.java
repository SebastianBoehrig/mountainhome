package com.mountainhome.database.mappers;

import com.mountainhome.database.domain.dto.WorkstationStoreDto;
import com.mountainhome.database.domain.entities.WorkstationStoreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkstationStoreMapper{
    @Mapping(source = "workstationType.name", target = "workstationTypeName")
    WorkstationStoreDto toDto(WorkstationStoreEntity entity);
}
