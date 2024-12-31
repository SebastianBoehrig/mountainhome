package com.mountainhome.database.mappers;

import com.mountainhome.database.domain.dto.DwarfDto;
import com.mountainhome.database.domain.entities.DwarfEntity;
import com.mountainhome.database.domain.entities.WorkstationSkillEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DwarfMapper {
    @Mapping(target = "id", ignore = true) // Ignore the id field to let JPA generate it
    @Mapping(target = "fortress", ignore = true) // To set this manually in the service
    @Mapping(target = "birthday", ignore = true) // To set this manually in the service
    @Mapping(target = "heightInCm", ignore = true) // To set this manually in the service
    @Mapping(target = "favoriteFood", ignore = true) // To set this manually in the service
    @Mapping(target = "partner", ignore = true) // No child marriage
    @Mapping(target = "workstationSkill", ignore = true)
    DwarfEntity toDwarfEntity(DwarfDto source);

    @Mapping(source = "fortress.name", target = "fortress")
    @Mapping(target = "workstationSkill", expression = "java(toSkillMap(source.getWorkstationSkill()))")
    DwarfDto toDwarfDto(DwarfEntity source);

    default Map<String, Integer> toSkillMap(List<WorkstationSkillEntity> skills) {
        return skills.stream()
                .collect(Collectors.toMap(
                        skill -> skill.getWorkstationType().getName(),
                        WorkstationSkillEntity::getLevel
                ));
    }
}
