package com.mountainhome.database.repositories;

import com.mountainhome.database.domain.entities.WorkstationTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkstationTypeRepository extends CrudRepository<WorkstationTypeEntity, Integer> {
    Optional<WorkstationTypeEntity> findByName(String name);
}
