package com.mountainhome.database.services;

import com.mountainhome.database.domain.entities.DwarfEntity;

import java.util.List;
import java.util.Optional;

public interface DwarfService {
    DwarfEntity createDwarf(DwarfEntity dwarfEntity);

    List<DwarfEntity> getDwarves();

    Optional<DwarfEntity> getDwarfById(int id);

    List<DwarfEntity> getDwarvesByName(String name);

    boolean isExists(int id);

    DwarfEntity updateDwarf(int id, DwarfEntity dwarfEntity);

    DwarfEntity migrateDwarf(int id, Integer fortressId);

    List<DwarfEntity> marryDwarves(int id, Integer partnerId);

}
