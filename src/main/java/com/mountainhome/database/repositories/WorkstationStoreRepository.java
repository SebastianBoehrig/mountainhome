package com.mountainhome.database.repositories;

import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.domain.entities.WorkstationStoreEntity;
import com.mountainhome.database.domain.entities.WorkstationTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkstationStoreRepository extends CrudRepository<WorkstationStoreEntity, Integer> {
    List<WorkstationStoreEntity> findAllByFortress(FortressEntity fortress);

    Optional<WorkstationStoreEntity>findByFortressAndWorkstationType(FortressEntity fortress, WorkstationTypeEntity workstationType);
}
