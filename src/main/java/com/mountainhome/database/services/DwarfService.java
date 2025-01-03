package com.mountainhome.database.services;

import com.mountainhome.database.domain.entities.DwarfEntity;

import java.util.List;
import java.util.Optional;

public interface DwarfService {
    DwarfEntity createDwarf(DwarfEntity dwarfEntity, String fortressName);

    List<DwarfEntity> getDwarves();

    Optional<DwarfEntity> getDwarfById(int id);

    List<DwarfEntity> getDwarvesByName(String name);

    DwarfEntity updateDwarf(int id, String dwarfName, Integer partnerId, String fortressName);

    DwarfEntity migrateDwarf(int id, Integer fortressId);

    List<DwarfEntity> marryDwarves(int id, Integer partnerId);

}
