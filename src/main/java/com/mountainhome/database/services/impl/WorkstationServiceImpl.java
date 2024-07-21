package com.mountainhome.database.services.impl;

import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.domain.entities.WorkstationEntity;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;
import com.mountainhome.database.repositories.FortressRepository;
import com.mountainhome.database.repositories.WorkstationRepository;
import com.mountainhome.database.repositories.WorkstationTypeRepository;
import com.mountainhome.database.services.WorkstationService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
                .orElseThrow(() -> new NoSuchElementException("Fortress does not exist, however isExist finds the id"));
        WorkstationTypeEntity workstationType = workstationTypeRepository.findById(workstationTypeId)
                .orElseThrow(() -> new NoSuchElementException("Workstation does not exist, however isExistType finds the id"));
        WorkstationEntity workstation = WorkstationEntity.builder().fortress(fortress).workstationType(workstationType).build();
        workstationRepository.save(workstation);
        return fortress;
    }

    @Override
    public List<WorkstationTypeEntity> getWorkstationTypes() {
        return StreamSupport.stream(workstationTypeRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public boolean isExists(int id) {
        return workstationRepository.existsById(id);
    }

    @Override
    public boolean isExists(int fortress_id, String workstationName) {
        return workstationRepository.existsByFortressIdAndWorkstationTypeName(fortress_id, workstationName);
    }

    @Override
    public boolean isExistsType(int id) {
        return workstationTypeRepository.existsById(id);
    }
}
