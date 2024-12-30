package com.mountainhome.database.services;

import com.mountainhome.database.domain.entities.FortressEntity;

import java.util.List;
import java.util.Optional;

public interface FortressService {
    FortressEntity createFortress(FortressEntity fortressEntity, Integer kingId);

    List<FortressEntity> getFortresses();

    Optional<FortressEntity> getFortressById(int id);

    List<FortressEntity> getFortressesByName(String name);

    FortressEntity updateFortress(int id, FortressEntity fortressEntity);

    FortressEntity crownKing(int id, Integer kingId);
}
