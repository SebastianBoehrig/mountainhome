package com.mountainhome.database.mappers.impl;

import com.mountainhome.database.domain.dto.WorkstationDto;
import com.mountainhome.database.domain.entities.WorkstationEntity;
import com.mountainhome.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WorkstationMapper implements Mapper<WorkstationEntity, WorkstationDto> {
    private final ModelMapper modelMapper;

    public WorkstationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public WorkstationDto mapTo(WorkstationEntity workstationEntity) {
        return modelMapper.map(workstationEntity, WorkstationDto.class);
    }

    @Override
    public WorkstationEntity mapFrom(WorkstationDto workstationDto) {
        return modelMapper.map(workstationDto, WorkstationEntity.class);
    }
}
