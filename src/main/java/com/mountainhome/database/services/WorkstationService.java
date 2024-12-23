package com.mountainhome.database.services;

import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;

import java.util.List;
import java.util.Optional;

public interface WorkstationService {
    FortressEntity createWorkstation(int id, Integer workstationId);

    List<WorkstationTypeEntity> getWorkstationTypes();

    Optional<WorkstationTypeEntity> getJobByWorkstationTypeId(int id);

    Optional<WorkstationTypeEntity> getJobByWorkstationTypeName(String name);
}
