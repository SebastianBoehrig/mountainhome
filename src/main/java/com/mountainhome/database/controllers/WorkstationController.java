package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.FortressDto;
import com.mountainhome.database.domain.dto.WorkstationTypeDto;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;
import com.mountainhome.database.mappers.Mapper;
import com.mountainhome.database.services.FortressService;
import com.mountainhome.database.services.WorkstationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkstationController {
    private final WorkstationService workstationService;
    private final FortressService fortressService;
    private final Mapper<WorkstationTypeEntity, WorkstationTypeDto> workstationTypeMapper;
    private final Mapper<FortressEntity, FortressDto> fortressMapper;

    public WorkstationController(WorkstationService workstationService, FortressService fortressService, Mapper<WorkstationTypeEntity, WorkstationTypeDto> workstationTypeMapper, Mapper<FortressEntity, FortressDto> fortressMapper) {
        this.workstationService = workstationService;
        this.fortressService = fortressService;
        this.workstationTypeMapper = workstationTypeMapper;
        this.fortressMapper = fortressMapper;
    }

    @GetMapping(path = "/workstation")
    public List<WorkstationTypeDto> getWorkstations() {
        List<WorkstationTypeEntity> workstationTypeEntityList = workstationService.getWorkstationTypes();
        return workstationTypeEntityList.stream().map(workstationTypeMapper::mapTo).toList();
    }

    @PostMapping(path = "fortress/{id}/workstation/{workstationId}")
    public ResponseEntity<FortressDto> createWorkstationByFortressId(@PathVariable("id") int id, @PathVariable("workstationId") Integer workstationTypeId) {
        if (!fortressService.isExists(id) && workstationService.isExistsType(workstationTypeId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        FortressEntity fortressEntity = workstationService.createWorkstation(id, workstationTypeId);
        return new ResponseEntity<>(fortressMapper.mapTo(fortressEntity), HttpStatus.CREATED);
    }
}
