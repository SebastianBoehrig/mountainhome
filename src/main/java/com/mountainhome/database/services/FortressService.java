package com.mountainhome.database.services;

import com.mountainhome.database.domain.entities.FortressEntity;

import java.util.List;

public interface FortressService {
    FortressEntity createFortress(FortressEntity fortressEntity, Integer kingId);

    List<String> getFortressNames();

    FortressEntity getFortress(String name);

    FortressEntity updateFortress(String name, Integer kingId);
}
