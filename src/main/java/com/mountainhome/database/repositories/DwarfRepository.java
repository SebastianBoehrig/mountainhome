package com.mountainhome.database.repositories;

import com.mountainhome.database.domain.entities.DwarfEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DwarfRepository extends CrudRepository<DwarfEntity, Integer> {
    List<DwarfEntity> findByName(String name);
}
