package com.mountainhome.database.services.impl;

import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.domain.entities.JobSkillEntity;
import com.mountainhome.database.repositories.DwarfRepository;
import com.mountainhome.database.repositories.FortressRepository;
import com.mountainhome.database.repositories.JobRepository;
import com.mountainhome.database.services.DwarfService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Log
@Service
public class DwarfServiceImpl implements DwarfService {

    private final DwarfRepository dwarfRepository;
    private final FortressRepository fortressRepository;

    private final JobRepository jobRepository;

    public DwarfServiceImpl(DwarfRepository dwarfRepository, FortressRepository fortressRepository, JobRepository jobRepository) {
        this.dwarfRepository = dwarfRepository;
        this.fortressRepository = fortressRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public DwarfEntity createDwarf(DwarfEntity dwarfEntity) {
        if (dwarfEntity.getFortress() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Dwarves are only born in a Fortress!");
        }

        List<JobSkillEntity> jobSkillList = new ArrayList<>();
        jobRepository.findAll().forEach(job-> jobSkillList.add(JobSkillEntity.builder().dwarf(dwarfEntity).level(0).job(job).build()));
        dwarfEntity.setJobSkill(jobSkillList);

        return dwarfRepository.save(dwarfEntity);
    }

    @Override
    public List<DwarfEntity> getDwarves() {
        return StreamSupport.stream(dwarfRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public Optional<DwarfEntity> getDwarfById(int id) {
        return dwarfRepository.findById(id);
    }

    @Override
    public List<DwarfEntity> getDwarvesByName(String name) {
        return dwarfRepository.findByName(name);
    }

    @Override
    public boolean isExists(int id) {
        return dwarfRepository.existsById(id);
    }

    @Override
    public DwarfEntity updateDwarf(int id, DwarfEntity dwarfEntity) {
        dwarfEntity.setId(id);
        return dwarfRepository.findById(id).map(existingDwarf -> {
            Optional.ofNullable(dwarfEntity.getName()).ifPresent(existingDwarf::setName);
            Optional.ofNullable(dwarfEntity.getAge()).ifPresent(existingDwarf::setAge);
            Optional.ofNullable(dwarfEntity.getHeightInCm()).ifPresent(existingDwarf::setHeightInCm);
            return dwarfRepository.save(existingDwarf);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dwarf does not exist"));
    }

    @Override
    public DwarfEntity migrateDwarf(int id, Integer fortressId) {
        DwarfEntity dwarf = dwarfRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dwarf does not exist"));
        Optional<FortressEntity> fortress = fortressRepository.findById(fortressId);
        dwarf.setFortress(fortress.orElse(null));
        return dwarfRepository.save(dwarf);
    }

    @Override
    public List<DwarfEntity> marryDwarves(int id, Integer partnerId) {
        DwarfEntity dwarf = dwarfRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dwarf does not exist"));
        DwarfEntity partner = dwarfRepository.findById(partnerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dwarf does not exist"));

        if (dwarf.getPartner() != null) {
            dwarf.getPartner().setPartner(null);
        }
        if (partner.getPartner() != null) {
            partner.getPartner().setPartner(null);
        }

        partner.setPartner(dwarf);
        dwarf.setPartner(partner);

        DwarfEntity d1 = dwarfRepository.save(dwarf);
        DwarfEntity d2 = dwarfRepository.save(partner);
        return Arrays.asList(d1, d2);
    }
}
