package com.mountainhome.database.services.impl;

import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.domain.entities.WorkstationSkillEntity;
import com.mountainhome.database.repositories.DwarfRepository;
import com.mountainhome.database.repositories.FortressRepository;
import com.mountainhome.database.repositories.WorkstationTypeRepository;
import com.mountainhome.database.services.DwarfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class NewDwarfServiceImpl implements DwarfService {

    private final DwarfRepository dwarfRepository;
    private final FortressRepository fortressRepository;
    private final WorkstationTypeRepository workstationTypeRepository;

    public NewDwarfServiceImpl(DwarfRepository dwarfRepository, FortressRepository fortressRepository, WorkstationTypeRepository workstationTypeRepository) {
        this.dwarfRepository = dwarfRepository;
        this.fortressRepository = fortressRepository;
        this.workstationTypeRepository = workstationTypeRepository;
    }

    @Override
    public DwarfEntity createDwarf(DwarfEntity dwarfEntity, String fortressName) {
        setFortressOnDwarf(dwarfEntity, fortressName, "createDwarf");

        dwarfEntity.setBirthday(LocalDate.of(0, 1, 1));

        Random random = new Random();
        dwarfEntity.setHeightInCm((short) (random.nextInt(180 - 120) + 120));

        List<WorkstationSkillEntity> skillList = new ArrayList<>();
        workstationTypeRepository.findAll().forEach(workstationType -> skillList.add(WorkstationSkillEntity.builder()
                .dwarf(dwarfEntity)
                .workstationType(workstationType)
                .level(0).build()));
        dwarfEntity.setWorkstationSkill(skillList);

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
    public DwarfEntity updateDwarf(int id, String dwarfName, Integer partnerId, String fortressName) {
        DwarfEntity dwarf = dwarfRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Got a createDwarf request with invalid fortressId: {}", fortressName);
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This dwarf doesn't exist!");
                });
        if (dwarfName != null) {
            dwarf.setName(dwarfName);
        }
        if (fortressName != null) {
            setFortressOnDwarf(dwarf, fortressName, "updateDwarf");
        }
        if (partnerId != null) {
            //TODO
        }
        return dwarf;
    }

    @Override
    public DwarfEntity migrateDwarf(int id, Integer fortressId) {
        return null;
    }

    @Override
    public List<DwarfEntity> marryDwarves(int id, Integer partnerId) {
        return null;
    }

    private void setFortressOnDwarf(DwarfEntity dwarf, String fortressName, String requestType) {
        FortressEntity fortress = fortressRepository.findByName(fortressName)
                .orElseThrow(() -> {
                    log.error("Got a {} request with invalid fortress: {}", requestType, fortressName);
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This fortress doesn't exist!");
                });
        dwarf.setFortress(fortress);
    }
}
