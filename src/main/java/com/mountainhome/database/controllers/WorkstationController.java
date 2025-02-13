package com.mountainhome.database.controllers;

import com.mountainhome.database.domain.dto.JobDto;
import com.mountainhome.database.domain.entities.JobEntity;
import com.mountainhome.database.mappers.JobMapper;
import com.mountainhome.database.services.WorkstationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class WorkstationController {
    private final WorkstationService workstationService;
    private final JobMapper jobMapper;

    public WorkstationController(WorkstationService workstationService, JobMapper jobMapper) {
        this.workstationService = workstationService;
        this.jobMapper = jobMapper;
    }

    @GetMapping(path = "/workstation")
    public List<String> getWorkstationTypeNames() {
        return workstationService.getWorkstationTypeNames();
    }

    @GetMapping(path="/workstation/{workstation_name}")
    public List<JobDto> getWorkstationJobs(@PathVariable("workstation_name") String name) {
        // execute
        List<JobEntity> jobs = workstationService.getJobsByWorkstation(name);
        // map n return
        jobMapper.toDto(jobs.getFirst());
        return jobs.stream().map((jobMapper::toDto)).toList();
    }

//    @GetMapping(path = "/workstation/{id}/job")
//    public ResponseEntity<WorkstationTypeDto> getJobsByWorkstationId(@PathVariable("id") int id) {
//        Optional<WorkstationTypeEntity> workstationTypeOptional = workstationService.getJobByWorkstationTypeId(id);
//        return workstationTypeOptional.map(workstationTypeEntity -> {
//            WorkstationTypeDto workstationTypeDto = workstationTypeMapper.mapTo(workstationTypeEntity);
//            return new ResponseEntity<>(workstationTypeDto, HttpStatus.OK);
//        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @GetMapping(path = "/workstation/name/{name}/job")
//    public ResponseEntity<WorkstationTypeDto> getJobsByWorkstationName(@PathVariable("name") String name) {
//        Optional<WorkstationTypeEntity> workstationTypeOptional = workstationService.getJobByWorkstationTypeName(name);
//        return workstationTypeOptional.map(workstationTypeEntity -> {
//            WorkstationTypeDto workstationTypeDto = workstationTypeMapper.mapTo(workstationTypeEntity);
//            return new ResponseEntity<>(workstationTypeDto, HttpStatus.OK);
//        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @PostMapping(path = "fortress/{id}/workstation/{workstationId}")
//    public ResponseEntity<FortressDto> createWorkstationByFortressId(@PathVariable("id") int id, @PathVariable("workstationId") Integer workstationTypeId) {
//        FortressEntity fortressEntity = workstationService.createWorkstation(id, workstationTypeId);
//        return new ResponseEntity<>(fortressMapper.mapTo(fortressEntity), HttpStatus.CREATED);
//    }
}