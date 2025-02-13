package com.mountainhome.database.services.impl;

import com.mountainhome.database.domain.entities.JobEntity;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;
import com.mountainhome.database.repositories.JobRepository;
import com.mountainhome.database.repositories.WorkstationTypeRepository;
import com.mountainhome.database.services.WorkstationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NewWorkstationServiceImpl implements WorkstationService {
    private final WorkstationTypeRepository workstationTypeRepository;
    private final JobRepository jobRepository;

    public NewWorkstationServiceImpl(WorkstationTypeRepository workstationTypeRepository, JobRepository jobRepository) {
        this.workstationTypeRepository = workstationTypeRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public List<String> getWorkstationTypeNames() {
        Iterable<WorkstationTypeEntity> workstationTypeIterable = workstationTypeRepository.findAll();
        List<String> workstationTypeNames = new ArrayList<>();
        workstationTypeIterable.forEach((workstationType -> workstationTypeNames.add(workstationType.getName())));
        return workstationTypeNames;
    }

    @Override
    public List<JobEntity> getJobsByWorkstation(String name) {
        WorkstationTypeEntity workstationType = workstationTypeRepository.findByName(name).orElseThrow(() -> {
            log.error("Got a getWorkstation request with invalid workstationType: {}", name);
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This workstationType doesn't exist!");
        });
        return jobRepository.findAllByWorkstationType(workstationType);
    }
}
