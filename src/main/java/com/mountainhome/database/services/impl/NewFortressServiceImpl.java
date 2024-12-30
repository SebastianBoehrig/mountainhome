package com.mountainhome.database.services.impl;

import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.repositories.DwarfRepository;
import com.mountainhome.database.repositories.FortressRepository;
import com.mountainhome.database.repositories.ResourceRepository;
import com.mountainhome.database.services.FortressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NewFortressServiceImpl implements FortressService {
    private final FortressRepository fortressRepository;
    private final DwarfRepository dwarfRepository;
    private final ResourceRepository resourceRepository;

    public NewFortressServiceImpl(FortressRepository fortressRepository, DwarfRepository dwarfRepository, ResourceRepository resourceRepository) {
        this.fortressRepository = fortressRepository;
        this.dwarfRepository = dwarfRepository;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public FortressEntity createFortress(FortressEntity fortressEntity, Integer kingId) {
        if (kingId != null) {
            DwarfEntity king = dwarfRepository.findById(kingId).orElseThrow(() -> {
                log.error("Got a createFortress request with invalid kingId: {}", kingId);
                return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This dwarf doesn't exist!");
            });
            fortressEntity.setKing(king);
            king.setFortress(fortressEntity);
        }
        return fortressRepository.save(fortressEntity);
    }

    @Override
    public List<FortressEntity> getFortresses() {
        return null;
    }

    @Override
    public Optional<FortressEntity> getFortressById(int id) {
        return Optional.empty();
    }

    @Override
    public List<FortressEntity> getFortressesByName(String name) {
        return null;
    }

    @Override
    public FortressEntity updateFortress(int id, FortressEntity fortressEntity) {
        return null;
    }

    @Override
    public FortressEntity crownKing(int id, Integer kingId) {
        return null;
    }
}
