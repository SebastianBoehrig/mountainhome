package com.mountainhome.database.services.impl;

import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.repositories.DwarfRepository;
import com.mountainhome.database.repositories.FortressRepository;
import com.mountainhome.database.services.FortressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NewFortressServiceImpl implements FortressService {
    private final FortressRepository fortressRepository;
    private final DwarfRepository dwarfRepository;

    public NewFortressServiceImpl(FortressRepository fortressRepository, DwarfRepository dwarfRepository) {
        this.fortressRepository = fortressRepository;
        this.dwarfRepository = dwarfRepository;
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
    public List<String> getFortressNames() {
        List<String> names = new ArrayList<>();
        for (FortressEntity fortressEntity : fortressRepository.findAll()) {
            names.add(fortressEntity.getName());
        }
        return names;
    }

    @Override
    public FortressEntity getFortress(String name) {
        return fortressRepository.findByName(name).orElseThrow(() -> {
            log.error("Got an getFortress request with invalid name: {}", name);
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This fortress doesn't exist!");
        });
    }

    @Override
    public List<DwarfEntity> getDwarfListByFortress(String fortressName) {
        FortressEntity fortress = fortressRepository.findByName(fortressName)
                .orElseThrow(() -> {
                    log.error("Got a getDwarfListByFortress request with invalid fortress: {}", fortressName);
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This fortress doesn't exist!");
                });
        return dwarfRepository.findAllByFortress(fortress);
    }

    @Override
    public FortressEntity updateFortress(String name, Integer kingId) {
        FortressEntity fortress = fortressRepository.findByName(name).orElseThrow(() -> {
                    log.error("Got an updateFortress request with invalid name: {}", name);
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This fortress doesn't exist!");
                });
        if(kingId != null) {
            DwarfEntity king = dwarfRepository.findById(kingId).orElseThrow(()->{
                        log.error("Got an updateFortress request with invalid kingId: {}", kingId);
                        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This dwarf doesn't exist!");
                        //A king never abandons his fortress
            });
            if (king.getFortress().getKing() == king) {
                log.error("Got an updateFortress request with a kingId: {} that was crowned already in fortress: {}", kingId, king.getFortress().getName());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A king never abandons his fortress!");
            }
            fortress.setKing(king);
            king.setFortress(fortress);
        }
        return fortressRepository.save(fortress);
    }
}
