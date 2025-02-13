package com.mountainhome.database.repositories;

import com.mountainhome.database.domain.entities.JobEntity;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends CrudRepository<JobEntity, Integer> {
    List<JobEntity> findAllByWorkstationType(WorkstationTypeEntity workstationType);
}
