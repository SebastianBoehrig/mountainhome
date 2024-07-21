package com.mountainhome.database.mappers.impl;

import com.mountainhome.database.domain.dto.DwarfDto;
import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.domain.entities.FortressEntity;
import com.mountainhome.database.domain.entities.JobSkillEntity;
import com.mountainhome.database.mappers.Mapper;
import com.mountainhome.database.repositories.DwarfRepository;
import com.mountainhome.database.repositories.FortressRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DwarfMapper implements Mapper<DwarfEntity, DwarfDto> {
    private final ModelMapper modelMapper;
    private final DwarfRepository dwarfRepository;
    private final FortressRepository fortressRepository;

    public DwarfMapper(ModelMapper modelMapper, DwarfRepository dwarfRepository, FortressRepository fortressRepository) {
        this.modelMapper = modelMapper;
        this.dwarfRepository = dwarfRepository;
        this.fortressRepository = fortressRepository;
        this.modelMapper.addMappings(getPropertyMapTo());
        this.modelMapper.addMappings(getPropertyMapFrom());
    }

    @Override
    public DwarfDto mapTo(DwarfEntity dwarfEntity) {
        return modelMapper.map(dwarfEntity, DwarfDto.class);
    }

    @Override
    public DwarfEntity mapFrom(DwarfDto dwarfDto) {
        return modelMapper.map(dwarfDto, DwarfEntity.class);
    }

    private PropertyMap<DwarfEntity, DwarfDto> getPropertyMapTo() {
        Converter<List<JobSkillEntity>, Map<String, Integer>> convertJobSkillListToValueMap = this::setJobSkillValueMap;
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                // Note: this is not normal code. It is "EDSL" so don't get confused
                // convert real Objects into id's
                using(convertJobSkillListToValueMap).map(source.getJobSkill()).setJobSkill(null);
            }
        };
    }

    private Map<String, Integer> setJobSkillValueMap(MappingContext<List<JobSkillEntity>, Map<String, Integer>> context) {
        List<JobSkillEntity> jobSkillEntityList = context.getSource();
        Map<String, Integer> jobSkillValueMap = new HashMap<>();
        jobSkillEntityList.forEach(jobSkillEntity -> jobSkillValueMap.put(jobSkillEntity.getJob().getName(),jobSkillEntity.getLevel()));
        return jobSkillValueMap;
    }

    private PropertyMap<DwarfDto, DwarfEntity> getPropertyMapFrom() {
        Converter<Integer, DwarfEntity> convertIdToDwarfEntity = this::setPartner;
        Converter<Integer, FortressEntity> convertIdToFortressEntity = this::setFortress;
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                // Note: this is not normal code. It is "EDSL" so don't get confused
                // ignore nested Properties that the user shouldn't be able to set
                skip(destination.getPartner());
                skip(destination.getJobSkill());
                skip(destination.getFavoriteFood());
                // convert id's into real Objects
                //using(convertIdToDwarfEntity).map(source.getPartnerId()).setPartner(null);
                using(convertIdToFortressEntity).map(source.getFortressId()).setFortress(null);
            }
        };
    }

    private DwarfEntity setPartner(MappingContext<Integer, DwarfEntity> context) {
        //disabled until I can figure a way out to marry both properly
        Integer id = context.getSource();
        if (id == null) return null;
        DwarfEntity partner = dwarfRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partner Dwarf doesn't exist!"));
        if (partner.getPartner() != null) {
            partner.getPartner().setPartner(null);
        }
        //set partner's partner to this new dwarf
        return partner;
    }

    private FortressEntity setFortress(MappingContext<Integer, FortressEntity> context) {
        Integer id = context.getSource();
        if (id == null) return null;
        return fortressRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fortress doesn't exist!"));
    }

    //TODO handle jobSkill map
}
