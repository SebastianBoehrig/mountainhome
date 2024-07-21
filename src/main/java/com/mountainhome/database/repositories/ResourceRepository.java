package com.mountainhome.database.repositories;

import com.mountainhome.database.domain.entities.ResourceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends CrudRepository<ResourceEntity, Integer> {
}
