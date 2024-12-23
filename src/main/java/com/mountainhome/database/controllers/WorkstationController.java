package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.FortressDto;
import com.mountainhome.database.domain.dto.JobDto;
import com.mountainhome.database.domain.dto.JobInputProductDto;
import com.mountainhome.database.domain.dto.WorkstationTypeDto;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;
import com.mountainhome.database.mappers.Mapper;
import com.mountainhome.database.services.WorkstationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
public class WorkstationController {
    private final WorkstationService workstationService;
    private final Mapper<WorkstationTypeEntity, WorkstationTypeDto> workstationTypeMapper;
    private final Mapper<FortressEntity, FortressDto> fortressMapper;

    public WorkstationController(WorkstationService workstationService, Mapper<WorkstationTypeEntity, WorkstationTypeDto> workstationTypeMapper, Mapper<FortressEntity, FortressDto> fortressMapper) {
        this.workstationService = workstationService;
        this.workstationTypeMapper = workstationTypeMapper;
        this.fortressMapper = fortressMapper;
    }

    @GetMapping(path = "/workstation")
    public List<WorkstationTypeDto> getWorkstations() {
        List<WorkstationTypeEntity> workstationTypeEntityList = workstationService.getWorkstationTypes();
        return workstationTypeEntityList.stream().map(workstationTypeMapper::mapTo).toList();
    }

    @GetMapping(path = "/workstation/{id}/job")
    public ResponseEntity<WorkstationTypeDto> getJobsByWorkstationId(@PathVariable("id") int id) {
        Optional<WorkstationTypeEntity> workstationTypeOptional = workstationService.getJobByWorkstationTypeId(id);
        return workstationTypeOptional.map(workstationTypeEntity -> {
            WorkstationTypeDto workstationTypeDto = workstationTypeMapper.mapTo(workstationTypeEntity);
            return new ResponseEntity<>(workstationTypeDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/workstation/name/{name}/job")
    public ResponseEntity<WorkstationTypeDto> getJobsByWorkstationName(@PathVariable("name") String name) {
        Optional<WorkstationTypeEntity> workstationTypeOptional = workstationService.getJobByWorkstationTypeName(name);
        return workstationTypeOptional.map(workstationTypeEntity -> {
            WorkstationTypeDto workstationTypeDto = workstationTypeMapper.mapTo(workstationTypeEntity);
            return new ResponseEntity<>(workstationTypeDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "fortress/{id}/workstation/{workstationId}")
    public ResponseEntity<FortressDto> createWorkstationByFortressId(@PathVariable("id") int id, @PathVariable("workstationId") Integer workstationTypeId) {
        FortressEntity fortressEntity = workstationService.createWorkstation(id, workstationTypeId);
        return new ResponseEntity<>(fortressMapper.mapTo(fortressEntity), HttpStatus.CREATED);
    }
}
