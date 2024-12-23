package com.mountainhome.database.services.impl;

import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.domain.entities.WorkstationEntity;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;
import com.mountainhome.database.repositories.FortressRepository;
import com.mountainhome.database.repositories.WorkstationRepository;
import com.mountainhome.database.repositories.WorkstationTypeRepository;
import com.mountainhome.database.services.WorkstationService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Log
@Service
public class WorkstationServiceImpl implements WorkstationService {
    private final WorkstationRepository workstationRepository;
    private final WorkstationTypeRepository workstationTypeRepository;
    private final FortressRepository fortressRepository;

    public WorkstationServiceImpl(WorkstationRepository workstationRepository, WorkstationTypeRepository workstationTypeRepository, FortressRepository fortressRepository) {
        this.workstationRepository = workstationRepository;
        this.workstationTypeRepository = workstationTypeRepository;
        this.fortressRepository = fortressRepository;
    }

    @Override
    public FortressEntity createWorkstation(int id, Integer workstationTypeId) {
        FortressEntity fortress = fortressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fortress does not exist"));
        WorkstationTypeEntity workstationType = workstationTypeRepository.findById(workstationTypeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workstation does not exist"));
        WorkstationEntity workstation = WorkstationEntity.builder().fortress(fortress).workstationType(workstationType).build();
        workstationRepository.save(workstation);
        return fortress;
    }

    @Override
    public List<WorkstationTypeEntity> getWorkstationTypes() {
        List<WorkstationTypeEntity> workstationTypes = new java.util.ArrayList<>(StreamSupport.stream(workstationTypeRepository.findAll().spliterator(), false).toList());
        workstationTypes.forEach(workstationType -> workstationType.setJobs(List.of()));
        workstationTypes.sort(Comparator.comparingInt(WorkstationTypeEntity::getId));
        return workstationTypes;
    }

    @Override
    public Optional<WorkstationTypeEntity> getJobByWorkstationTypeId(int id) {
        return workstationTypeRepository.findById(id);
    }

    @Override
    public Optional<WorkstationTypeEntity> getJobByWorkstationTypeName(String name) {
        Optional<WorkstationTypeEntity> a=workstationTypeRepository.findByName(name);
        return a;
    }
}
