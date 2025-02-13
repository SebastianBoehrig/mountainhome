package com.mountainhome.database.services;

import com.mountainhome.database.domain.entities.JobEntity;

import java.util.List;

public interface WorkstationService {
    //createWorkstation(int id, Integer workstationId);

    //List<WorkstationTypeEntity> getWorkstationTypes();

    //Optional<WorkstationTypeEntity> getJobByWorkstationTypeId(int id);

    //Optional<WorkstationTypeEntity> getJobByWorkstationTypeName(String name);

    List<String> getWorkstationTypeNames();

    List<JobEntity> getJobsByWorkstation(String name);
}
