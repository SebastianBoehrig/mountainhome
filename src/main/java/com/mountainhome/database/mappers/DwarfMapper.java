package com.mountainhome.database.mappers;

import com.mountainhome.database.domain.dto.DwarfDto;
import com.mountainhome.database.domain.entities.DwarfEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DwarfMapper {
    @Mapping(target = "id", ignore = true) // Ignore the id field to let JPA generate it
    @Mapping(target = "fortress", ignore = true) // To set this manually in the service
    @Mapping(target = "birthday", ignore = true) // To set this manually in the service
    @Mapping(target = "heightInCm", ignore = true) // To set this manually in the service
    @Mapping(target = "favoriteFood", ignore = true) // To set this manually in the service
    @Mapping(target = "partner", ignore = true) // No child marriage
    @Mapping(target = "jobSkill", ignore = true)
    DwarfEntity toDwarfEntity(DwarfDto source);

    @Mapping(source = "fortress.id", target = "fortressId")
    @Mapping(target = "jobSkill", ignore = true)
    DwarfDto toDwarfDto(DwarfEntity source);
}
