package com.mountainhome.database.repositories;

import com.mountainhome.database.domain.entities.JobEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends CrudRepository<JobEntity, Integer> {
}
