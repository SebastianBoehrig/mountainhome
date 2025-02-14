package com.mountainhome.database.services.impl;

import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.domain.entities.JobEntity;
import com.mountainhome.database.domain.entities.WorkstationStoreEntity;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;
import com.mountainhome.database.repositories.FortressRepository;
import com.mountainhome.database.repositories.JobRepository;
import com.mountainhome.database.repositories.WorkstationStoreRepository;
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
    private final FortressRepository fortressRepository;
    private final WorkstationStoreRepository workstationStoreRepository;

    public NewWorkstationServiceImpl(WorkstationTypeRepository workstationTypeRepository, JobRepository jobRepository, FortressRepository fortressRepository, WorkstationStoreRepository workstationStoreRepository) {
        this.workstationTypeRepository = workstationTypeRepository;
        this.jobRepository = jobRepository;
        this.fortressRepository = fortressRepository;
        this.workstationStoreRepository = workstationStoreRepository;
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
            log.error("Got a getJobsByWorkstation request with invalid workstationType: {}", name);
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This type of Workstation doesn't exist!");
        });
        return jobRepository.findAllByWorkstationType(workstationType);
    }

    @Override
    public List<WorkstationStoreEntity> createOrUpdateWorkstationStoreEntity(String fortressName, String workstationTypeName) {
        FortressEntity fortress = fortressRepository.findByName(fortressName).orElseThrow(() -> {
            log.error("Got a createOrUpdateWorkstationStoreEntity request with invalid Fortress: {}", fortressName);
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This fortress doesn't exist!");
        });
        WorkstationTypeEntity workstationType = workstationTypeRepository.findByName(workstationTypeName).orElseThrow(() -> {
            log.error("Got a getJobsByWorkstation request with invalid workstationType: {}", workstationTypeName);
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This type of Workstation doesn't exist!");
        });

        WorkstationStoreEntity workstationStoreEntity = workstationStoreRepository.findByFortressAndWorkstationType(fortress, workstationType)
                .orElse(WorkstationStoreEntity.builder().fortress(fortress).workstationType(workstationType).amount(0).build());
        workstationStoreEntity.setAmount(workstationStoreEntity.getAmount() + 1);
        workstationStoreRepository.save(workstationStoreEntity);

        return workstationStoreRepository.findAllByFortress(fortress);
    }
}
