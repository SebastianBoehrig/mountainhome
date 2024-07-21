package com.mountainhome.database.services;

import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.domain.entities.WorkstationEntity;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;

import java.util.List;

public interface WorkstationService {
    FortressEntity createWorkstation(int id, Integer workstationId);

    List<WorkstationTypeEntity> getWorkstationTypes();

    boolean isExists(int id);

    boolean isExists(int fortress_id, String workstationTypeName);

    boolean isExistsType(int id);
}
