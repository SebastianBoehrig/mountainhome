package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.JobDto;
import com.mountainhome.database.domain.dto.WorkstationStoreDto;
import com.mountainhome.database.domain.entities.JobEntity;
import com.mountainhome.database.domain.entities.WorkstationStoreEntity;
import com.mountainhome.database.mappers.JobMapper;
import com.mountainhome.database.mappers.WorkstationStoreMapper;
import com.mountainhome.database.services.WorkstationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class WorkstationController {
    private final WorkstationService workstationService;
    private final JobMapper jobMapper;
    private final WorkstationStoreMapper workstationStoreMapper;

    public WorkstationController(WorkstationService workstationService, JobMapper jobMapper, WorkstationStoreMapper workstationStoreMapper) {
        this.workstationService = workstationService;
        this.jobMapper = jobMapper;
        this.workstationStoreMapper = workstationStoreMapper;
    }

    @GetMapping(path = "/workstation")
    public List<String> getWorkstationTypeNames() {
        return workstationService.getWorkstationTypeNames();
    }

    @GetMapping(path="/workstation/{workstation_name}")
    public List<JobDto> getJobsByWorkstation(@PathVariable("workstation_name") String name) {
        // execute
        List<JobEntity> jobs = workstationService.getJobsByWorkstation(name);
        // map n return
        return jobs.stream().map((jobMapper::toJobDto)).toList();
    }

    @PostMapping(path= "fortress/{fortress_name}/workstation/{workstation_name}")
    public ResponseEntity<List<WorkstationStoreDto>> createOrUpdateWorkstationStoreEntity(@PathVariable("fortress_name") String fortressName, @PathVariable("workstation_name") String workstationTypeName) {
        // execute
        List<WorkstationStoreEntity> workstationStoreEntityList = workstationService.createOrUpdateWorkstationStoreEntity(fortressName, workstationTypeName);
        // map n return
        List<WorkstationStoreDto> workstationStoreDtoList = workstationStoreEntityList.stream().map((workstationStoreMapper::toWorkstationStoreDto)).toList();
        return new ResponseEntity<>(workstationStoreDtoList, HttpStatus.CREATED);
    }
}