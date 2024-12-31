package com.mountainhome.database.repositories;

import com.mountainhome.database.domain.entities.FortressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FortressRepository extends CrudRepository<FortressEntity, Integer> {
    Optional<FortressEntity> findByName(String name);
}
