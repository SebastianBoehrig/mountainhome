package com.mountainhome.database.mappers.impl;

import com.mountainhome.database.domain.dto.WorkstationTypeDto;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;
import com.mountainhome.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WorkstationTypeMapper implements Mapper<WorkstationTypeEntity, WorkstationTypeDto> {
    private final ModelMapper modelMapper;

    public WorkstationTypeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public WorkstationTypeDto mapTo(WorkstationTypeEntity workstationTypeEntity) {
        return modelMapper.map(workstationTypeEntity, WorkstationTypeDto.class);
    }

    @Override
    public WorkstationTypeEntity mapFrom(WorkstationTypeDto workstationTypeDto) {
        return modelMapper.map(workstationTypeDto, WorkstationTypeEntity.class);
    }
}
