package com.mountainhome.database.mappers.impl;

import com.mountainhome.database.domain.dto.JobDto;
import com.mountainhome.database.domain.dto.JobInputProductDto;
import com.mountainhome.database.domain.dto.WorkstationTypeDto;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;
import com.mountainhome.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class WorkstationTypeMapper implements Mapper<WorkstationTypeEntity, WorkstationTypeDto> {
    private final ModelMapper modelMapper;

    public WorkstationTypeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public WorkstationTypeDto mapTo(WorkstationTypeEntity workstationTypeEntity) {
        WorkstationTypeDto workstationTypeDto = modelMapper.map(workstationTypeEntity, WorkstationTypeDto.class);
        return sortListsByID(workstationTypeDto);
    }

    @Override
    public WorkstationTypeEntity mapFrom(WorkstationTypeDto workstationTypeDto) {
        return modelMapper.map(workstationTypeDto, WorkstationTypeEntity.class);
    }

    private WorkstationTypeDto sortListsByID(WorkstationTypeDto workstationTypeDto) {
        List<JobDto> jobs = workstationTypeDto.getJobs();
        jobs.sort(Comparator.comparingInt(JobDto::getId));
        jobs.forEach(job -> {
            List<JobInputProductDto> inputs = job.getInputs();
            List<JobInputProductDto> products = job.getProducts();
            inputs.sort(Comparator.comparingInt(JobInputProductDto::getResourceId));
            products.sort(Comparator.comparingInt(JobInputProductDto::getResourceId));
            job.setInputs(inputs);
            job.setProducts(products);
        });
        return workstationTypeDto;
    }
}
