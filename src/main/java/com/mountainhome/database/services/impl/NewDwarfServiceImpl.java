package com.mountainhome.database.services.impl;

import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.repositories.DwarfRepository;
import com.mountainhome.database.repositories.FortressRepository;
import com.mountainhome.database.repositories.JobRepository;
import com.mountainhome.database.services.DwarfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class NewDwarfServiceImpl implements DwarfService {

    private final DwarfRepository dwarfRepository;
    private final FortressRepository fortressRepository;
    private final JobRepository jobRepository;

    public NewDwarfServiceImpl(DwarfRepository dwarfRepository, FortressRepository fortressRepository, JobRepository jobRepository) {
        this.dwarfRepository = dwarfRepository;
        this.fortressRepository = fortressRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public DwarfEntity createDwarf(DwarfEntity dwarfEntity, int fortressId) {
        FortressEntity fortress = fortressRepository.findById(fortressId)
                .orElseThrow(() -> {
                    log.error("Got a createDwarf request with invalid fortressId: {}", fortressId);
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This fortress doesn't exist!");
                });
        dwarfEntity.setFortress(fortress);
        dwarfEntity.setBirthday(LocalDate.of(0, 1, 1));
        Random random = new Random();
        dwarfEntity.setHeightInCm((short) (random.nextInt(180 - 120) + 120));
        return dwarfRepository.save(dwarfEntity);
    }

    @Override
    public List<DwarfEntity> getDwarves() {
        return null;
    }

    @Override
    public Optional<DwarfEntity> getDwarfById(int id) {
        return Optional.empty();
    }

    @Override
    public List<DwarfEntity> getDwarvesByName(String name) {
        return null;
    }

    @Override
    public DwarfEntity updateDwarf(int id, DwarfEntity dwarfEntity) {
        return null;
    }

    @Override
    public DwarfEntity migrateDwarf(int id, Integer fortressId) {
        return null;
    }

    @Override
    public List<DwarfEntity> marryDwarves(int id, Integer partnerId) {
        return null;
    }
}
