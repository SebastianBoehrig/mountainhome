package com.mountainhome.database.repositories;

import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.domain.entities.FortressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DwarfRepository extends CrudRepository<DwarfEntity, Integer> {
    List<DwarfEntity> findAllByFortress(FortressEntity fortress);
}
