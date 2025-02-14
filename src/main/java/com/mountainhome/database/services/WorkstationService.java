package com.mountainhome.database.services;

import com.mountainhome.database.domain.entities.JobEntity;
import com.mountainhome.database.domain.entities.WorkstationStoreEntity;

import java.util.List;

public interface WorkstationService {
    List<String> getWorkstationTypeNames();

    List<JobEntity> getJobsByWorkstation(String name);

    List<WorkstationStoreEntity> createOrUpdateWorkstationStoreEntity(String fortressName, String workstationTypeName);
}
