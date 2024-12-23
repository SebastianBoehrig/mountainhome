package com.mountainhome.database.repositories;

import com.mountainhome.database.domain.entities.WorkstationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkstationRepository extends CrudRepository<WorkstationEntity, Integer> { }
