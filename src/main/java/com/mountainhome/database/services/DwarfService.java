package com.mountainhome.database.services;

import com.mountainhome.database.domain.entities.DwarfEntity;

public interface DwarfService {
    DwarfEntity createDwarf(DwarfEntity dwarfEntity, String fortressName);

    DwarfEntity getDwarf(int id);

    DwarfEntity updateDwarf(int id, String dwarfName, Integer partnerId, String fortressName);
}
