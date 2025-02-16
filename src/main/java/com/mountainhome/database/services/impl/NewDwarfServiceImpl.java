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
    public DwarfEntity getDwarf(int id) {
        return dwarfRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Got a getDwarf request with invalid id: {}", id);
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This dwarf doesn't exist!");
                });
    }

    @Override
    public DwarfEntity updateDwarf(int id, String dwarfName, Integer partnerId, String fortressName) {
        DwarfEntity dwarf = dwarfRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Got an updateDwarf request with invalid id: {}", id);
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This dwarf doesn't exist!");
                });
        if (dwarfName != null) {
            dwarf.setName(dwarfName);
        }
        if (fortressName != null) {
            setFortressOnDwarf(dwarf, fortressName, "updateDwarf");
        }
        if (partnerId != null) {
            marryDwarves(dwarf, partnerId);
        }
        dwarfRepository.save(dwarf);
        return dwarf;
    }

    private void setFortressOnDwarf(DwarfEntity dwarf, String fortressName, String requestType) {
        FortressEntity fortress = fortressRepository.findByName(fortressName)
                .orElseThrow(() -> {
                    log.error("Got a {} request with invalid fortress: {}", requestType, fortressName);
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This fortress doesn't exist!");
                });
        dwarf.setFortress(fortress);
    }

    private void marryDwarves(DwarfEntity dwarf, int partnerId) {
        if (dwarf.getId() == partnerId) {
            log.error("Got an updateDwarf request with same id and partnerId: {}", dwarf.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A dwarf can't marry itself!");
        }
        DwarfEntity partner = dwarfRepository.findById(partnerId)
                .orElseThrow(() -> {
                    log.error("Got an updateDwarf request with invalid partnerId: {}", partnerId);
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "This partner-dwarf doesn't exist!");
                });
        if (dwarf.getFortress() != partner.getFortress()) {
            log.error("Got an updateDwarf request with different fortresses: {}-{}, {}-{}", dwarf.getId(), dwarf.getFortress(), partnerId, partner.getFortress());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dwarves must live together to marry!");
        }

        if (dwarf.getPartner() != null) {
            DwarfEntity dwarfOldPartner = dwarf.getPartner();
            dwarfOldPartner.setPartner(null);
            dwarfRepository.save(dwarfOldPartner);
        }
        if (partner.getPartner() != null) {
            DwarfEntity partnerOldPartner = partner.getPartner();
            partnerOldPartner.setPartner(null);
            dwarfRepository.save(partnerOldPartner);
        }
        dwarf.setPartner(partner);
        partner.setPartner(dwarf);
    }
}
