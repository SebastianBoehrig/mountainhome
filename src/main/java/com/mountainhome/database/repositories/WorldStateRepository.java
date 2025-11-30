package com.mountainhome.database.repositories;

import com.mountainhome.database.domain.entities.WorldStateEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldStateRepository extends CrudRepository<WorldStateEntity, Integer> {
}
